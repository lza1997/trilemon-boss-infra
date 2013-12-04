package com.trilemon.boss.infra.base.client.impl;

import com.trilemon.boss.infra.base.client.BaseClient;
import com.trilemon.boss.infra.base.model.*;
import com.trilemon.boss.infra.base.model.dto.SignIn;
import com.trilemon.boss.infra.base.service.*;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoAccessControlException;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoEnhancedApiException;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoSessionExpiredException;
import com.trilemon.boss.infra.base.web.auth.TaobaoOauthException;
import com.trilemon.boss.infra.base.web.auth.shiro.ShiroTaobaoAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    @Autowired
    private AppUserService appUserService;

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
    public void insertOrUpdateTaobaoSession(TaobaoSession taobaoSession) {
        taobaoSessionService.insertOrUpdateTaobaoSession(taobaoSession);
    }

    @Override
    public List<BuyerBlacklist> paginateBuyerBlacklist(long userId, byte type, int pageNum, int pageSize, String sortField,
                                                       String sortType) {
        return taobaoShopService.paginateBuyerBlacklist(userId, type, pageNum, pageSize, sortField, sortType);
    }

    @Override
    public void addBuyerBlacklist(BuyerBlacklist buyerBlacklist) {
        taobaoShopService.addBuyerBlacklist(buyerBlacklist);
    }

    @Override
    public void addBuyerBlacklists(List<BuyerBlacklist> buyerBlacklists) {
        taobaoShopService.addBuyerBlacklists(buyerBlacklists);
    }

    @Override
    public void updateBuyerBlacklist(BuyerBlacklist buyerBlacklist) {
        taobaoShopService.updateBuyerBlacklist(buyerBlacklist);
    }

    @Override
    public void deleteBuyerBlacklist(Long userId, String buyerNick, byte type) {
        taobaoShopService.deleteBuyerBlacklist(userId, buyerNick, type);
    }

    @Override
    public BuyerBlacklist getBuyerBlacklist(Long userId, String buyerNick) {
        return taobaoShopService.getBuyerBlacklist(userId, buyerNick);
    }

    @Override
    public AppUser getAppUser(Long userId, String appKey) {
        return appUserService.getAppUser(userId, appKey);
    }

    @Override
    public AppUser signIn(ShiroTaobaoAuthenticationToken token) throws TaobaoOauthException, TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        return taobaoSessionService.singIn(token);
    }

    @Override
    public AppUser signIn(TaobaoSession taobaoSession, SignIn signIn) throws TaobaoSessionExpiredException, TaobaoAccessControlException, TaobaoEnhancedApiException {
        return taobaoSessionService.signIn(taobaoSession, signIn);
    }

    @Override
    public long getTradeNum(Long userId, Date startDate, Date endDate) {
        return 0;
    }

    @Override
    public TaobaoSession getTaobaoSession(Long userId, Long subUserId, String appKey) {
        return taobaoSessionService.getTaobaoSession(userId, subUserId, appKey);
    }

    @Override
    public TaobaoSession getTaobaoSession(Long userId, String appKey) {
        TaobaoSession taobaoSession = taobaoSessionService.getTaobaoSessionBySubTaobaoUserId(userId, appKey);
        if (null == taobaoSession) {
            taobaoSession = taobaoSessionService.getTaobaoSession(userId, null, appKey);
        }
        return taobaoSession;
    }
}