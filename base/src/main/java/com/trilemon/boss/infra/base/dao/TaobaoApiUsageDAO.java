package com.trilemon.boss.infra.base.dao;

import com.trilemon.boss.infra.base.model.TaobaoApiUsage;

public interface TaobaoApiUsageDAO {
    int deleteByPrimaryKey(Integer id);

    void insert(TaobaoApiUsage record);

    void insertSelective(TaobaoApiUsage record);

    TaobaoApiUsage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaobaoApiUsage record);

    int updateByPrimaryKey(TaobaoApiUsage record);
}