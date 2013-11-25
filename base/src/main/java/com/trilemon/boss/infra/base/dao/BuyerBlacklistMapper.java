package com.trilemon.boss.infra.base.dao;

import com.trilemon.boss.infra.base.model.BuyerBlacklist;
import com.trilemon.boss.infra.base.model.BuyerBlacklistExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BuyerBlacklistMapper {
    int countByExample(BuyerBlacklistExample example);

    int deleteByExample(BuyerBlacklistExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BuyerBlacklist record);

    int insertSelective(BuyerBlacklist record);

    List<BuyerBlacklist> selectByExample(BuyerBlacklistExample example);

    BuyerBlacklist selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BuyerBlacklist record, @Param("example") BuyerBlacklistExample example);

    int updateByExample(@Param("record") BuyerBlacklist record, @Param("example") BuyerBlacklistExample example);

    int updateByPrimaryKeySelective(BuyerBlacklist record);

    int updateByPrimaryKey(BuyerBlacklist record);

    void deleteByUserIdAndBuyerNickAndType(@Param("userId") Long userId, @Param("buyerNick") String buyerNick,
                                           @Param("type") Byte type);

    void updateByUserIdAndBuyerNickAndType(BuyerBlacklist buyerBlacklist);
}