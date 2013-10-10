package com.trilemon.boss360.infrastructure.trade.service;

import com.google.common.base.Joiner;
import com.taobao.api.ApiException;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradesSoldGetResponse;
import com.trilemon.boss360.base.serivce.EnhancedApiException;
import com.trilemon.boss360.base.serivce.TaobaoApiService;
import com.trilemon.boss360.infrastructure.trade.Constants;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kevin
 */
@Service
public class ShopService {
    private static Logger logger = LoggerFactory.getLogger(ShopService.class);
    @Autowired
    private TaobaoApiService taobaoApiService;

    public long getTradeNumFromTop(String appKey, String sessionKey, DateTime startHour,
                               DateTime endHour) throws EnhancedApiException, ApiException {
        TradesSoldGetRequest request = new TradesSoldGetRequest();
        request.setFields("tid");
        request.setType(Joiner.on(",").join(Constants.TRADE_TYPES));
        request.setStartCreated(startHour.toDate());
        request.setEndCreated(endHour.toDate());
        request.setPageNo(1L);
        request.setPageSize(1L);
        request.setUseHasNext(false);


        TradesSoldGetResponse response = taobaoApiService.request(request, appKey, sessionKey);
        if (response.isSuccess()) {
            return response.getTotalResults();
        } else {
            throw new EnhancedApiException(request, response);
        }
    }
}
