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
    /**
     * 更新淘宝 api 使用情况
     *
     * @param taobaoApiUsageList
     */
    void updateApiUsage(List<TaobaoApiUsage> taobaoApiUsageList);

    /**
     * 获取{@link TaobaoApp}对象
     *
     * @param taobaoAppKey 淘宝 app key
     * @return {@link TaobaoApp}
     */
    TaobaoApp getTaobaoApp(String taobaoAppKey);

    /**
     * 根据淘宝昵称获取在线宝贝列表
     *
     * @param request
     * @param appKey
     * @param nick
     * @return
     * @throws EnhancedApiException
     */
    List<Item> getOnSaleItemsByNick(ItemsOnsaleGetRequest request, String appKey, String nick) throws EnhancedApiException;

    /**
     * 根据淘宝id获取在线宝贝列表
     *
     * @param request
     * @param appKey
     * @param userId
     * @return
     * @throws EnhancedApiException
     */
    List<Item> getOnSaleItemsByUserId(ItemsOnsaleGetRequest request, String appKey, long userId) throws EnhancedApiException;

    /**
     * 从淘宝开放平台获取指定时间范围内的订单数量
     *
     * @param userId
     * @param appKey
     * @param startDate
     * @param endDate
     * @return
     */
    long getTradeNumFromTop(Long userId, String appKey, Date startDate, Date endDate);

    /**
     * 从数据库中获取指定时间范围内订单数量
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    long getTradeNum(Long userId, Date startDate, Date endDate);

    /**
     * 获取{@link TaobaoSession}对象
     *
     * @param userId
     * @return {@link TaobaoSession}
     */
    TaobaoSession getTaobaoSession(Long userId);

    TaobaoSession getTaobaoSession(String nick);
}
