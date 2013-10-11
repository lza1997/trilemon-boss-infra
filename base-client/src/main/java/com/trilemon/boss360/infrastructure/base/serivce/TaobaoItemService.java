package com.trilemon.boss360.infrastructure.base.serivce;

import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kevin
 */
@Service
public class TaobaoItemService {
    @Autowired
    private TaobaoApiService taobaoApiService;
    @Autowired
    private TaobaoUserService taobaoUserService;

    public List<Item> getOnSaleItemsByNick(ItemsOnsaleGetRequest request, String appKey,
                                           String nick) throws EnhancedApiException {
        TaobaoSession taobaoSession = taobaoUserService.getTaobaoSession(nick);
        if(null==taobaoSession){
            return null;
        }
        return getOnSaleItemsBySessionKey(request, appKey, taobaoSession.getSessionKey());
    }

    public List<Item> getOnSaleItemsByUserId(ItemsOnsaleGetRequest request, String appKey,
                                             long userId) throws EnhancedApiException {
        TaobaoSession taobaoSession = taobaoUserService.getTaobaoSession(userId);
        if(null==taobaoSession){
            return null;
        }
        return getOnSaleItemsBySessionKey(request, appKey, taobaoSession.getSessionKey());
    }

    public List<Item> getOnSaleItemsBySessionKey(ItemsOnsaleGetRequest request, String appKey,

                                                 String sessionKey) throws EnhancedApiException {
        ItemsOnsaleGetResponse response = taobaoApiService.request(request, appKey, sessionKey);
        if (response.isSuccess()) {
            return response.getItems();
        } else {
            throw new EnhancedApiException(request, response);
        }
    }
}
