package com.trilemon.boss360.infrastructure.trade.service;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.taobao.api.domain.Task;
import com.taobao.api.request.TopatsResultGetRequest;
import com.taobao.api.request.TopatsTradesSoldGetRequest;
import com.taobao.api.response.TopatsResultGetResponse;
import com.taobao.api.response.TopatsTradesSoldGetResponse;
import com.trilemon.boss360.base.client.BaseClient;
import com.trilemon.boss360.base.model.TaobaoSession;
import com.trilemon.boss360.base.serivce.ApplicationService;
import com.trilemon.boss360.base.serivce.EnhancedApiException;
import com.trilemon.boss360.base.serivce.TaobaoApiService;
import com.trilemon.boss360.infrastructure.trade.dao.TradeAsyncMapper;
import com.trilemon.boss360.infrastructure.trade.model.TradeAsync;
import com.trilemon.commons.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

import static com.trilemon.boss360.infrastructure.trade.Constants.TRADE_FIELDS;

/**
 * @author kevin
 */
@Service
public class TradeInitSyncService {
    private static Logger logger = LoggerFactory.getLogger(TradeInitSyncService.class);
    @Autowired
    private ShopService shopService;
    @Autowired
    private TaobaoApiService taobaoApiService;
    @Autowired
    private TradeAsyncMapper tradeAsyncMapper;
    @Autowired
    private ApplicationService applicationService;
    @Value("${trade_download_dir}")
    private String tradeDownloadDir;
    @Autowired
    private TradeCalcService tradeCalcService;
    private PriorityQueue<TradeAsync> tradeAsyncQueue = Queues.newPriorityQueue();
    private BlockingThreadPoolExecutor tradeAsyncPool = new BlockingThreadPoolExecutor(5);

    public void sync(long userId, String appKey) {
        TradeAsync tradeAsync = tradeAsyncMapper.selectByUserId(userId);
        if (null != tradeAsync) {
            if (tradeAsync.getSyncStatus() == TradeAsync.SYNC_STATUS_SUCCESSFUL) {
                logger.info("userId[{}] , appKey[{}] has been sync, skip.", userId, appKey);
                return;
            }
        }
        prepare(userId, appKey);
    }

    public void dispatch() {
        while (true) {
            final TradeAsync tradeAsync = tradeAsyncQueue.poll();
            if (null == tradeAsync) {
                fillTradeAsyncQueue();
                //如果填充后还是空，则说明此时没有需要同步的卖家，则休眠一分钟，节省系统资源。
                if (tradeAsyncQueue.isEmpty()) {
                    Threads.sleep(1, TimeUnit.MINUTES);
                }
            } else {
                tradeAsyncPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        logger.info("start to sync tradeAsync[{}].", tradeAsync.getId());

                        try {
                            long tradeNumPerDay = shopService.getTradeNumFromTop(appKey,sessionKey, startTime,
                                    endTime);
                            if (tradeNumPerDay <= 1000) {
                                logger.info("trade num [{}] <= 1000 , use sync, tradeAsync[{}]", tradeNumPerDay,
                                        tradeAsync.getId());
                                sync(tradeAsync);
                            } else {
                                logger.info("trade num [{}] > 1000 , use async, tradeAsync[{}]", tradeNumPerDay,
                                        tradeAsync.getId());
                                async(tradeAsync);
                            }
                        } catch (Exception e) {
                            logger.error("error get tradeNumPerDay, use async, tradeAsync[" + tradeAsync.getId() + "]",
                                    e);
                            async(tradeAsync);
                        }
                        async(tradeAsync);
                    }
                });
            }
        }
    }

    private void sync(TradeAsync tradeAsync) {

    }

    private void async(TradeAsync tradeAsync) {
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

    private void success(TradeAsync tradeAsync) {
        tradeAsync.setSyncEndTime(applicationService.getLocalSystemTime().toDate());
        tradeAsync.setSyncStatus(TradeAsync.SYNC_STATUS_FAILED);
        tradeAsyncMapper.updateByPrimaryKeySelective(tradeAsync);
    }

    private void fail(TradeAsync tradeAsync) {
        tradeAsync.setSyncEndTime(applicationService.getLocalSystemTime().toDate());
        tradeAsync.setSyncStatus(TradeAsync.SYNC_STATUS_SUCCESSFUL);
        tradeAsyncMapper.updateByPrimaryKeySelective(tradeAsync);
    }

    public void prepare(long userId, String appKey) {
        TradeAsync tradeAsync = new TradeAsync();
        tradeAsync.setSyncAppKey(appKey);
        tradeAsync.setUserId(userId);
        tradeAsync.setSyncStatus(TradeAsync.SYNC_STATUS_INIT);
        tradeAsync.setSyncStartTime(applicationService.getLocalSystemTime().toDate());
        tradeAsync.setServerName(applicationService.getServiceName());
        tradeAsync.setServerName(applicationService.getServiceId());
        tradeAsyncMapper.insertSelective(tradeAsync);
    }

    public void fillTradeAsyncQueue() {
        int offset = 0;
        while (true) {
            Collection<TradeAsync> tradeAsyncList = tradeAsyncMapper.pagination(offset,
                    100, TradeAsync.SYNC_STATUS_INIT, applicationService.getServiceName(),
                    applicationService.getServiceId());
            if (CollectionUtils.isEmpty(tradeAsyncList)) {
                break;
            } else {
                tradeAsyncQueue.addAll(tradeAsyncList);
            }
        }
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
        request.setFields(Joiner.on(",").join(TRADE_FIELDS));

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
