package com.trilemon.boss.infra.base.dao.impl;

import com.alibaba.cobarclient.BaseSqlMapClientDaoSupport;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.trilemon.boss.infra.base.dao.BuyerBlacklistDAO;
import com.trilemon.boss.infra.base.model.BuyerBlacklist;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BuyerBlacklistDAOImpl extends BaseSqlMapClientDaoSupport implements BuyerBlacklistDAO {

    public BuyerBlacklistDAOImpl() {
        super();
    }

    public int deleteByPrimaryKey(Long id) {
        BuyerBlacklist _key = new BuyerBlacklist();
        _key.setId(id);
        return getSqlMapClientTemplate().delete("buyer_blacklist.deleteByPrimaryKey", _key);
    }

    public void insert(BuyerBlacklist record) {
        getSqlMapClientTemplate().insert("buyer_blacklist.insert", record);
    }

    public void insertSelective(BuyerBlacklist record) {
        getSqlMapClientTemplate().insert("buyer_blacklist.insertSelective", record);
    }

    public BuyerBlacklist selectByPrimaryKey(Long id) {
        BuyerBlacklist _key = new BuyerBlacklist();
        _key.setId(id);
        return (BuyerBlacklist) getSqlMapClientTemplate().queryForObject("buyer_blacklist.selectByPrimaryKey", _key);
    }

    public int updateByPrimaryKeySelective(BuyerBlacklist record) {
        return getSqlMapClientTemplate().update("buyer_blacklist.updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(BuyerBlacklist record) {
        return getSqlMapClientTemplate().update("buyer_blacklist.updateByPrimaryKey", record);
    }

    @Override
    public int deleteByUserIdAndBuyerNickAndType(Long userId, String buyerNick, Byte type) {
        Map<String, ?> parameterObject = ImmutableMap.of("userId", userId, "buyerNick", buyerNick, "type", type);
        return getSqlMapClientTemplate().delete("buyer_blacklist.deleteByUserIdAndBuyerNickAndType", parameterObject);
    }

    @Override
    public int updateByUserIdAndBuyerNickAndType(BuyerBlacklist buyerBlacklist) {
        return getSqlMapClientTemplate().update("buyer_blacklist.updateByUserIdAndBuyerNickAndType", buyerBlacklist);
    }

    @Override
    public List<BuyerBlacklist> paginateBuyerBlacklist(Long userId, Byte type, Integer offset, Integer limit, String sortField, String sortType) {
        Map<String,  Object> parameterMap = Maps.newHashMap();
        parameterMap.put("userId", userId);
        parameterMap.put("type", type);
        parameterMap.put("offset", offset);
        parameterMap.put("limit", limit);
        parameterMap.put("sortField", sortField);
        parameterMap.put("sortType", sortType);

        return (List<BuyerBlacklist>)getSqlMapClientTemplate().queryForList("buyer_blacklist.paginateBuyerBlacklist",parameterMap);

    }

    @Override
    public BuyerBlacklist selectByUserIdAndBuyerNick(Long userId, String buyerNick) {
        Map<String,  Object> parameterMap = Maps.newHashMap();
        parameterMap.put("userId", userId);
        parameterMap.put("buyerNick", buyerNick);

        return (BuyerBlacklist)getSqlMapClientTemplate().queryForObject("buyer_blacklist.selectByUserIdAndBuyerNick", parameterMap);
    }

    @Override
    public int batchInsert(List<BuyerBlacklist> buyerBlacklists) {
        return batchInsert("buyer_blacklist.insertSelective",buyerBlacklists);
    }
}