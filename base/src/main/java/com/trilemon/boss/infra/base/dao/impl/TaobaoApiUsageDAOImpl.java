package com.trilemon.boss.infra.base.dao.impl;

import com.alibaba.cobarclient.BaseSqlMapClientDaoSupport;
import com.trilemon.boss.infra.base.dao.TaobaoApiUsageDAO;
import com.trilemon.boss.infra.base.model.TaobaoApiUsage;
import org.springframework.stereotype.Repository;

@Repository
public class TaobaoApiUsageDAOImpl extends BaseSqlMapClientDaoSupport implements TaobaoApiUsageDAO {

    public TaobaoApiUsageDAOImpl() {
        super();
    }

    public int deleteByPrimaryKey(Integer id) {
        TaobaoApiUsage _key = new TaobaoApiUsage();
        _key.setId(id);
        return getSqlMapClientTemplate().delete("taobao_api_usage.deleteByPrimaryKey", _key);
    }

    public void insert(TaobaoApiUsage record) {
        getSqlMapClientTemplate().insert("taobao_api_usage.insert", record);
    }

    public void insertSelective(TaobaoApiUsage record) {
        getSqlMapClientTemplate().insert("taobao_api_usage.insertSelective", record);
    }

    public TaobaoApiUsage selectByPrimaryKey(Integer id) {
        TaobaoApiUsage _key = new TaobaoApiUsage();
        _key.setId(id);
        return (TaobaoApiUsage) getSqlMapClientTemplate().queryForObject("taobao_api_usage.selectByPrimaryKey", _key);
    }

    public int updateByPrimaryKeySelective(TaobaoApiUsage record) {
        return getSqlMapClientTemplate().update("taobao_api_usage.updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(TaobaoApiUsage record) {
        return getSqlMapClientTemplate().update("taobao_api_usage.updateByPrimaryKey", record);
    }
}