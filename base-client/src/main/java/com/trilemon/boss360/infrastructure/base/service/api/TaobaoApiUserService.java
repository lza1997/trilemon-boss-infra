package com.trilemon.boss360.infrastructure.base.service.api;

import com.taobao.api.domain.User;
import com.taobao.api.request.UserGetRequest;
import com.taobao.api.response.UserGetResponse;
import com.trilemon.boss360.infrastructure.base.client.BaseClient;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;
import com.trilemon.boss360.infrastructure.base.service.TaobaoApiService;
import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoAccessControlException;
import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoEnhancedApiException;
import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoSessionExpiredException;
import com.trilemon.commons.Collections3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author kevin
 */
public class TaobaoApiUserService {
    private static Logger logger = LoggerFactory.getLogger(TaobaoApiShopService.class);
    private TaobaoApiService taobaoApiService;
    private BaseClient baseClient;

    public User getUser(Long userId, String nick, List<String> fields) throws TaobaoEnhancedApiException,
            TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(nick, "nick must be not null.");
        checkNotNull(fields, "fields must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s]", userId);

        UserGetRequest request = new UserGetRequest();
        request.setFields(Collections3.COMMA_JOINER.join(fields));
        request.setNick(nick);
        UserGetResponse response = taobaoApiService.requestWithAppKey(request, taobaoApiService.getAppKey(),
                taobaoSession.getAccessToken());
        return response.getUser();
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
