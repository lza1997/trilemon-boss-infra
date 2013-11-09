package com.trilemon.boss360.infrastructure.base.service;

import com.taobao.api.domain.User;
import com.taobao.api.request.UserSellerGetRequest;
import com.taobao.api.response.UserSellerGetResponse;
import com.trilemon.boss360.infrastructure.base.dao.TaobaoSellerMapper;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSeller;
import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoAccessControlException;
import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoEnhancedApiException;
import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoSessionExpiredException;
import com.trilemon.commons.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kevin
 */
@Service
public class TaobaoShopService {

    @Autowired
    private TaobaoSellerMapper taobaoSellerMapper;
    @Autowired
    private TaobaoApiService taobaoApiService;

    public String getNick(Long userId) {
        TaobaoSeller taobaoSeller = taobaoSellerMapper.selectByUserId(userId);
        if (null == taobaoSeller) {
            return null;
        } else {
            return taobaoSeller.getNick();
        }
    }

    public TaobaoSeller getSeller(Long taobaoUserId) {
        TaobaoSeller taobaoSeller = taobaoSellerMapper.selectByUserId(taobaoUserId);
        return taobaoSeller;
    }

    public TaobaoSeller createSeller(String accessToken, String appKey) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        UserSellerGetRequest req = new UserSellerGetRequest();
        req.setFields("nick");
        UserSellerGetResponse response = taobaoApiService.requestWithAppKey(req, appKey, accessToken);
        User user = response.getUser();
        TaobaoSeller taobaoSeller = BeanMapper.map(user, TaobaoSeller.class);
        taobaoSellerMapper.insertSelective(taobaoSeller);
        return taobaoSeller;
    }
}
