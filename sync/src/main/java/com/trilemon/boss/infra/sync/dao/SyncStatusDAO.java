package com.trilemon.boss.infra.sync.dao;

import com.trilemon.boss.infra.sync.model.SyncStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncStatusDAO {
    int deleteByPrimaryKey(Integer id);

    void insert(SyncStatus record);

    void insertSelective(SyncStatus record);

    SyncStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SyncStatus record);

    int updateByPrimaryKey(SyncStatus record);
}