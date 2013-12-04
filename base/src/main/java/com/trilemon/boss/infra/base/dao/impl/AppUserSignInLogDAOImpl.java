package com.trilemon.boss.infra.base.dao.impl;

import com.alibaba.cobarclient.BaseSqlMapClientDaoSupport;
import com.trilemon.boss.infra.base.dao.AppUserSignInLogDAO;
import com.trilemon.boss.infra.base.model.AppUserSignInLog;
import org.springframework.stereotype.Repository;

@Repository
public class AppUserSignInLogDAOImpl extends BaseSqlMapClientDaoSupport implements AppUserSignInLogDAO {

    public AppUserSignInLogDAOImpl() {
        super();
    }

    public int deleteByPrimaryKey(Long id) {
        AppUserSignInLog _key = new AppUserSignInLog();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("app_user_sign_in_log.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(AppUserSignInLog record) {
        getSqlMapClientTemplate().insert("app_user_sign_in_log.insert", record);
    }

    public void insertSelective(AppUserSignInLog record) {
        getSqlMapClientTemplate().insert("app_user_sign_in_log.insertSelective", record);
    }

    public AppUserSignInLog selectByPrimaryKey(Long id) {
        AppUserSignInLog _key = new AppUserSignInLog();
        _key.setId(id);
        AppUserSignInLog record = (AppUserSignInLog) getSqlMapClientTemplate().queryForObject("app_user_sign_in_log.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByPrimaryKeySelective(AppUserSignInLog record) {
        int rows = getSqlMapClientTemplate().update("app_user_sign_in_log.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(AppUserSignInLog record) {
        int rows = getSqlMapClientTemplate().update("app_user_sign_in_log.updateByPrimaryKey", record);
        return rows;
    }
}