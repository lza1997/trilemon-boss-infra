package com.trilemon.boss.infra.base.service;

import com.google.common.base.Stopwatch;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.taobao.api.*;
import com.trilemon.boss.infra.base.client.BaseClient;
import com.trilemon.boss.infra.base.model.TaobaoApp;
import com.trilemon.boss.infra.base.service.api.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TODO 支持多帐号
 *
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
    private TaobaoApiExceptionHandler taobaoApiExceptionHandler;

    @PostConstruct
    public void init() throws SocketException {
        checkNotNull(appService.getServiceName());
        checkNotNull(appService.getServiceId());
        if (null == taobaoApiExceptionHandler) {
            logger.info("no specify taobaoApiExceptionHandler, use DefaultTaobaoApiExceptionHandler.");
            taobaoApiExceptionHandler = new DefaultTaobaoApiExceptionHandler();
        }
        initTaobaoClient();
    }

    public void initTaobaoClient() {
        TaobaoApp taobaoApp = baseClient.getTaobaoApp(taobaoAppKey);
        if (null == taobaoApp) {
            throw new RuntimeException("init taobao client fail, app key[" + taobaoAppKey + "]");
        }
        taobaoClient = new AutoRetryTaobaoClient(taobaoApp.getAppCallbackUrl(),
                taobaoApp.getAppKey(), taobaoApp.getAppSecret());
        logger.info("init taobao client successfully, app key[{}].", taobaoApp.getAppKey());
    }

    public <REQ extends TaobaoRequest<RES>, RES extends TaobaoResponse> RES requestWithAppKey(REQ req,
                                                                                              String appKey) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        return requestWithAppKey(req, appKey, null);
    }

    public <REQ extends TaobaoRequest<RES>, RES extends TaobaoResponse> RES request(REQ req,
                                                                                    String sessionKey) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        return requestWithAppKey(req, taobaoAppKey, sessionKey);
    }

    public <REQ extends TaobaoRequest<RES>, RES extends TaobaoResponse> RES request(REQ req) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        return requestWithAppKey(req, taobaoAppKey, null);
    }

    public <REQ extends TaobaoRequest<RES>, RES extends TaobaoResponse> RES requestWithAppKey(REQ request,
                                                                                              String appKey,
                                                                                              String sessionKey) throws TaobaoAccessControlException, TaobaoSessionExpiredException, TaobaoEnhancedApiException {
        checkNotNull(taobaoClient);
        checkNotNull(request);
        checkNotNull(appKey);

        try {
            return internalRequestWithAppKey(request, appKey, sessionKey);
        } catch (BaseTaobaoApiException e) {
            taobaoApiExceptionHandler.handler(e);
            throw e;
        }
    }

    private <REQ extends TaobaoRequest<RES>, RES extends TaobaoResponse> RES internalRequestWithAppKey(REQ request,
                                                                                                       String appKey,
                                                                                                       String sessionKey) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(taobaoClient);
        checkNotNull(request);
        checkNotNull(appKey);

        final Stopwatch stopwatch = Stopwatch.createStarted();
        RES response;
        try {
            response = taobaoClient.execute(request, sessionKey);

            if (!response.isSuccess()) {
                String subCode = response.getSubCode();
                if (null != subCode) {
                    if (subCode.equals("session-expired")) {
                        throw new TaobaoSessionExpiredException(request, response);
                    } else if (subCode.contains("accesscontrol")) {
                        throw new TaobaoAccessControlException(request, response);
                    }
                }
                throw new TaobaoEnhancedApiException(request, response);
            }
        } catch (ApiException e) {
            throw new TaobaoEnhancedApiException(request, e);
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

    public String getTaobaoAppKey() {
        return taobaoAppKey;
    }

    public void setTaobaoAppKey(String taobaoAppKey) {
        this.taobaoAppKey = taobaoAppKey;
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
