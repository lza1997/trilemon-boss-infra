package com.trilemon.boss360.infrastructure.base.service.api;

import com.taobao.api.domain.TradeRate;
import com.taobao.api.request.TraderateAddRequest;
import com.taobao.api.request.TradesSoldIncrementGetRequest;
import com.taobao.api.response.TraderateAddResponse;
import com.taobao.api.response.TradesSoldIncrementGetResponse;
import com.trilemon.boss360.infrastructure.base.client.BaseClient;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;
import com.trilemon.boss360.infrastructure.base.service.TaobaoApiService;
import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoAccessControlException;
import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoEnhancedApiException;
import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoSessionExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author kevin
 */
public class TaobaoApiTradeService {
    private static Logger logger = LoggerFactory.getLogger(TaobaoApiTradeService.class);
    private TaobaoApiService taobaoApiService;
    private BaseClient baseClient;

    public TradesSoldIncrementGetResponse getTrades(Long userId, TradesSoldIncrementGetRequest request) throws
            TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(request, "request must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s]",userId);

        TradesSoldIncrementGetResponse response = taobaoApiService.requestWithAppKey(request, taobaoApiService.getAppKey(),
                taobaoSession.getAccessToken());
        return response;
    }

    public TradeRate addRate(Long userId, TraderateAddRequest request) throws
            TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(request, "request must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s]",userId);

        TraderateAddResponse response = taobaoApiService.requestWithAppKey(request, taobaoApiService.getAppKey(),
                taobaoSession.getAccessToken());
        return response.getTradeRate();
    }

    public TaobaoApiService getTaobaoApiService() {
        return taobaoApiService;
    }

    public void setTaobaoApiService(TaobaoApiService taobaoApiService) {
        this.taobaoApiService = taobaoApiService;
    }

    public BaseClient getBaseClient() {
        return baseClient;
    }

    public void setBaseClient(BaseClient baseClient) {
        this.baseClient = baseClient;
    }

}
