package com.trilemon.boss360.infrastructure.base.serivce;

import com.google.common.base.Stopwatch;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.taobao.api.*;
import com.trilemon.boss360.infrastructure.base.client.BaseClient;
import com.trilemon.boss360.infrastructure.base.model.TaobaoApp;
import com.trilemon.boss360.infrastructure.base.serivce.api.EnhancedApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author kevin
 */
@Service
public class TaobaoApiService {
    private static Logger logger = LoggerFactory.getLogger(TaobaoApiService.class);
    private Multiset<String> totalApiExecTime = HashMultiset.create();
    private Multiset<String> totalApiExecCount = HashMultiset.create();
    @Value("${taobao_app_key}")
    private String taobaoAppKey;
    @Autowired
    private AppService appService;
    @Autowired
    private BaseClient baseClient;
    private TaobaoClient taobaoClient;

    @PostConstruct
    public void init() throws SocketException {
        checkNotNull(appService.getServiceName());
        checkNotNull(appService.getServiceId());
        initTaobaoClient();
    }

    public void initTaobaoClient() {
        TaobaoApp taobaoApp = baseClient.getTaobaoApp(taobaoAppKey);
        taobaoClient = new AutoRetryTaobaoClient(taobaoApp.getAppCallbackUrl(),
                taobaoApp.getAppKey(), taobaoApp.getAppSecret());
    }

    public <REQ extends TaobaoRequest<RES>, RES extends TaobaoResponse> RES request(REQ req, String appKey) throws EnhancedApiException {
        return request(req, appKey, null);
    }

    public <REQ extends TaobaoRequest<RES>, RES extends TaobaoResponse> RES request(REQ req, String appKey, String sessionKey) throws EnhancedApiException {
        checkNotNull(taobaoClient);

        final Stopwatch stopwatch = new Stopwatch().start();
        RES response;
        try {
            response = taobaoClient.execute(req, sessionKey);
        } catch (ApiException e) {
            throw new EnhancedApiException(e);
        }
        final long elapsedMillis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        totalApiExecTime.add(appKey, (int) elapsedMillis);
        totalApiExecCount.add(appKey);
        return response;
    }

    public int getAvgTaobaoApiExecTime(String appKey) {
        return totalApiExecTime.count(appKey) / totalApiExecCount.count(appKey);
    }

    public String getAppKey() {
        return taobaoAppKey;
    }

    public void setTaobaoAppKey(String taobaoAppKey) {
        this.taobaoAppKey = taobaoAppKey;
    }
}
