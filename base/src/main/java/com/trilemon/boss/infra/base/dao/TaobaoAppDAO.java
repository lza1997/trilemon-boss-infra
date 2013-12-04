package com.trilemon.boss.infra.base.dao;

import com.trilemon.boss.infra.base.model.TaobaoApp;

public interface TaobaoAppDAO {
    int deleteByPrimaryKey(Integer id);

    void insert(TaobaoApp record);

    void insertSelective(TaobaoApp record);

    TaobaoApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaobaoApp record);

    int updateByPrimaryKey(TaobaoApp record);
    TaobaoApp selectByAppKey(String taobaoAppKey);
}