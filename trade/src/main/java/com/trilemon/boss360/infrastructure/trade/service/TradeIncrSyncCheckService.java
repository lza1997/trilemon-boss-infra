package com.trilemon.boss360.infrastructure.trade.service;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author kevin
 */
public class TradeIncrSyncCheckService {
    private static Logger logger = LoggerFactory.getLogger(TradeIncrSyncCheckService.class);

    @Autowired
    private ShopService shopService;

    /**
     * 检查订单的完整性，是否丢单
     */
    @Override
    public void checkImmediately(long shopId) {
        Date lastCheckStartTime = null;

        //第一次检查，从全量同步开始时间检查
        if (shopSync.getTradeSyncCheckStatus() == ShopSync.TRADE_SYNC_CHECK_STATUS_INIT) {
            if (shopSync.getTradeFullSyncStatus() == ShopSync.TRADE_FULL_SYNC_STATUS_SUCCESSFUL) {
                lastCheckStartTime = shopSync.getLastTradeFullSyncStartTime();
                Date lastCheckEndTime = shopSync.getLastTradeFullSyncEndTime();
                if (null != lastCheckStartTime && null != lastCheckEndTime) {
                    try {
                        checkImmediatelyWithTimeRange(shopId, new DateTime(lastCheckStartTime), new DateTime(lastCheckEndTime));
                    } catch (Exception e) {
                        shopService.releaseTradeIntegrityCheckLock(shopId);
                    }
                } else {
                    logger.info("shopId[{}] last check failed[init mode] , " +
                            "lastCheckStartTime[{}] lastCheckEndTime[{}]",
                            shopId,
                            lastCheckStartTime, lastCheckEndTime);
                }
            }
            shopService.releaseTradeIntegrityCheckLock(shopId);
            return;
        }

        if (shopSync.getTradeSyncCheckStatus() == ShopSync.TRADE_SYNC_CHECK_STATUS_SUCCESSFUL) {
            Date checkTime = shopSync.getLastTradeSyncCheckEndTime();
            if (null != checkTime) {
                lastCheckStartTime = checkTime;
            }
        }

        if (shopSync.getTradeSyncCheckStatus() == ShopSync.TRADE_SYNC_CHECK_STATUS_FAIL) {
            logger.info("shopId[{}] last check failed , check from last check time[{}]", shopId,
                    ManyUtils.to_yyyy_MM_dd_HH_mm_ss_String(shopSync.getLastTradeSyncCheckStartTime()));
            Date checkTime = shopSync.getLastTradeSyncCheckStartTime();
            if (null != checkTime) {
                lastCheckStartTime = checkTime;
            }
        }

        if (null == lastCheckStartTime) {
            logger.info("shopId[{}] last check failed , last check time is null", shopId);
            shopService.releaseTradeIntegrityCheckLock(shopId);
            return;
        }

        //检查时间间隔
        DateTime thisCheckRoundTime = systemService.getLocalSystemTime().minusMinutes(5);

        //和增量同步时间比较，取最近的时间
        Date lastTradeIncrementalSyncTime = getLastTradeIncrementalSyncTime(shopSync);
        if (null == lastTradeIncrementalSyncTime) {
            logger.info("shopId[{}] last incremental end time is null , skip check.", shopId);
            shopService.releaseTradeIntegrityCheckLock(shopId);
            return;
        }
        if (lastTradeIncrementalSyncTime.getTime() < thisCheckRoundTime.toDate().getTime()) {
            thisCheckRoundTime = new DateTime(lastTradeIncrementalSyncTime);
        }

        try {
            checkImmediatelyWithTimeRange(shopId, new DateTime(lastCheckStartTime), thisCheckRoundTime);
        } catch (Exception e) {
            shopService.releaseTradeIntegrityCheckLock(shopId);
        }
    }

    public void checkImmediatelyWithTimeRange(long shopId, DateTime startTime, DateTime endTime) throws Exception {
        //只能获取3个月以内的数据
        if (Hours.hoursBetween(startTime, endTime).getHours() > 90 * 24) {
            startTime = endTime.minusDays(90);
        }

        int checkSyncInterval = 60;
        try {
            checkSyncInterval = configService.getInt(ConfigService
                    .CHECK_SYNC_INTERVAL, 60);
        } catch (ConfigException e) {
            logger.error("get check interval error,use default.", e);
        }

        if (Minutes.minutesBetween(startTime, endTime).getMinutes() < checkSyncInterval) {
            shopService.releaseTradeIntegrityCheckLock(shopId);
            return;
        }
        logger.info("start check shopId[{}] startTime[{}] endTime[{}]", shopId,
                ManyUtils.to_yyyy_MM_dd_HH_mm_ss_String(startTime), ManyUtils.to_yyyy_MM_dd_HH_mm_ss_String
                (endTime));

        List<Interval> checkTimeIntervals = ManyUtils.splitByMinute(startTime, endTime, 60 * 24);

        for (Interval checkTimeInterval : checkTimeIntervals) {
            try {
                checkAndFix(shopId, checkTimeInterval.getStart(), checkTimeInterval.getEnd());
            } catch (Exception e) {
                logger.error("check error shopId[" + shopId + "] startTime[" + ManyUtils.to_yyyy_MM_dd_HH_mm_ss_String
                        (checkTimeInterval.getStart()) + "] endTime[" + ManyUtils.to_yyyy_MM_dd_HH_mm_ss_String
                        (checkTimeInterval.getEnd()) + "]", e.getMessage());
                shopService.releaseTradeIntegrityCheckLock(shopId);
            } finally {
                logger.info("end check shopId[{}] startTime[{}] endTime[{}]", shopId,
                        ManyUtils.to_yyyy_MM_dd_HH_mm_ss_String(checkTimeInterval.getStart()),
                        ManyUtils.to_yyyy_MM_dd_HH_mm_ss_String
                                (checkTimeInterval.getEnd()));
            }
        }
    }

    private Date getLastTradeIncrementalSyncTime(ShopSync shopSync) {
        switch (shopSync.getTradeIncrementalSyncStatus()) {
            case ShopSync.LAST_TRADE_INCREMENTAL_SYNC_STATUS_FAILED:
                return shopSync.getLastTradeIncrementalSyncStartTime();
            case ShopSync.LAST_TRADE_INCREMENTAL_SYNC_STATUS_SUCCESSFUL:
                return shopSync.getLastTradeIncrementalSyncEndTime();
            case ShopSync.LAST_TRADE_INCREMENTAL_SYNC_STATUS_SYNCING:
                return shopSync.getLastTradeIncrementalSyncStartTime();
        }
        return null;
    }

    public void checkAndFix(long shopId, DateTime startTime,
                            DateTime endTime) throws Exception {
        int fixRetry = 2;
        int fixRetryCount = 1;
        shopService.updateTradeSyncCheck(shopId, ShopSync.TRADE_SYNC_CHECK_STATUS_CHECKING, null, startTime, endTime);

        while (true) {
            try {
                checkImmediately(shopId, startTime, endTime);
                break;
            } catch (Exception e) {
                if (fixRetryCount > fixRetry) {
                    shopService.updateTradeSyncCheck(shopId, ShopSync.TRADE_SYNC_CHECK_STATUS_FAIL, null, null, null);
                    throw e;
                } else {
                    logger.error("try to fix , check sync retry [" + fixRetryCount + "/" + fixRetry + "] times", e);
                    fixRetryCount++;
                    try {
                        tradeSyncService.syncImmediately(shopId, startTime, endTime, TradeSyncService.SYNC_BY_CREATED);
                    } catch (Exception e1) {
                        logger.error(e1.getMessage());
                    }
                }
            }
        }
    }

    public void Check(long shopId, DateTime startHour,
                                 DateTime endHour) throws DisIntegrityTradeException {
        long topTradeNum;
        try {
            topTradeNum = shopService.getTopTradeNum(shopId, startHour, endHour);
        } catch (Exception e) {
            throw new DisIntegrityTradeException("can not get topTradeNum. shopId[" + shopId + "] startHour[" +
                    ManyUtils
                            .to_yyyy_MM_dd_HH_mm_ss_String(startHour) + "] endHour[" + ManyUtils
                    .to_yyyy_MM_dd_HH_mm_ss_String(startHour) + "]", e);
        }
        long ourTradeNum = shopService.getTradeNum(shopId, startHour, endHour);
        if (ourTradeNum >= topTradeNum) {
            if (ourTradeNum > topTradeNum) {
                logger.warn("ourTradeNum[{}] is more than topTradeNum[{}] shopId[{}] startHour[{}] endHour[{}] ",
                        ourTradeNum,
                        topTradeNum, shopId, startHour, endHour);
            }
            shopService.updateTradeSyncCheck(shopId, ShopSync.TRADE_SYNC_CHECK_STATUS_SUCCESSFUL, null, null, null);
        } else {
            throw new DisIntegrityTradeException("shopId[" + shopId + "] startTime[" + ManyUtils
                    .to_yyyy_MM_dd_HH_mm_ss_String(startHour)
                    + "] endTime[" + ManyUtils
                    .to_yyyy_MM_dd_HH_mm_ss_String(endHour) + "] " +
                    "topTradeNum[" + topTradeNum + "] " +
                    "ourTradeNum[" + ourTradeNum + "]");
        }
    }
}
