package com.trilemon.boss360.infrastructure.trade.service;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.taobao.api.domain.Refund;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.RefundsReceiveGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.request.TradesSoldIncrementGetRequest;
import com.taobao.api.response.RefundsReceiveGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.taobao.api.response.TradesSoldIncrementGetResponse;
import com.trilemon.boss360.infrastructure.base.BaseConstants;
import com.trilemon.boss360.infrastructure.base.client.BaseClient;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;
import com.trilemon.boss360.infrastructure.base.service.AbstractQueueService;
import com.trilemon.boss360.infrastructure.base.service.AppService;
import com.trilemon.boss360.infrastructure.base.service.TaobaoApiService;
import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoEnhancedApiException;
import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoTaobaoEnhancedApiException;
import com.trilemon.boss360.infrastructure.sync.Constants;
import com.trilemon.boss360.infrastructure.sync.dao.TradeAsyncMapper;
import com.trilemon.boss360.infrastructure.sync.dao.TradeSyncMapper;
import com.trilemon.boss360.infrastructure.sync.model.TradeAsync;
import com.trilemon.boss360.infrastructure.sync.model.TradeSync;
import com.trilemon.commons.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
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
import java.util.concurrent.TimeUnit;

/**
 * @author kevin
 */
@Service
public class TradeIncrSyncService extends AbstractQueueService<TradeSync> {
    private static Logger logger = LoggerFactory.getLogger(TradeIncrSyncService.class);
    @Autowired
    private TaobaoApiService taobaoApiService;
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private TradeSyncMapper tradeSyncMapper;
    @Autowired
    private TradeAsyncMapper tradeAsyncMapper;
    @Autowired
    private AppService appService;

    @PostConstruct
    public void init() {
        reboot();
        startPoll();
    }

    @Override
    public void reboot() {
        super.reboot();
        tradeSyncMapper.updateSyncStatusAndLock(TradeConstants.SYNC_STATUS_FAILED, UNLOCK, appService.getServiceName(),
                appService.getServiceId());
    }

    @Override
    public void timeout() {
        tradeSyncMapper.updateSyncTimeoutSync(60 * 60 * 5);
    }

    @Override
    public void fillQueue() {
        int offset = 0;
        while (true) {
            Collection<TradeSync> tradeSyncList = tradeSyncMapper.paginationSync(offset, 100);
            if (CollectionUtils.isEmpty(tradeSyncList)) {
                break;
            } else {
                for (TradeSync tradeSync : tradeSyncList) {
                    if (isAsync(tradeSync) && lock(tradeSync)) {
                        fillQueue(tradeSync);
                    }
                }
            }
        }
    }

    public boolean isAsync(TradeSync tradeSync) {
        TradeAsync tradeAsync = tradeAsyncMapper.selectByUserId(tradeSync.getUserId());
        return null != tradeAsync;
    }

    @Override
    public void process(TradeSync tradeSync) throws Exception {
        Stopwatch stopwatch = new Stopwatch().start();
        logger.info("start to incr sync tradeSync[{}].", tradeSync.getId());
        //设置订单同步开始和结束时间
        DateTime tradeStartDateTime = null;
        if (tradeSync.getSyncStatus() == TradeConstants.ASYNC_STATUS_INIT) {
            TradeAsync tradeAsync = tradeAsyncMapper.selectByUserId(tradeSync.getUserId());
            if (tradeAsync.getSyncStatus() == TradeConstants.ASYNC_STATUS_SUCCESSFUL) {
                tradeStartDateTime = new DateTime(tradeAsync.getTradeEndTime());
            } else {
                logger.error("TradeSync[{}],TradeAsync[{}]. init async is not successful, skip incr sync.",
                        tradeSync.getId(), tradeAsync.getId());
                return;
            }
        }
        if (tradeSync.getSyncStatus() == TradeConstants.ASYNC_STATUS_SUCCESSFUL) {
            tradeStartDateTime = new DateTime(tradeSync.getCheckTradeEndTime());
        }
        if (tradeSync.getSyncStatus() == TradeConstants.SYNC_CHECK_STATUS_FAILED) {
            tradeStartDateTime = new DateTime(tradeSync.getSyncTradeStartTime());
        }
        if (null == tradeStartDateTime) {
            try {
                throw new Exception("process error, tradeStartDateTime is null.");
            } catch (Exception e) {
                fail(tradeSync);
                throw e;
            }
        }
        DateTime tradeEndDateTime = DateTime.now().minusMinutes(5);

        if (Minutes.minutesBetween(tradeStartDateTime, tradeEndDateTime).getMinutes() < 60) {
            logger.info("tradeSync[], startTime[{}], endTime[{}], interval < 60 min, skip.", tradeSync.getId(),
                    tradeStartDateTime.toString(DateUtils.yyyy_MM_dd_HH_mm_ss), tradeEndDateTime.toString(DateUtils.yyyy_MM_dd_HH_mm_ss));
            return;
        }

        //同步退款
        try {
            syncRefund(tradeSync.getUserId(), tradeSync.getSyncAppKey(), tradeStartDateTime.toDate(), tradeEndDateTime.toDate());
        } catch (TaobaoEnhancedApiException e) {
            fail(tradeSync);
            throw e;
        }

        //同步订单
        List<Interval> syncIntervalPartitionList = DateUtils.partitionByMinute(tradeStartDateTime, tradeEndDateTime, 60);
        for (Interval syncInterval : syncIntervalPartitionList) {
            try {
                syncByModified(tradeSync.getUserId(), tradeSync.getSyncAppKey(), syncInterval.getStart().toDate(),
                        syncInterval.getEnd().toDate());
            } catch (Exception e) {
                fail(tradeSync);
                throw e;
            }
        }
        success(tradeSync);
        stopwatch.stop();
        logger.info("end incr sync tradeSync[{}], cost {} sec", tradeSync.getId(), stopwatch.elapsed(TimeUnit.SECONDS));
    }

    private void fail(TradeSync tradeSync) {
        TradeSync newTradeSync = new TradeSync();
        newTradeSync.setId(tradeSync.getId());
        newTradeSync.setSyncLock(UNLOCK);
        newTradeSync.setSyncStatus(TradeConstants.SYNC_STATUS_FAILED);
        newTradeSync.setSyncEndTime(appService.getLocalSystemTime().toDate());
        tradeSyncMapper.updateByPrimaryKey(newTradeSync);
    }

    private void success(TradeSync tradeSync) {
        TradeSync newTradeSync = new TradeSync();
        newTradeSync.setId(tradeSync.getId());
        newTradeSync.setSyncLock(UNLOCK);
        newTradeSync.setSyncStatus(TradeConstants.SYNC_STATUS_SUCCESSFUL);
        newTradeSync.setSyncEndTime(appService.getLocalSystemTime().toDate());
        tradeSyncMapper.updateByPrimaryKey(newTradeSync);
    }

    private boolean lock(TradeSync tradeSync) {
        int rows = tradeSyncMapper.updateSyncLock(tradeSync.getId(), LOCK, appService.getServiceName(),
                appService.getServiceId());
        return rows == 1;
    }

    public void syncRefund(Long userId, String appKey, Date startDate,
                           Date endDate) throws TaobaoEnhancedApiException {
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId,taobaoApiService.getAppKey());
        long pageNo = 1;
        while (true) {
            RefundsReceiveGetRequest request = new RefundsReceiveGetRequest();
            request.setFields("refund_id");
            request.setType(Joiner.on(",").join(BaseConstants.TRADE_TYPES));
            request.setPageSize(100L);
            request.setStartModified(startDate);
            request.setEndModified(endDate);
            request.setPageNo(pageNo);

            RefundsReceiveGetResponse response = taobaoApiService.request(request, appKey, taobaoSession.getSessionKey());

            if (response.isSuccess()) {
                List<Refund> refunds = response.getRefunds();
                if (CollectionUtils.isEmpty(refunds)) {
                    break;
                } else {
                    pageNo++;
                    processRefunds(userId, refunds);
                }
            } else {
                throw new TaobaoEnhancedApiException(request, response);
            }
        }
    }

    public void syncByCreated(Long userId, String appKey, Date startDate,
                              Date endDate) throws TaobaoEnhancedApiException {
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId,appKey);
        long pageNo = 1;
        while (true) {
            TradesSoldGetRequest request = new TradesSoldGetRequest();
            request.setFields(Joiner.on(",").join(BaseConstants.TRADE_FIELDS));
            request.setType(Joiner.on(",").join(BaseConstants.TRADE_TYPES));
            request.setStartCreated(startDate);
            request.setEndCreated(endDate);
            request.setPageNo(pageNo);
            request.setPageSize(100L);
            request.setUseHasNext(true);

            TradesSoldGetResponse response = taobaoApiService.request(request, appKey, taobaoSession.getSessionKey());

            if (response.isSuccess()) {
                List<Trade> trades = response.getTrades();
                if (CollectionUtils.isEmpty(trades)) {
                    break;
                } else {
                    pageNo++;
                    processTrades(userId, trades);
                }
            } else {
                throw new TaobaoEnhancedApiException(request, response);
            }
        }
    }

    public void syncByModified(Long userId, String appKey, Date startDate,
                               Date endDate) throws TaobaoEnhancedApiException {
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId,appKey);
        long pageNo = 1;
        while (true) {
            TradesSoldIncrementGetRequest request = new TradesSoldIncrementGetRequest();
            request.setFields(Joiner.on(",").join(BaseConstants.TRADE_FIELDS));
            request.setType(Joiner.on(",").join(BaseConstants.TRADE_TYPES));
            request.setStartModified(startDate);
            request.setEndModified(endDate);
            request.setPageNo(pageNo);
            request.setPageSize(100L);
            request.setUseHasNext(true);


            TradesSoldIncrementGetResponse response = taobaoApiService.request(request, appKey, taobaoSession.getSessionKey());

            if (response.isSuccess()) {
                List<Trade> trades = response.getTrades();
                if (CollectionUtils.isEmpty(trades)) {
                    break;
                } else {
                    pageNo++;
                    processTrades(userId, trades);
                }
            } else {
                throw new TaobaoTaobaoEnhancedApiException(request, response);
            }
        }
    }

    private void processTrades(Long userId, List<Trade> trades) {
    }

    public void processRefunds(Long userId, List<Refund> refunds) {

    }
}
