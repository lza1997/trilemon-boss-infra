package com.trilemon.boss360.infrastructure.trade.service;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.taobao.api.request.RefundsReceiveGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.request.TradesSoldIncrementGetRequest;
import com.taobao.api.response.RefundsReceiveGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.taobao.api.response.TradesSoldIncrementGetResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kevin
 */
@Service
public class TradeIncrementalSyncService {
    private static Logger logger = LoggerFactory.getLogger(TradeIncrementalSyncService.class);

    public void syncRefund(final int appId, final long shopId, DateTime startTime, DateTime endTime) throws Exception {
        logger.info("start to sync refund , appId[{}] , shopId[{}] , startTime[{}] , endTime[{}]", appId, shopId,
                startTime, endTime);
        long pageNo = 1;
        while (true) {
            RefundsReceiveGetRequest req = new RefundsReceiveGetRequest();
            req.setFields("refund_id");
            req.setType(Joiner.on(",").join());
            req.setPageSize(100L);
            req.setStartModified(startTime.toDate());
            req.setEndModified(endTime.toDate());
            req.setPageNo(pageNo);
            final App app = appService.getApp(appId);
            final TopSession topSession = topSessionService.getTopSession(shopId, appId);

            TopApiRequestTemplate<RefundsReceiveGetRequest,
                    RefundsReceiveGetResponse, Integer> template = new
                    TopApiRequestTemplate<RefundsReceiveGetRequest, RefundsReceiveGetResponse, Integer>() {
                        @Override
                        public Integer success(RefundsReceiveGetRequest request, RefundsReceiveGetResponse response) throws Exception {
                            List<com.taobao.api.domain.Refund> refunds = response.getRefunds();
                            List<com.taobao.api.domain.Refund> fullInfoRefunds = Lists.newArrayList();
                            if (CollectionUtils.isEmpty(fullInfoRefunds)) {
                                return 0;
                            } else {
                                for (com.taobao.api.domain.Refund refund : refunds) {
                                    try {
                                        com.taobao.api.domain.Refund fullInfoRefund = tradeService.getTopRefund
                                                (shopId, Long.valueOf
                                                        (refund.getRefundId()));
                                        fullInfoRefunds.add(fullInfoRefund);
                                    } catch (Exception e) {
                                        throw e;
                                    }
                                }
                                tradeService.saveTopRefunds(fullInfoRefunds);
                                return refunds.size();
                            }
                        }

                        @Override
                        public Integer fail(RefundsReceiveGetRequest request, RefundsReceiveGetResponse response) throws Exception {
                            String errorCode = response.getErrorCode();
                            if (StringUtils.isNotEmpty(errorCode) && errorCode.equals("27")) {
                                try {
                                    Shop shop = shopService.getShop(shopId);
                                    logger.info("shop[] is expired. response[]", shop, ToStringBuilder.reflectionToString(response));
                                    String sk = shopService.getSessionKey(appId, shop.getNick(), shop.getUserId(),
                                            topSession.getSessionKey());
                                    if (shopService.isTopApiAccessible(app.getAppKey(), sk)) {
                                        topSessionService.refreshSessionKey(shopId, appId, sk);
                                    } else {
                                        shopService.changeApp(shopId);
                                    }
                                } catch (Exception e) {
                                    logger.error("refresh token error , change app.current app[" + app + "]", e);
                                    shopService.changeApp(shopId);
                                }
                            }
                            throw new Exception("request: " + ToStringBuilder.reflectionToString(request) + "\n" +
                                    "response: " + ToStringBuilder
                                    .reflectionToString(response));
                        }
                    };
            int num = topApiService.request(template, app.getAppKey(), req, topSession.getSessionKey());
            if (num == 0) {
                break;
            } else {
                pageNo++;
            }
        }
        logger.info("end to sync refund , appId[{}] , shopId[{}] , startTime[{}] , endTime[{}]", appId, shopId,
                startTime, endTime);
    }

    public void syncByCreated(final int appId, final long shopId, final DateTime startTime,
                                         final DateTime endTime) throws Exception {
        long pageNo = 1;
        while (true) {
            TradesSoldGetRequest req = new TradesSoldGetRequest();
            req.setFields(Joiner.on(",").join(SHORT_TRADE_FIELDS));
            req.setType(Joiner.on(",").join(TRADE_TYPES));
            req.setStartCreated(startTime.toDate());
            req.setEndCreated(endTime.toDate());
            req.setPageNo(pageNo);
            req.setPageSize(100L);
            req.setUseHasNext(true);

            final App app = appService.getApp(appId);
            final TopSession topSession = topSessionService.getTopSession(shopId, appId);

            TopApiRequestTemplate<TradesSoldGetRequest,
                    TradesSoldGetResponse, Integer> topApiRequestTemplate = new
                    TopApiRequestTemplate<TradesSoldGetRequest, TradesSoldGetResponse, Integer>() {
                        @Override
                        public Integer success(TradesSoldGetRequest request, TradesSoldGetResponse response) throws Exception {
                            List<com.taobao.api.domain.Trade> trades = response.getTrades();
                            List<com.taobao.api.domain.Trade> fullInfoTrades = Lists.newArrayList();
                            if (CollectionUtils.isEmpty(trades)) {
                                return 0;
                            } else {
                                for (com.taobao.api.domain.Trade trade : trades) {
                                    try {
                                        com.taobao.api.domain.Trade fullInfoTrade = tradeService.getTopTrade(shopId, Long.valueOf
                                                (trade.getTid()));
                                        fullInfoTrades.add(fullInfoTrade);
                                    } catch (Exception e) {
                                        throw e;
                                    }

                                }
                                tradeService.saveTopTrades(fullInfoTrades);
                                return trades.size();
                            }
                        }

                        @Override
                        public Integer fail(TradesSoldGetRequest request, TradesSoldGetResponse response) throws Exception {
                            String errorCode = response.getErrorCode();
                            if (StringUtils.isNotEmpty(errorCode) && errorCode.equals("27")) {
                                try {
                                    Shop shop = shopService.getShop(shopId);
                                    logger.info("shop[] is expired. response[]", shop, ToStringBuilder.reflectionToString(response));
                                    String sk = shopService.getSessionKey(appId, shop.getNick(), shop.getUserId(),
                                            topSession.getSessionKey());
                                    if (shopService.isTopApiAccessible(app.getAppKey(), sk)) {
                                        topSessionService.refreshSessionKey(shopId, appId, sk);
                                    } else {
                                        shopService.changeApp(shopId);
                                    }
                                } catch (Exception e) {
                                    logger.error("refresh token error , change app.current app[" + app + "]", e);
                                    shopService.changeApp(shopId);
                                }
                            }
                            throw new Exception("request: " + ToStringBuilder.reflectionToString(request) + "\n" +
                                    "response: " + ToStringBuilder
                                    .reflectionToString(response));
                        }
                    };
            int syncNum;
            syncNum = topApiService.request(topApiRequestTemplate, app.getAppKey(), req, topSession.getSessionKey());
            if (syncNum == 0) {
                break;
            } else {
                pageNo++;
            }
        }
    }

    public void syncByModified(final int appId, final long shopId, final DateTime startTime,
                                          final DateTime endTime) throws Exception {
        long pageNo = 1;
        while (true) {
            TradesSoldIncrementGetRequest req = new TradesSoldIncrementGetRequest();
            req.setFields(Joiner.on(",").join(SHORT_TRADE_FIELDS));
            req.setType(Joiner.on(",").join(TRADE_TYPES));
            req.setStartModified(startTime.toDate());
            req.setEndModified(endTime.toDate());
            req.setPageNo(pageNo);
            req.setPageSize(100L);
            req.setUseHasNext(true);

            final App app = appService.getApp(appId);
            final TopSession topSession = topSessionService.getTopSession(shopId, appId);

            TopApiRequestTemplate<TradesSoldIncrementGetRequest,
                    TradesSoldIncrementGetResponse, Integer> topApiRequestTemplate = new
                    TopApiRequestTemplate<TradesSoldIncrementGetRequest, TradesSoldIncrementGetResponse, Integer>() {
                        @Override
                        public Integer success(TradesSoldIncrementGetRequest tradesSoldIncrementGetRequest, TradesSoldIncrementGetResponse response) throws Exception {
                            List<com.taobao.api.domain.Trade> trades = response.getTrades();
                            List<com.taobao.api.domain.Trade> fullInfoTrades = Lists.newArrayList();
                            if (CollectionUtils.isEmpty(trades)) {
                                return 0;
                            } else {
                                for (com.taobao.api.domain.Trade trade : trades) {
                                    try {
                                        com.taobao.api.domain.Trade fullInfoTrade = tradeService.getTopTrade(shopId, Long.valueOf
                                                (trade.getTid()));
                                        fullInfoTrades.add(fullInfoTrade);
                                    } catch (Exception e) {
                                        throw e;
                                    }
                                }
                                tradeService.saveTopTrades(fullInfoTrades);
                                return trades.size();
                            }
                        }

                        @Override
                        public Integer fail(TradesSoldIncrementGetRequest request,
                                            TradesSoldIncrementGetResponse response) throws Exception {
                            String errorCode = response.getErrorCode();
                            if (StringUtils.isNotEmpty(errorCode) && errorCode.equals("27")) {
                                try {
                                    Shop shop = shopService.getShop(shopId);
                                    logger.info("shop[{}] is expired. response[{}]", shop,
                                            ToStringBuilder.reflectionToString(response));
                                    String sk = shopService.getSessionKey(appId, shop.getNick(), shop.getUserId(),
                                            topSession.getSessionKey());
                                    if (shopService.isTopApiAccessible(app.getAppKey(), sk)) {
                                        topSessionService.refreshSessionKey(shopId, appId, sk);
                                    } else {
                                        logger.info("refresh token invalid , change app. current app[{}]", app);
                                        shopService.changeApp(shopId);
                                    }
                                } catch (Exception e) {
                                    logger.error("refresh token error , change app.current app[" + app + "]", e);
                                    shopService.changeApp(shopId);
                                }
                            }
                            throw new Exception("request: " + ToStringBuilder.reflectionToString(request) + "\n" +
                                    "response: " + ToStringBuilder
                                    .reflectionToString(response));
                        }
                    };
            int syncNum;
            syncNum = topApiService.request(topApiRequestTemplate, app.getAppKey(), req, topSession.getSessionKey());
            if (syncNum == 0) {
                break;
            } else {
                pageNo++;
            }
        }
    }
}
