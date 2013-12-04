package com.trilemon.boss.infra.base.dao.impl;

import com.alibaba.cobarclient.BaseSqlMapClientDaoSupport;
import com.trilemon.boss.infra.base.dao.TaobaoSellerDAO;
import com.trilemon.boss.infra.base.model.TaobaoSeller;
import org.springframework.stereotype.Repository;

@Repository
public class TaobaoSellerDAOImpl extends BaseSqlMapClientDaoSupport implements TaobaoSellerDAO {

    public TaobaoSellerDAOImpl() {
        super();
    }

    public int deleteByPrimaryKey(Long userId) {
        TaobaoSeller _key = new TaobaoSeller();
        _key.setUserId(userId);
        int rows = getSqlMapClientTemplate().delete("taobao_seller.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(TaobaoSeller record) {
        getSqlMapClientTemplate().insert("taobao_seller.insert", record);
    }

    public void insertSelective(TaobaoSeller record) {
        getSqlMapClientTemplate().insert("taobao_seller.insertSelective", record);
    }

    public TaobaoSeller selectByPrimaryKey(Long userId) {
        TaobaoSeller _key = new TaobaoSeller();
        _key.setUserId(userId);
        TaobaoSeller record = (TaobaoSeller) getSqlMapClientTemplate().queryForObject("taobao_seller.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByPrimaryKeySelective(TaobaoSeller record) {
        int rows = getSqlMapClientTemplate().update("taobao_seller.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(TaobaoSeller record) {
        int rows = getSqlMapClientTemplate().update("taobao_seller.updateByPrimaryKey", record);
        return rows;
    }

    @Override
    public void replaceSelective(TaobaoSeller taobaoSeller) {
        getSqlMapClientTemplate().insert("taobao_seller.replaceSelective", taobaoSeller);
    }
}