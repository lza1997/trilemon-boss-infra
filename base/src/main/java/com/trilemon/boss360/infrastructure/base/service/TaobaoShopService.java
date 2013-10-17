package com.trilemon.boss360.infrastructure.base.service;

import com.trilemon.boss360.infrastructure.base.dao.TaobaoSellerMapper;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSeller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kevin
 */
@Service
public class TaobaoShopService {

    @Autowired
    private TaobaoSellerMapper taobaoSellerMapper;

    /**
     * dadfasdfd
     * @param userId
     * @return
     */
    public String getNick(Long userId) {
        TaobaoSeller taobaoSeller = taobaoSellerMapper.selectByUserId(userId);
        if (null == taobaoSeller) {
            return null;
        } else {
            return taobaoSeller.getNick();
        }
    }
}
