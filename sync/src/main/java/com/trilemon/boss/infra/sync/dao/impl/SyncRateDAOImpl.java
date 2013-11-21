package com.trilemon.boss.infra.sync.dao.impl;

import com.alibaba.cobarclient.MysdalCobarSqlMapClientDaoSupport;
import com.trilemon.boss.infra.sync.dao.SyncRateDAO;
import com.trilemon.boss.infra.sync.model.SyncRate;

public class SyncRateDAOImpl extends MysdalCobarSqlMapClientDaoSupport implements SyncRateDAO {

    public SyncRateDAOImpl() {
        super();
    }

    public int deleteByPrimaryKey(Long userId) {
        SyncRate _key = new SyncRate();
        _key.setUserId(userId);
        int rows = getSqlMapClientTemplate().delete("sync_rate.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(SyncRate record) {
        getSqlMapClientTemplate().insert("sync_rate.insert", record);
    }

    public void insertSelective(SyncRate record) {
        getSqlMapClientTemplate().insert("sync_rate.insertSelective", record);
    }

    public SyncRate selectByPrimaryKey(Long userId) {
        SyncRate _key = new SyncRate();
        _key.setUserId(userId);
        SyncRate record = (SyncRate) getSqlMapClientTemplate().queryForObject("sync_rate.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByPrimaryKeySelective(SyncRate record) {
        int rows = getSqlMapClientTemplate().update("sync_rate.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(SyncRate record) {
        int rows = getSqlMapClientTemplate().update("sync_rate.updateByPrimaryKey", record);
        return rows;
    }
}