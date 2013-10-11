package com.trilemon.boss360.infrastructure.trade.dao;

import com.trilemon.boss360.infrastructure.trade.model.TradeSync;
import com.trilemon.boss360.infrastructure.trade.model.TradeSyncExample;
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

    /**
     * default 或者失败状态
     *
     * @param offset
     * @param size
     * @return
     */
    Collection<TradeSync> paginationSync(int offset, int size);

    int updateSyncLock(Long id, Byte lock, String serviceName, String serviceId);

    void updateSyncStatus(Byte syncStatus, String serviceName, String serviceId);

    /**
     * @param timeout 单位秒
     */
    void updateSyncTimeoutSync(int timeout);

    void updateSyncStatusAndLock(Byte syncStatus, Byte lock, String serviceName, String serviceId);

    void updateSyncCheckStatusAndLock(byte syncCheckStatusFailed, Byte unlock, String serviceName, String serviceId);

    void updateSyncCheckTimeoutSync(int timeout);

    Collection<TradeSync> paginationCheck(int offset, int size);

    int updateSyncCheckLock(Long id, Byte lock, String serviceName, String serviceId);
}