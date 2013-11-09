package com.trilemon.boss360.infrastructure.base.service.api.exception;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoRequest;
import com.taobao.api.TaobaoResponse;

/**
 * 淘宝的 response（非 success 状态） 和{@link ApiException}全部用这个异常表达
 *
 * @author kevin
 */
public class TaobaoEnhancedApiException extends BaseTaobaoApiException {
    private TaobaoResponse taobaoResponse;
    private TaobaoRequest taobaoRequest;
    private ApiException apiException;

    public TaobaoEnhancedApiException(TaobaoRequest request, ApiException apiException) {
        this.apiException = apiException;
        this.taobaoRequest = request;
    }

    public TaobaoEnhancedApiException(TaobaoRequest request, TaobaoResponse response) {
        this.taobaoRequest = request;
        this.taobaoResponse = response;
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

    public ApiException getApiException() {
        return apiException;
    }

    public void setApiException(ApiException apiException) {
        this.apiException = apiException;
    }
}
