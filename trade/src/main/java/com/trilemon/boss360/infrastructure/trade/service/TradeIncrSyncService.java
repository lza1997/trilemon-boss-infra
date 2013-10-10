package com.trilemon.boss360.infrastructure.trade.service;

import com.google.common.base.Joiner;
import com.taobao.api.domain.Refund;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.RefundsReceiveGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.request.TradesSoldIncrementGetRequest;
import com.taobao.api.response.RefundsReceiveGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.taobao.api.response.TradesSoldIncrementGetResponse;
import com.trilemon.boss360.infrastructure.base.serivce.TaobaoApiService;
import com.trilemon.boss360.infrastructure.base.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kevin
 */
@Service
public class TradeIncrSyncService {
    private static Logger logger = LoggerFactory.getLogger(TradeIncrSyncService.class);
    @Autowired
    private TaobaoApiService taobaoApiService;

    public void syncRefund(String appKey, String SessionKey, DateTime startTime, DateTime endTime) throws Exception {
        long pageNo = 1;
        while (true) {
            RefundsReceiveGetRequest request = new RefundsReceiveGetRequest();
            request.setFields("refund_id");
            request.setType(Joiner.on(",").join(Constants.TRADE_TYPES));
            request.setPageSize(100L);
            request.setStartModified(startTime.toDate());
            request.setEndModified(endTime.toDate());
            request.setPageNo(pageNo);

            RefundsReceiveGetResponse response = taobaoApiService.request(request, appKey, SessionKey);

            if (response.isSuccess()) {
                List<Refund> refunds = response.getRefunds();
                if (CollectionUtils.isEmpty(refunds)) {
                    break;
                } else {
                    pageNo++;
                    processRefunds(refunds);
                }
            } else {
                syncRefundFail();
                break;
            }
        }
    }

    public void processRefunds(List<Refund> refunds) {

    }

    public void syncByCreated(String appKey, String SessionKey, final DateTime startTime,
                              final DateTime endTime) throws Exception {
        long pageNo = 1;
        while (true) {
            TradesSoldGetRequest request = new TradesSoldGetRequest();
            request.setFields(Joiner.on(",").join(Constants.TRADE_FIELDS));
            request.setType(Joiner.on(",").join(Constants.TRADE_TYPES));
            request.setStartCreated(startTime.toDate());
            request.setEndCreated(endTime.toDate());
            request.setPageNo(pageNo);
            request.setPageSize(100L);
            request.setUseHasNext(true);

            TradesSoldGetResponse response = taobaoApiService.request(request, appKey, SessionKey);

            if (response.isSuccess()) {
                List<Trade> trades = response.getTrades();
                if (CollectionUtils.isEmpty(trades)) {
                    break;
                } else {
                    pageNo++;
                    processTrades(trades);
                }
            } else {
                syncTradeFail();
                break;
            }
        }
    }

    private void processTrades(List<Trade> trades) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void syncByModified(String appKey, String sessionKey, final DateTime startTime,
                               final DateTime endTime) throws Exception {
        long pageNo = 1;
        while (true) {
            TradesSoldIncrementGetRequest request = new TradesSoldIncrementGetRequest();
            request.setFields(Joiner.on(",").join(Constants.TRADE_FIELDS));
            request.setType(Joiner.on(",").join(Constants.TRADE_TYPES));
            request.setStartModified(startTime.toDate());
            request.setEndModified(endTime.toDate());
            request.setPageNo(pageNo);
            request.setPageSize(100L);
            request.setUseHasNext(true);


            TradesSoldIncrementGetResponse response = taobaoApiService.request(request, appKey, sessionKey);

            if (response.isSuccess()) {
                List<Trade> trades = response.getTrades();
                if (CollectionUtils.isEmpty(trades)) {
                    break;
                } else {
                    pageNo++;
                    processTrades(trades);
                }
            } else {
                syncTradeFail();
                break;
            }

        }
    }
}
