package com.trilemon.boss360.infrastructure.base.client.impl;

import com.trilemon.boss360.infrastructure.base.client.BaseClient;
import com.trilemon.boss360.infrastructure.base.model.TaobaoApp;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSeller;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;
import com.trilemon.boss360.infrastructure.base.service.TaobaoAppService;
import com.trilemon.boss360.infrastructure.base.service.TaobaoSessionService;
import com.trilemon.boss360.infrastructure.base.service.TaobaoShopService;
import com.trilemon.boss360.infrastructure.base.service.api.EnhancedApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author kevin
 */
@Service
public class BaseClientImpl implements BaseClient {
    @Autowired
    private TaobaoAppService taobaoAppService;
    @Autowired
    private TaobaoSessionService taobaoSessionService;
    @Autowired
    private TaobaoShopService taobaoShopService;

    @Override
    public TaobaoApp getTaobaoApp(String taobaoAppKey) {
        return taobaoAppService.getTaobaoApp(taobaoAppKey);
    }

    @Override
    public String getNick(Long userId) {
        return taobaoShopService.getNick(userId);
    }

    @Override
    public TaobaoSeller getSeller(Long taobaoUserId) {
        return taobaoShopService.getSeller(taobaoUserId);
    }

    @Override
    public void createSeller(String accessToken, String appKey) throws EnhancedApiException {
        taobaoShopService.createSeller(accessToken, appKey);
    }

    @Override
    public void saveOrUpdateTaobaoSession(TaobaoSession taobaoSession) {
        taobaoSessionService.saveOrUpdateTaobaoSession(taobaoSession);
    }

    @Override
    public long getTradeNum(Long userId, Date startDate, Date endDate) {
        return 0;
    }

    @Override
    public TaobaoSession getTaobaoSession(Long userId, String appKey) {
        return taobaoSessionService.getTaobaoSession(userId, appKey);
    }
}