package com.trilemon.boss.infra.base.dao;

import com.trilemon.boss.infra.base.model.TaobaoShop;

public interface TaobaoShopDAO {
    int deleteByPrimaryKey(Long userId);

    void insert(TaobaoShop record);

    void insertSelective(TaobaoShop record);

    TaobaoShop selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(TaobaoShop record);

    int updateByPrimaryKey(TaobaoShop record);

    void replaceSelective(TaobaoShop taobaoShop);
}