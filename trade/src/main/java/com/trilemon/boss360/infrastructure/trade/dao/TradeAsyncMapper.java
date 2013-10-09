package com.trilemon.boss360.infrastructure.trade.dao;

import com.trilemon.boss360.infrastructure.trade.model.TradeAsync;
import com.trilemon.boss360.infrastructure.trade.model.TradeAsyncExample;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface TradeAsyncMapper {
    int countByExample(TradeAsyncExample example);

    int deleteByExample(TradeAsyncExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TradeAsync record);

    int insertSelective(TradeAsync record);

    List<TradeAsync> selectByExample(TradeAsyncExample example);

    TradeAsync selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TradeAsync record, @Param("example") TradeAsyncExample example);

    int updateByExample(@Param("record") TradeAsync record, @Param("example") TradeAsyncExample example);

    int updateByPrimaryKeySelective(TradeAsync record);

    int updateByPrimaryKey(TradeAsync record);

    Collection<TradeAsync> pagination(int offset, int size, Byte syncStatus, String serviceName, String serviceId);

    TradeAsync selectByUserId(long userId);
}