package com.trilemon.boss.infra.trade.dao;

import com.trilemon.boss.infra.trade.model.TradeAsync;
import com.trilemon.boss.infra.trade.model.TradeAsyncExample;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface TradeAsyncMapper {
    int countByExample(TradeAsyncExample example);

    int deleteByExample(TradeAsyncExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TradeAsync record);

    int insertSelective(TradeAsync record);

    List<TradeAsync> selectByExample(TradeAsyncExample example);

    TradeAsync selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TradeAsync record, @Param("example") TradeAsyncExample example);

    int updateByExample(@Param("record") TradeAsync record, @Param("example") TradeAsyncExample example);

    int updateByPrimaryKeySelective(TradeAsync record);

    int updateByPrimaryKey(TradeAsync record);

    TradeAsync selectByUserId(Long userId);

    void updateSyncStatusByService(@Param("syncStatus") byte syncStatus,
                                   @Param("serviceName") String serviceName,
                                   @Param("serviceId") String serviceId);

    void updateTimeoutSyncStatus(@Param("syncStatus") byte syncStatus, @Param("timeout") int timeout);

    Collection<TradeAsync> pagination(@Param("offset") int offset,
                                      @Param("size") int size,
                                      @Param("syncStatus") byte syncStatus,
                                      @Param("serviceName") String serviceName,
                                      @Param("serviceId") String serviceId);

    void updateByUserId(TradeAsync tradeAsync);
}