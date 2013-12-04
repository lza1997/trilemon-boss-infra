package com.trilemon.boss.infra.base.dao.impl;

import com.alibaba.cobarclient.BaseSqlMapClientDaoSupport;
import com.trilemon.boss.infra.base.dao.TaobaoShopDAO;
import com.trilemon.boss.infra.base.model.TaobaoShop;
import org.springframework.stereotype.Repository;

@Repository
public class TaobaoShopDAOImpl extends BaseSqlMapClientDaoSupport implements TaobaoShopDAO {

    public TaobaoShopDAOImpl() {
        super();
    }

    public int deleteByPrimaryKey(Long userId) {
        TaobaoShop _key = new TaobaoShop();
        _key.setUserId(userId);
        int rows = getSqlMapClientTemplate().delete("taobao_shop.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(TaobaoShop record) {
        getSqlMapClientTemplate().insert("taobao_shop.insert", record);
    }

    public void insertSelective(TaobaoShop record) {
        getSqlMapClientTemplate().insert("taobao_shop.insertSelective", record);
    }

    public TaobaoShop selectByPrimaryKey(Long userId) {
        TaobaoShop _key = new TaobaoShop();
        _key.setUserId(userId);
        TaobaoShop record = (TaobaoShop) getSqlMapClientTemplate().queryForObject("taobao_shop.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByPrimaryKeySelective(TaobaoShop record) {
        int rows = getSqlMapClientTemplate().update("taobao_shop.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(TaobaoShop record) {
        int rows = getSqlMapClientTemplate().update("taobao_shop.updateByPrimaryKey", record);
        return rows;
    }

    @Override
    public void replaceSelective(TaobaoShop taobaoShop) {
        getSqlMapClientTemplate().insert("taobao_shop.replaceSelective", taobaoShop);
    }
}