package com.trilemon.boss360.infrastructure.base.service.api.exception;

import com.taobao.api.TaobaoRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author kevin
 */
public class TaobaoSessionExpiredException extends Exception {
    private Long userId;
    private String nick;
    private TaobaoRequest taobaoRequest;

    public TaobaoSessionExpiredException(TaobaoRequest taobaoRequest) {
        this.taobaoRequest = taobaoRequest;
    }

    public TaobaoSessionExpiredException(String message, TaobaoRequest taobaoRequest) {
        super(message);
        this.taobaoRequest = taobaoRequest;
    }

    public TaobaoSessionExpiredException(String message, Throwable cause, TaobaoRequest taobaoRequest) {
        super(message, cause);
        this.taobaoRequest = taobaoRequest;
    }

    public TaobaoSessionExpiredException(Throwable cause, TaobaoRequest taobaoRequest) {
        super(cause);
        this.taobaoRequest = taobaoRequest;
    }

    public TaobaoSessionExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, TaobaoRequest taobaoRequest) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.taobaoRequest = taobaoRequest;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TaobaoRequest getTaobaoRequest() {
        return taobaoRequest;
    }

    public void setTaobaoRequest(TaobaoRequest taobaoRequest) {
        this.taobaoRequest = taobaoRequest;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
