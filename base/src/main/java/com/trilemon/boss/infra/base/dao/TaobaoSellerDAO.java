package com.trilemon.boss.infra.base.dao;

import com.trilemon.boss.infra.base.model.TaobaoSeller;

public interface TaobaoSellerDAO {
    int deleteByPrimaryKey(Long userId);

    void insert(TaobaoSeller record);

    void insertSelective(TaobaoSeller record);

    TaobaoSeller selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(TaobaoSeller record);

    int updateByPrimaryKey(TaobaoSeller record);

    void replaceSelective(TaobaoSeller taobaoSeller);

}