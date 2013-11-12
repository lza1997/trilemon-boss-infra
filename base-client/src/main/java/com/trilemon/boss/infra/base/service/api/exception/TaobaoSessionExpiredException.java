package com.trilemon.boss.infra.base.service.api.exception;

import com.taobao.api.TaobaoRequest;
import com.taobao.api.TaobaoResponse;

/**
 * taobao session 过期了
 *
 * @author kevin
 */
public class TaobaoSessionExpiredException extends BaseTaobaoApiException {
    private Long userId;
    private String nick;
    private TaobaoResponse taobaoResponse;
    private TaobaoRequest taobaoRequest;

    public TaobaoSessionExpiredException(TaobaoRequest request, TaobaoResponse response) {
        this.taobaoRequest = request;
        this.taobaoResponse = response;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
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