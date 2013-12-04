package com.trilemon.boss.infra.base.dao;

import com.trilemon.boss.infra.base.model.TaobaoSession;

import java.util.List;

public interface TaobaoSessionDAO {
    int deleteByPrimaryKey(Long id);

    void insert(TaobaoSession record);

    void insertSelective(TaobaoSession record);

    TaobaoSession selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaobaoSession record);

    int updateByPrimaryKey(TaobaoSession record);

    TaobaoSession selectByTaobaoUserIdAndAppKey(Long taobaoUserId, Long subTaobaoUserId,String appKey);

    List<Long> paginateIds(Integer offset, Integer limit);

    int updateByTaobaoUserIdAndAppKey(TaobaoSession newTaobaoSession);

    TaobaoSession selectBySubTaobaoUserId(Long subTaobaoUserId, String appKey);
}