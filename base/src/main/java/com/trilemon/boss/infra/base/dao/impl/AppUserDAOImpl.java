package com.trilemon.boss.infra.base.dao.impl;

import com.alibaba.cobarclient.BaseSqlMapClientDaoSupport;
import com.google.common.collect.Maps;
import com.trilemon.boss.infra.base.dao.AppUserDAO;
import com.trilemon.boss.infra.base.model.AppUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AppUserDAOImpl extends BaseSqlMapClientDaoSupport implements AppUserDAO {

    public AppUserDAOImpl() {
        super();
    }

    public int deleteByPrimaryKey(Long id) {
        AppUser _key = new AppUser();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("app_user.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(AppUser record) {
        getSqlMapClientTemplate().insert("app_user.insert", record);
    }

    public void insertSelective(AppUser record) {
        getSqlMapClientTemplate().insert("app_user.insertSelective", record);
    }

    public AppUser selectByPrimaryKey(Long id) {
        AppUser _key = new AppUser();
        _key.setId(id);
        AppUser record = (AppUser) getSqlMapClientTemplate().queryForObject("app_user.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByPrimaryKeySelective(AppUser record) {
        int rows = getSqlMapClientTemplate().update("app_user.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(AppUser record) {
        int rows = getSqlMapClientTemplate().update("app_user.updateByPrimaryKey", record);
        return rows;
    }

    @Override
    public AppUser selectByUserIdAndAppKey(Long userId, String appKey) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("appKey", appKey);
        return (AppUser) getSqlMapClientTemplate().queryForObject("app_user.selectByUserIdAndAppKey", map);
    }

    @Override
    public int updateByUserIdAndAppKeySelective(AppUser appUser) {
        int rows = getSqlMapClientTemplate().update("app_user.updateByUserIdAndAppKeySelective", appUser);
        return rows;
    }

    @Override
    public List<Long> paginateIds(int offset, int limit) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("offset", offset);
        map.put("limit", limit);
        return (List<Long>) getSqlMapClientTemplate().queryForList("app_user.paginateIds", map);
    }

    @Override
    public AppUser selectByUserId(Long userId) {
        AppUser _key = new AppUser();
        _key.setUserId(userId);
        AppUser record = (AppUser) getSqlMapClientTemplate().queryForObject("app_user.selectByUserId", _key);
        return record;
    }
}