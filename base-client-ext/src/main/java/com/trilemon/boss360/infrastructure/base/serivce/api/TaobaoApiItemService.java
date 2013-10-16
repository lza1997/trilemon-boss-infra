package com.trilemon.boss360.infrastructure.base.serivce.api;

import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.trilemon.boss360.infrastructure.base.client.BaseClient;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;
import com.trilemon.boss360.infrastructure.base.serivce.TaobaoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kevin
 */
@Service
public class TaobaoApiItemService {
    @Autowired
    private TaobaoApiService taobaoApiService;
    @Autowired
    private BaseClient baseClient;

    public List<Item> getItems(long userId, ItemsOnsaleGetRequest request) throws EnhancedApiException {
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        if (null == taobaoSession) {
            throw new EnhancedApiException("no taobaoSession of userId[" + userId + "]");
        }
        ItemsOnsaleGetResponse response = taobaoApiService.request(request, taobaoApiService.getAppKey(),
                taobaoSession.getSessionKey());
        if (response.isSuccess()) {
            return response.getItems();
        } else {
            throw new EnhancedApiException(request, response);
        }
    }

    public Long getItemNum(long userId, ItemsOnsaleGetRequest request) throws EnhancedApiException {
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        if (null == taobaoSession) {
            throw new EnhancedApiException("no taobaoSession of userId[" + userId + "]");
        }
        request.setPageSize(0L);
        request.setPageNo(1L);
        ItemsOnsaleGetResponse response = taobaoApiService.request(request, taobaoApiService.getAppKey(),
                taobaoSession.getSessionKey());
        if (response.isSuccess()) {
            return response.getTotalResults();
        } else {
            throw new EnhancedApiException(request, response);
        }
    }

}
