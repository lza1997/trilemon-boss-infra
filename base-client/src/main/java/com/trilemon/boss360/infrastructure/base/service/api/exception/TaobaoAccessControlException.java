package com.trilemon.boss360.infrastructure.base.service.api.exception;

import com.taobao.api.TaobaoRequest;
import com.taobao.api.TaobaoResponse;

/**
 * @author kevin
 */
public class TaobaoAccessControlException extends BaseTaobaoApiException {
    private int banMinus;
    private TaobaoResponse taobaoResponse;
    private TaobaoRequest taobaoRequest;

    public TaobaoAccessControlException(TaobaoRequest request, TaobaoResponse response) {
        this.taobaoRequest = request;
        this.taobaoResponse = response;
    }

    public int getBanMinus() {
        return banMinus;
    }

    public void setBanMinus(int banMinus) {
        this.banMinus = banMinus;
    }

    public TaobaoResponse getTaobaoResponse() {
        return taobaoResponse;
    }

    public void setTaobaoResponse(TaobaoResponse taobaoResponse) {
        this.taobaoResponse = taobaoResponse;
    }

    public TaobaoRequest getTaobaoRequest() {
        return taobaoRequest;
    }

    public void setTaobaoRequest(TaobaoRequest taobaoRequest) {
        this.taobaoRequest = taobaoRequest;
    }
}
