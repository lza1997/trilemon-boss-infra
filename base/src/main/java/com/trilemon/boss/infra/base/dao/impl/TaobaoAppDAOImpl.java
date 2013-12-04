package com.trilemon.boss.infra.base.dao.impl;

import com.alibaba.cobarclient.BaseSqlMapClientDaoSupport;
import com.trilemon.boss.infra.base.dao.TaobaoAppDAO;
import com.trilemon.boss.infra.base.model.TaobaoApp;
import org.springframework.stereotype.Repository;

@Repository
public class TaobaoAppDAOImpl extends BaseSqlMapClientDaoSupport implements TaobaoAppDAO {

    public TaobaoAppDAOImpl() {
        super();
    }

    public int deleteByPrimaryKey(Integer id) {
        TaobaoApp _key = new TaobaoApp();
        _key.setId(id);
        return getSqlMapClientTemplate().delete("taobao_app.deleteByPrimaryKey", _key);
    }

    public void insert(TaobaoApp record) {
        getSqlMapClientTemplate().insert("taobao_app.insert", record);
    }

    public void insertSelective(TaobaoApp record) {
        getSqlMapClientTemplate().insert("taobao_app.insertSelective", record);
    }

    public TaobaoApp selectByPrimaryKey(Integer id) {
        TaobaoApp _key = new TaobaoApp();
        _key.setId(id);
        return (TaobaoApp) getSqlMapClientTemplate().queryForObject("taobao_app.selectByPrimaryKey", _key);
    }

    public int updateByPrimaryKeySelective(TaobaoApp record) {
        return getSqlMapClientTemplate().update("taobao_app.updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(TaobaoApp record) {
        return getSqlMapClientTemplate().update("taobao_app.updateByPrimaryKey", record);
    }

    @Override
    public TaobaoApp selectByAppKey(String taobaoAppKey) {
        return (TaobaoApp) getSqlMapClientTemplate().queryForObject("taobao_app.selectByAppKey", taobaoAppKey);
    }
}