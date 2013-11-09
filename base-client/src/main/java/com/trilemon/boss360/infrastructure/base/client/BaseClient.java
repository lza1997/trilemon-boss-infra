package com.trilemon.boss360.infrastructure.base.client;

import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoAccessControlException;
import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoEnhancedApiException;
import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoSessionExpiredException;
import com.trilemon.boss360.infrastructure.base.model.TaobaoApp;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSeller;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;

import java.util.Date;

/**
 * @author kevin
 */
public interface BaseClient {
    /**
     * 获取{@link TaobaoApp}对象
     *
     * @param appKey 淘宝 app key
     * @return {@link TaobaoApp}
     */
    TaobaoApp getTaobaoApp(String appKey);

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
    TaobaoSession getTaobaoSession(Long userId, String appKey);

    String getNick(Long userId);

    TaobaoSeller getSeller(Long taobaoUserId);

    void createSeller(String accessToken, String appKey) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException;

    void saveOrUpdateTaobaoSession(TaobaoSession taobaoSession);
}
