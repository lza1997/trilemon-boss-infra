package com.trilemon.boss.infra.base.dao.impl;

import com.alibaba.cobarclient.BaseSqlMapClientDaoSupport;
import com.google.common.collect.Maps;
import com.trilemon.boss.infra.base.dao.TaobaoSessionDAO;
import com.trilemon.boss.infra.base.model.TaobaoSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TaobaoSessionDAOImpl extends BaseSqlMapClientDaoSupport implements TaobaoSessionDAO {

    public TaobaoSessionDAOImpl() {
        super();
    }

    public int deleteByPrimaryKey(Long id) {
        TaobaoSession _key = new TaobaoSession();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("taobao_session.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(TaobaoSession record) {
        getSqlMapClientTemplate().insert("taobao_session.insert", record);
    }

    public void insertSelective(TaobaoSession record) {
        getSqlMapClientTemplate().insert("taobao_session.insertSelective", record);
    }

    public TaobaoSession selectByPrimaryKey(Long id) {
        TaobaoSession _key = new TaobaoSession();
        _key.setId(id);
        return (TaobaoSession) getSqlMapClientTemplate().queryForObject("taobao_session.selectByPrimaryKey", _key);
    }

    public int updateByPrimaryKeySelective(TaobaoSession record) {
        return getSqlMapClientTemplate().update("taobao_session.updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(TaobaoSession record) {
        return getSqlMapClientTemplate().update("taobao_session.updateByPrimaryKey", record);
    }

    @Override
    public TaobaoSession selectByTaobaoUserIdAndAppKey(Long taobaoUserId, Long subTaobaoUserId, String appKey) {
        Map<String, Object> parameterMap = Maps.newHashMap();
        parameterMap.put("taobaoUserId", taobaoUserId);
        parameterMap.put("subTaobaoUserId", subTaobaoUserId);
        parameterMap.put("appKey", appKey);
        return (TaobaoSession) getSqlMapClientTemplate().queryForObject("taobao_session.selectByTaobaoUserIdAndAppKey", parameterMap);
    }

    @Override
    public List<Long> paginateIds(Integer offset, Integer limit) {
        Map<String, Object> parameterMap = Maps.newHashMap();
        parameterMap.put("offset", offset);
        parameterMap.put("limit", limit);
        return (List<Long>) getSqlMapClientTemplate().queryForList("taobao_session.paginateIds", parameterMap);
    }

    @Override
    public int updateByTaobaoUserIdAndAppKey(TaobaoSession newTaobaoSession) {
        return getSqlMapClientTemplate().update("taobao_session.updateByTaobaoUserIdAndAppKey", newTaobaoSession);
    }

    @Override
    public TaobaoSession selectBySubTaobaoUserId(Long subTaobaoUserId, String appKey) {
        Map<String, Object> parameterMap = Maps.newHashMap();
        parameterMap.put("subTaobaoUserId", subTaobaoUserId);
        parameterMap.put("appKey", appKey);
        return (TaobaoSession) getSqlMapClientTemplate().queryForObject("taobao_session.selectBySubTaobaoUserId", parameterMap);
    }
}