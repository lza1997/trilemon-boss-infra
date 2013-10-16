package com.trilemon.boss360.infrastructure.trade.service;

import com.trilemon.boss360.infrastructure.base.client.BaseClient;
import com.trilemon.boss360.infrastructure.base.serivce.AbstractQueueService;
import com.trilemon.boss360.infrastructure.base.serivce.AppService;
import com.trilemon.boss360.infrastructure.base.serivce.api.TaobaoApiShopService;
import com.trilemon.boss360.infrastructure.trade.TradeConstants;
import com.trilemon.boss360.infrastructure.trade.dao.TradeSyncMapper;
import com.trilemon.boss360.infrastructure.trade.model.TradeSync;
import com.trilemon.commons.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author kevin
 */
@Service
public class TradeIncrSyncCheckService extends AbstractQueueService<TradeSync> {
    private static Logger logger = LoggerFactory.getLogger(TradeIncrSyncCheckService.class);
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private TradeIncrSyncService tradeIncrSyncService;
    @Autowired
    private TradeSyncMapper tradeSyncMapper;
    @Autowired
    private AppService appService;
    @Autowired
    private TaobaoApiShopService taobaoApiShopService;

    @PostConstruct
    public void init() {
        reboot();
        startPoll();
    }

    @Override
    public void reboot() {
        super.reboot();
        tradeSyncMapper.updateSyncCheckStatusAndLock(TradeConstants.SYNC_CHECK_STATUS_FAILED, UNLOCK,
                appService.getServiceName(),
                appService.getServiceId());
    }

    @Override
    public void timeout() {
        tradeSyncMapper.updateSyncCheckTimeoutSync(60 * 60 * 5);
    }

    @Override
    public void fillQueue() {
        int offset = 0;
        while (true) {
            Collection<TradeSync> tradeSyncList = tradeSyncMapper.paginationSyncCheck(offset, 100);
            if (CollectionUtils.isEmpty(tradeSyncList)) {
                break;
            } else {
                for (TradeSync tradeSync : tradeSyncList) {
                    if (isInitSyncCheck(tradeSync) && lock(tradeSync)) {
                        fillQueue(tradeSync);
                    }
                }
            }
        }
    }

    public boolean lock(TradeSync tradeSync) {
        int rows = tradeSyncMapper.updateSyncCheckLock(tradeSync.getId(), LOCK, appService.getServiceName(),
                appService.getServiceId());
        return rows == 1;
    }

    public boolean unlock(TradeSync tradeSync) {
        int rows = tradeSyncMapper.updateSyncCheckLock(tradeSync.getId(), UNLOCK, appService.getServiceName(),
                appService.getServiceId());
        return rows == 1;
    }

    public boolean isInitSyncCheck(TradeSync tradeSync) {
        return (tradeSync.getSyncStatus() == TradeConstants.SYNC_CHECK_STATUS_SUCCESSFUL)
                || (tradeSync.getSyncStatus() == TradeConstants.SYNC_CHECK_STATUS_FAILED);
    }

    @Override
    public void process(TradeSync tradeSync) throws Exception {
        DateTime tradeStartDateTime = null;
        if (tradeSync.getSyncStatus() == TradeConstants.SYNC_CHECK_STATUS_SUCCESSFUL) {
            tradeStartDateTime = new DateTime(tradeSync.getCheckTradeEndTime());
        }
        if (tradeSync.getSyncStatus() == TradeConstants.ASYNC_STATUS_FAILED) {
            tradeStartDateTime = new DateTime(tradeSync.getCheckTradeStartTime());
        }
        if (null == tradeStartDateTime) {
            try {
                throw new Exception("process error, tradeStartDateTime is null.");
            } catch (Exception e) {
                fail(tradeSync);
                throw e;
            }
        }

        DateTime tradeEndDateTime = new DateTime(tradeSync.getSyncTradeEndTime());
        //只能获取3个月以内的数据
        if (Hours.hoursBetween(tradeStartDateTime, tradeEndDateTime).getHours() > 90 * 24) {
            throw new Exception("tradeSync[" + tradeSync.getId() + "], startTime[" + tradeStartDateTime.toString(DateUtils
                    .yyyy_MM_dd_HH_mm_ss) + "], " +
                    "endTime[" + tradeEndDateTime.toString(DateUtils.yyyy_MM_dd_HH_mm_ss) + "] greater than 90 days.");
        }

        if (Minutes.minutesBetween(tradeStartDateTime, tradeEndDateTime).getMinutes() < 60) {
            logger.info("tradeSync[], startTime[{}], endTime[{}], interval < 60 min, skip.", tradeSync.getId(),
                    tradeStartDateTime.toString(DateUtils.yyyy_MM_dd_HH_mm_ss), tradeEndDateTime.toString(DateUtils.yyyy_MM_dd_HH_mm_ss));
            unlock(tradeSync);
            return;
        }

        List<Interval> checkTimeIntervals = DateUtils.partitionByMinute(tradeStartDateTime, tradeEndDateTime, 60 * 24);

        for (Interval checkTimeInterval : checkTimeIntervals) {
            try {
                checkAndFix(tradeSync, checkTimeInterval.getStart().toDate(), checkTimeInterval.getEnd().toDate());
            } catch (Exception e) {
                fail(tradeSync);
                throw e;
            }
        }
    }

    private void fail(TradeSync tradeSync) {
        TradeSync newTradeSync = new TradeSync();
        newTradeSync.setId(tradeSync.getId());
        newTradeSync.setCheckLock(UNLOCK);
        newTradeSync.setCheckStatus(TradeConstants.SYNC_CHECK_STATUS_FAILED);
        newTradeSync.setSyncEndTime(appService.getLocalSystemTime().toDate());
        tradeSyncMapper.updateByPrimaryKey(newTradeSync);
    }

    public void checkAndFix(TradeSync tradeSync, Date startDate,
                            Date endDate) throws Exception {
        int fixRetry = 2;
        int fixRetryCount = 1;

        while (true) {
            try {
                check(tradeSync, startDate, endDate);
                break;
            } catch (Exception e) {
                if (fixRetryCount > fixRetry) {
                    throw e;
                } else {
                    logger.error("try to fix, check sync retry [" + fixRetryCount + "/" + fixRetry + "] times", e);
                    fixRetryCount++;
                    try {
                        tradeIncrSyncService.syncByCreated(tradeSync.getUserId(), tradeSync.getSyncAppKey(), startDate, endDate);
                    } catch (Exception e1) {
                        logger.error(e1.getMessage());
                    }
                }
            }
        }
    }

    public void check(TradeSync tradeSync, Date startDate, Date endDate) throws Exception {
        long topTradeNum = taobaoApiShopService.getTradeNumFromTop(tradeSync.getUserId(), tradeSync.getSyncAppKey(), startDate, endDate);
        long ourTradeNum = baseClient.getTradeNum(tradeSync.getUserId(), startDate, endDate);
        if (ourTradeNum >= topTradeNum) {
            if (ourTradeNum > topTradeNum) {
            } else {
                throw new Exception("ourTradeNum[" + ourTradeNum + "] is not equal topTradeNum[" + topTradeNum + "].");
            }
        }
    }
}
