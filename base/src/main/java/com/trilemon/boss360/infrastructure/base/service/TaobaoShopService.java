package com.trilemon.boss360.infrastructure.base.service;

import com.google.common.base.Joiner;
import com.taobao.api.ApiException;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradesSoldGetResponse;
import com.trilemon.boss360.infrastructure.base.BaseConstants;
import com.trilemon.boss360.infrastructure.base.serivce.EnhancedApiException;
import com.trilemon.boss360.infrastructure.base.serivce.TaobaoApiService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kevin
 */
@Service
public class TaobaoShopService {
    private static Logger logger = LoggerFactory.getLogger(TaobaoShopService.class);
    @Autowired
    private TaobaoApiService taobaoApiService;

    public long getTradeNumFromTop(String appKey, String sessionKey, DateTime startHour,
                                   DateTime endHour) throws EnhancedApiException, ApiException {
        TradesSoldGetRequest request = new TradesSoldGetRequest();
        request.setFields("tid");
        request.setType(Joiner.on(",").join(BaseConstants.TRADE_TYPES));
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
