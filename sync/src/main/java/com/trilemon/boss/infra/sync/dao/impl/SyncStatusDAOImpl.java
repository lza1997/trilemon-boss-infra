package com.trilemon.boss.infra.sync.dao.impl;

import com.alibaba.cobarclient.MysdalCobarSqlMapClientDaoSupport;
import com.google.common.collect.ImmutableList;
import com.trilemon.boss.infra.sync.dao.SyncStatusDAO;
import com.trilemon.boss.infra.sync.model.SyncStatus;

public class SyncStatusDAOImpl extends MysdalCobarSqlMapClientDaoSupport implements SyncStatusDAO {

    public SyncStatusDAOImpl() {
        super();
    }

    public int deleteByPrimaryKey(Integer id) {
        SyncStatus _key = new SyncStatus();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("sync_status.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(SyncStatus record) {
        getSqlMapClientTemplate().insert("sync_status.insert", record);
    }

    public void insertSelective(SyncStatus record) {
        getSqlMapClientTemplate().insert("sync_status.insertSelective", record);
    }

    public SyncStatus selectByPrimaryKey(Integer id) {
        SyncStatus _key = new SyncStatus();
        _key.setId(id);
        SyncStatus record = (SyncStatus) getSqlMapClientTemplate().queryForObject("sync_status.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByPrimaryKeySelective(SyncStatus record) {
        int rows = getSqlMapClientTemplate().update("sync_status.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(SyncStatus record) {
        int rows = getSqlMapClientTemplate().update("sync_status.updateByPrimaryKey", record);
        return rows;
    }

    @Override
    public SyncStatus selectByUserId(Long userId) {
        SyncStatus _key = new SyncStatus();
        _key.setUserId(userId);
        SyncStatus record = (SyncStatus) getSqlMapClientTemplate().queryForObject("sync_status.selectByUserId",
                _key);
        return record;
    }

    @Override
    public int deleteByRateSyncOwnerAndStatus(String owner, ImmutableList<Byte> statusList) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}