package com.trilemon.boss360.infrastructure.trade.service;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.taobao.api.domain.Task;
import com.taobao.api.request.TopatsResultGetRequest;
import com.taobao.api.request.TopatsTradesSoldGetRequest;
import com.taobao.api.response.TopatsResultGetResponse;
import com.taobao.api.response.TopatsTradesSoldGetResponse;
import com.trilemon.boss360.infrastructure.base.client.BaseClient;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;
import com.trilemon.boss360.infrastructure.base.serivce.ApplicationService;
import com.trilemon.boss360.infrastructure.base.serivce.EnhancedApiException;
import com.trilemon.boss360.infrastructure.base.serivce.TaobaoApiService;
import com.trilemon.boss360.infrastructure.trade.Constants;
import com.trilemon.boss360.infrastructure.trade.dao.TradeAsyncMapper;
import com.trilemon.boss360.infrastructure.trade.model.TradeAsync;
import com.trilemon.commons.DateUtils;
import com.trilemon.commons.Files2;
import com.trilemon.commons.Threads;
import com.trilemon.commons.WebUtils;
import com.trilemon.commons.service.AbstractQueueService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author kevin
 */
@Service
public class TradeInitSyncService extends AbstractQueueService<TradeAsync> {
    private static Logger logger = LoggerFactory.getLogger(TradeInitSyncService.class);
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private TaobaoApiService taobaoApiService;
    @Autowired
    private TradeIncrSyncService tradeIncrSyncService;
    @Autowired
    private TradeAsyncMapper tradeAsyncMapper;
    @Autowired
    private ApplicationService applicationService;
    @Value("${trade_download_dir}")
    private String tradeDownloadDir;
    @Autowired
    private TradeCalcService tradeCalcService;

    @PostConstruct
    public void init() {
        reboot();
        startPoll();
    }

    @Override
    public void reboot() {
        super.reboot();
        tradeAsyncMapper.updateSyncStatusByService(Constants.ASYNC_STATUS_FAILED, applicationService.getServiceName(),
                applicationService.getServiceId());
    }

    @Override
    public void timeout() {
        tradeAsyncMapper.updateTimeoutSyncStatus(Constants.ASYNC_STATUS_FAILED,60 * 60 * 5);
    }

    @Override
    public void fillQueue() {
        int offset = 0;
        while (true) {
            Collection<TradeAsync> tradeAsyncList = tradeAsyncMapper.pagination(offset,
                    100, Constants.ASYNC_STATUS_INIT, applicationService.getServiceName(),
                    applicationService.getServiceId());
            if (CollectionUtils.isEmpty(tradeAsyncList)) {
                break;
            } else {
                fillQueue(tradeAsyncList);
            }
        }
    }

    @Override
    public void process(TradeAsync tradeAsync) {
        logger.info("start to sync tradeAsync[{}].", tradeAsync.getId());

        DateTime endDateTime = DateUtils.endOfNDaysBefore(1);
        DateTime startDateTime = DateUtils.startOfNDaysBefore(90);
        TradeAsync newTradeAsync = new TradeAsync();
        newTradeAsync.setId(tradeAsync.getId());
        newTradeAsync.setServiceName(applicationService.getServiceName());
        newTradeAsync.setServiceId(applicationService.getServiceId());
        newTradeAsync.setSyncStartTime(applicationService.getLocalSystemTime().toDate());
        newTradeAsync.setTradeStartTime(startDateTime.toDate());
        newTradeAsync.setTradeEndTime(endDateTime.toDate());
        tradeAsyncMapper.updateByPrimaryKeySelective(newTradeAsync);

        try {
            long tradeNum = baseClient.getTradeNumFromTop(tradeAsync.getUserId(),
                    tradeAsync.getSyncAppKey(), tradeAsync.getTradeStartTime(),
                    tradeAsync.getTradeEndTime());
            long tradeNumPerDay = tradeNum / 90;
            if ((tradeNum / 90) <= 1000) {
                logger.info("trade num [{}/{}] <= 1000 , use sync, tradeAsync[{}]", tradeNum, tradeNumPerDay,
                        tradeAsync.getId());
                tradeIncrSyncService.syncByCreated(tradeAsync.getUserId(), tradeAsync.getSyncAppKey(),
                        tradeAsync.getTradeStartTime(), tradeAsync.getTradeEndTime());
            } else {
                logger.info("trade num [{}/{}] > 1000 , use async, tradeAsync[{}]", tradeNum, tradeNumPerDay,
                        tradeAsync.getId());
                async(tradeAsync);
            }
        } catch (Exception e) {
            logger.error("error get tradeNum, use async, tradeAsync[" + tradeAsync.getId() + "]", e);
            async(tradeAsync);
        }
    }

    /**
     * 进行异步同步
     *
     * @param userId
     * @param appKey
     */
    public void async(long userId, String appKey) {
        TradeAsync tradeAsync = tradeAsyncMapper.selectByUserId(userId);
        if (null != tradeAsync) {
            if (tradeAsync.getSyncStatus() == Constants.SYNC_STATUS_SUCCESSFUL) {
                logger.info("userId[{}] , appKey[{}] has been sync, skip.", userId, appKey);
                return;
            } else {
                prepare(userId, false);
            }
        } else {
            prepare(userId, true);
        }
    }

    public void prepare(long userId, boolean insertOrUpdate) {
        TradeAsync tradeAsync = new TradeAsync();
        tradeAsync.setUserId(userId);
        tradeAsync.setSyncStatus(Constants.ASYNC_STATUS_INIT);
        tradeAsync.setSyncStartTime(applicationService.getLocalSystemTime().toDate());
        tradeAsync.setServiceName(applicationService.getServiceName());
        tradeAsync.setServiceId(applicationService.getServiceId());
        if (insertOrUpdate) {
            tradeAsyncMapper.insertSelective(tradeAsync);
        } else {
            tradeAsyncMapper.updateByUserId(tradeAsync);
        }
    }

    public void async(TradeAsync tradeAsync) {
        Stopwatch stopwatch = new Stopwatch().start();
        logger.info("start to async tradeAsync[{}].", tradeAsync.getId());
        //1. submit to taobao
        logger.info("start to submit tradeAsync[{}] to taobao.", tradeAsync.getId());
        Long taskId;

        try {
            taskId = submit2Taobao(tradeAsync);
        } catch (EnhancedApiException e) {
            logger.error("error submit tradeAsync[" + tradeAsync.getId() + "] to taobao", e);
            fail(tradeAsync);
            return;
        }
        logger.info("end submit tradeAsync[{}] to taobao, cost {} sec", tradeAsync.getId(),
                stopwatch.elapsed(TimeUnit.SECONDS));

        //2. poll taobao task status
        logger.info("start poll tradeAsync[{}].", tradeAsync.getId());
        if (null == taskId) {
            logger.error("error poll tradeAsync[{}], taskId is null.", tradeAsync.getId());
            fail(tradeAsync);
            return;
        }

        Task task;
        while (true) {
            try {
                task = pollTaobaoTask(tradeAsync.getSyncAppKey(), taskId);
                if (task.getStatus().equalsIgnoreCase("done")) {
                    break;
                } else {
                    Threads.sleep(1, TimeUnit.MINUTES);
                }
            } catch (EnhancedApiException e) {
                if (e.getSubCode().contains("isv.task-result-empty")) {
                    success(tradeAsync);
                    logger.info("poll tradeAsync[{}], taskId is null.task-result-empty.", tradeAsync.getId());
                    return;
                } else {
                    fail(tradeAsync);
                    logger.error("error poll tradeAsync[" + tradeAsync.getId() + "]", e);
                    return;
                }
            }
        }
        logger.info("end poll tradeAsync[{}], cost {} sec", tradeAsync.getId(),
                stopwatch.elapsed(TimeUnit.SECONDS));

        //3. download
        logger.info("start to download tradeAsync[{}], taskId[{}].", tradeAsync.getId(), taskId);
        if (null == task) {
            logger.error("error download tradeAsync[{}], taskId[{}].", tradeAsync.getId(), taskId);
            fail(tradeAsync);
            return;
        }
        String filename;
        try {
            filename = downloadTaobaoResult(task);
            tradeAsync.setDownloadFile(filename);
            tradeAsyncMapper.updateByPrimaryKeySelective(tradeAsync);
        } catch (Exception e) {
            logger.error("error download tradeAsync[" + tradeAsync.getId() + "],taskId[" + taskId + "].", e);
            fail(tradeAsync);
            return;
        }
        logger.error("end download tradeAsync[{}], taskId[{}], cost {} sec.", tradeAsync.getId(),
                taskId, stopwatch.elapsed(TimeUnit.SECONDS));

        //4. process
        logger.info("start to process tradeAsync[{}],filename[{}].", tradeAsync.getId(), filename);
        try {
            process(tradeAsync, filename);
        } catch (Exception e) {
            logger.error("error process tradeAsync[" + tradeAsync.getId() + "],filename[" + filename + "].", e);
            fail(tradeAsync);
            return;
        }
        logger.info("start to process tradeAsync[{}],filename[{}].", tradeAsync.getId(), filename);
        stopwatch.stop();
        logger.info("end async tradeAsync[{}], cost {} min.", tradeAsync.getId(),
                stopwatch.elapsed(TimeUnit.MINUTES));
    }

    public void success(TradeAsync tradeAsync) {
        tradeAsync.setSyncEndTime(applicationService.getLocalSystemTime().toDate());
        tradeAsync.setSyncStatus(Constants.ASYNC_STATUS_SUCCESSFUL);
        tradeAsyncMapper.updateByPrimaryKeySelective(tradeAsync);
    }

    public void fail(TradeAsync tradeAsync) {
        tradeAsync.setSyncEndTime(applicationService.getLocalSystemTime().toDate());
        tradeAsync.setSyncStatus(Constants.ASYNC_STATUS_FAILED);
        tradeAsyncMapper.updateByPrimaryKeySelective(tradeAsync);
    }

    /**
     * 提交异步任务到淘宝
     *
     * @param tradeAsync tradeAsync
     * @throws EnhancedApiException 如果提交不成功则抛出异常
     */
    public long submit2Taobao(TradeAsync tradeAsync) throws EnhancedApiException {
        TopatsTradesSoldGetRequest request = new TopatsTradesSoldGetRequest();
        String startTimeStr = DateUtils.format(tradeAsync.getTradeStartTime(), DateUtils.yyyyMMdd2);
        String endTimeStr = DateUtils.format(tradeAsync.getTradeEndTime(), DateUtils.yyyyMMdd2);
        request.setStartTime(startTimeStr);
        request.setEndTime(endTimeStr);
        request.setFields(Joiner.on(",").join(Constants.TRADE_FIELDS));

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(tradeAsync.getUserId());
        TopatsTradesSoldGetResponse response = taobaoApiService.request(request, tradeAsync.getSyncAppKey(),
                taobaoSession.getSessionKey());

        if (response.isSuccess()) {
            return response.getTask().getTaskId();
        } else {
            String subCode = response.getSubCode();
            //检查是否重复提交任务
            if (null != subCode) {
                if (subCode.contains("isv.task-duplicate")) {
                    String[] splitArray = response.getSubMsg().split("=");
                    if (splitArray.length > 1 && splitArray[1].matches("^-?[1-9]\\d*$")) {
                        return Long.valueOf(splitArray[1]);
                    }
                }
            }
            throw new EnhancedApiException(request, response);
        }
    }

    public Task pollTaobaoTask(String appKey, long taskId) throws EnhancedApiException {
        TopatsResultGetRequest request = new TopatsResultGetRequest();
        request.setTaskId(taskId);

        TopatsResultGetResponse response = taobaoApiService.request(request, appKey);

        if (response.isSuccess()) {
            return response.getTask();
        } else {
            throw new EnhancedApiException(request, response);
        }
    }

    public String downloadTaobaoResult(Task task) throws Exception {
        String downloadUrl = task.getDownloadUrl();
        String filename = Joiner.on(File.separator).join(tradeDownloadDir, task.getTaskId() + ".zip");
        WebUtils.download(downloadUrl, filename, true);
        return filename;
    }

    public void process(TradeAsync tradeAsync, String downloadFile) throws Exception {
        Stopwatch stopwatch = new Stopwatch().start();

        logger.info("start to unzip file {}", downloadFile);
        File[] unzipFiles = Files2.unzip2TempDir(downloadFile);
        logger.info("unzip file {} successfully, cost {} sec", downloadFile, stopwatch.elapsed(TimeUnit.SECONDS));
        if (ArrayUtils.isEmpty(unzipFiles)) {
            return;
        }
        try {
            process(tradeAsync, unzipFiles);
        } finally {
            Files2.deleteQuietly(Lists.newArrayList(unzipFiles));
        }
    }

    private void process(TradeAsync tradeAsync, File[] file) {
        tradeCalcService.calcAdjustIntervals(tradeAsync, file);
    }
}
