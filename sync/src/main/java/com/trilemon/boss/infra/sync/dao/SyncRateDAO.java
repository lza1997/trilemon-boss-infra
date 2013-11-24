package com.trilemon.boss.infra.sync.dao;

import com.trilemon.boss.infra.sync.model.SyncRate;

import java.util.List;

public interface SyncRateDAO {
    int deleteByPrimaryKey(Long userId);

    void insert(SyncRate record);

    void insertSelective(SyncRate record);

    SyncRate selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SyncRate record);

    int updateByPrimaryKey(SyncRate record);

    int batchInsertSelective(List<SyncRate> records);
}