package com.trilemon.boss.infra.base.service.api;

import com.taobao.api.request.ItemUpdateRequest;
import com.taobao.api.response.ItemUpdateResponse;
import com.trilemon.boss.infra.base.client.BaseClient;
import com.trilemon.boss.infra.base.model.TaobaoSession;
import com.trilemon.boss.infra.base.service.TaobaoApiService;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoAccessControlException;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoEnhancedApiException;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoSessionExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author kevin
 */
public class TaobaoApiItemService {
    private static Logger logger = LoggerFactory.getLogger(TaobaoApiItemService.class);
    private TaobaoApiService taobaoApiService;
    private BaseClient baseClient;

    public ItemUpdateResponse updateItem(Long userId, ItemUpdateRequest request) throws
            TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(request, "request must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s] appKey[{}]", userId,
                taobaoApiService.getAppKey());

        return taobaoApiService.requestWithAppKey(request, taobaoApiService.getAppKey(),
                taobaoSession.getAccessToken());
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
