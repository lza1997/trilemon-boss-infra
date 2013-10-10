package com.trilemon.boss360.infrastructure.base.client;

import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.trilemon.boss360.infrastructure.base.model.TaobaoApiUsage;
import com.trilemon.boss360.infrastructure.base.model.TaobaoApp;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;
import com.trilemon.boss360.infrastructure.base.serivce.EnhancedApiException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author kevin
 */
@Service
public interface BaseClient {
    void updateApiUsage(List<TaobaoApiUsage> taobaoApiUsageList);

    TaobaoApp getTaobaoApp(String taobaoAppKey);

    List<Item> getOnSaleItemsByNick(ItemsOnsaleGetRequest request, String appKey, String nick) throws EnhancedApiException;

    List<Item> getOnSaleItemsByUserId(ItemsOnsaleGetRequest request, String appKey, long userId) throws EnhancedApiException;

    long getTradeNumFromTop(String appKey, String sessionKey, Date startDateTime, Date endDateTime);

    TaobaoSession getTaobaoSession(Long userId);
}
