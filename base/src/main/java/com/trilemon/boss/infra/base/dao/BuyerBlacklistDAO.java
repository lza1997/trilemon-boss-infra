package com.trilemon.boss.infra.base.dao;

import com.trilemon.boss.infra.base.model.BuyerBlacklist;

import java.util.List;

public interface BuyerBlacklistDAO {
    int deleteByPrimaryKey(Long id);

    void insert(BuyerBlacklist record);

    void insertSelective(BuyerBlacklist record);

    BuyerBlacklist selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BuyerBlacklist record);

    int updateByPrimaryKey(BuyerBlacklist record);

    int deleteByUserIdAndBuyerNickAndType(Long userId, String buyerNick, Byte type);

    int updateByUserIdAndBuyerNickAndType(BuyerBlacklist buyerBlacklist);

    List<BuyerBlacklist> paginateBuyerBlacklist(Long userId,
                                                Byte type,
                                                Integer offset,
                                                Integer limit,
                                                String sortField,
                                                String sortType);

    BuyerBlacklist selectByUserIdAndBuyerNick(Long userId, String buyerNick);

    int batchInsert(List<BuyerBlacklist> buyerBlacklists);
}