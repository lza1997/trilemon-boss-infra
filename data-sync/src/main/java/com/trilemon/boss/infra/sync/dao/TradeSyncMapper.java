package com.trilemon.boss.infra.trade.dao;

import com.trilemon.boss.infra.trade.model.TradeSync;
import com.trilemon.boss.infra.trade.model.TradeSyncExample;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface TradeSyncMapper {
    int countByExample(TradeSyncExample example);

    int deleteByExample(TradeSyncExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TradeSync record);

    int insertSelective(TradeSync record);

    List<TradeSync> selectByExample(TradeSyncExample example);

    TradeSync selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TradeSync record, @Param("example") TradeSyncExample example);

    int updateByExample(@Param("record") TradeSync record, @Param("example") TradeSyncExample example);

    int updateByPrimaryKeySelective(TradeSync record);

    int updateByPrimaryKey(TradeSync record);

    void updateSyncCheckStatusAndLock(byte syncCheckStatus, Byte unlock, String serviceName, String serviceId);

    void updateSyncCheckTimeoutSync(int timeout);

    Collection<TradeSync> paginationSyncCheck(int offset, int size);

    int updateSyncCheckLock(Long id, Byte lock, String serviceName, String serviceId);

    void updateSyncStatusAndLock(byte syncStatusFailed, Byte unlock, String serviceName, String serviceId);

    void updateSyncTimeoutSync(int timeout);

    Collection<TradeSync> paginationSync(int offset, int size);

    int updateSyncLock(Long id, Byte lock, String serviceName, String serviceId);
}