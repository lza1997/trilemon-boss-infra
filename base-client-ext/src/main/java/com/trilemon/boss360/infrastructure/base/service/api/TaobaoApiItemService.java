package com.trilemon.boss360.infrastructure.base.service.api;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.trilemon.boss360.infrastructure.base.client.BaseClient;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;
import com.trilemon.boss360.infrastructure.base.service.TaobaoApiService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
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

    @NotNull
    public Pair<List<Item>, Long> getOnSaleItems(long userId, ItemsOnsaleGetRequest request) throws
            EnhancedApiException {
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        if (null == taobaoSession) {
            throw new EnhancedApiException("no taobaoSession of userId[" + userId + "]");
        }
        ItemsOnsaleGetResponse response = taobaoApiService.request(request, taobaoApiService.getAppKey(),
                taobaoSession.getSessionKey());
        if (response.isSuccess()) {
            List<Item> items = response.getItems();
            items = (null == items ? Lists.<Item>newArrayList() : items);
            return Pair.of(items, response.getTotalResults());
        } else {
            throw new EnhancedApiException(request, response);
        }
    }

    public Item getItem(long userId,Long numIid,List<String> fields) throws EnhancedApiException {
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        if (null == taobaoSession) {
            throw new EnhancedApiException("no taobaoSession of userId[" + userId + "]");
        }
        ItemGetRequest request=new ItemGetRequest();
        request.setFields(Joiner.on(",").join(fields));
        request.setNumIid(numIid);
        ItemGetResponse response = taobaoApiService.request(request,taobaoSession.getSessionKey());
        if (response.isSuccess()) {
           return response.getItem();
        } else {
            throw new EnhancedApiException(request, response);
        }
    }
}
