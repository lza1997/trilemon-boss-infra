package com.trilemon.boss360.infrastructure.base.service;

import com.google.common.base.Stopwatch;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.taobao.api.*;
import com.trilemon.boss360.infrastructure.base.service.api.TaobaoEnhancedApiException;
import com.trilemon.boss360.infrastructure.base.service.api.TaobaoSessionExpiredException;
import com.trilemon.boss360.infrastructure.base.client.BaseClient;
import com.trilemon.boss360.infrastructure.base.model.TaobaoApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author kevin
 */
public class TaobaoApiService {
    private static Logger logger = LoggerFactory.getLogger(TaobaoApiService.class);
    private Multiset<String> totalApiExecTime = HashMultiset.create();
    private Multiset<String> totalApiExecCount = HashMultiset.create();
    private String taobaoAppKey;
    private AppService appService;
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

    public <REQ extends TaobaoRequest<RES>, RES extends TaobaoResponse> RES request(REQ req, String appKey) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        return request(req, appKey, null);
    }

    public <REQ extends TaobaoRequest<RES>, RES extends TaobaoResponse> RES request(REQ req, String appKey, String sessionKey) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        checkNotNull(taobaoClient);

        final Stopwatch stopwatch = new Stopwatch().start();
        RES response;
        try {
            response = taobaoClient.execute(req, sessionKey);
            if(response.getSubCode().equals("session-expired")){
                throw new TaobaoSessionExpiredException("session expired",req);
            }
        } catch (ApiException e) {
            throw new TaobaoEnhancedApiException(e);
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

    public String getTaobaoAppKey() {
        return taobaoAppKey;
    }

    public AppService getAppService() {
        return appService;
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public BaseClient getBaseClient() {
        return baseClient;
    }

    public void setBaseClient(BaseClient baseClient) {
        this.baseClient = baseClient;
    }
}
