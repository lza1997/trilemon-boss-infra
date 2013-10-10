package com.trilemon.boss360.base.client;

import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.trilemon.boss360.base.model.TaobaoApiUsage;
import com.trilemon.boss360.base.model.TaobaoApp;
import com.trilemon.boss360.base.serivce.EnhancedApiException;

import java.util.List;

/**
 * @author kevin
 */
public interface BaseClient {
    void updateApiUsage(List<TaobaoApiUsage> taobaoApiUsageList);

    TaobaoApp getTaobaoApp(String taobaoAppKey);

    List<Item> getOnSaleItemsByNick(ItemsOnsaleGetRequest request, String appKey, String nick) throws EnhancedApiException;

    List<Item> getOnSaleItemsByUserId(ItemsOnsaleGetRequest request, String appKey, long userId) throws EnhancedApiException;
}
