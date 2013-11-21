package com.trilemon.boss.infra.sync.dao;

import com.trilemon.boss.infra.sync.model.SyncRate;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncRateDAO {
    int deleteByPrimaryKey(Long userId);

    void insert(SyncRate record);

    void insertSelective(SyncRate record);

    SyncRate selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SyncRate record);

    int updateByPrimaryKey(SyncRate record);
}