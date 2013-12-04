package com.trilemon.boss.infra.base.web.auth;

/**
 * @author kevin
 */
public class TaobaoOauthException extends Exception {
    public TaobaoOauthException(Exception e) {
        super(e);
    }

    public TaobaoOauthException(String msg, Exception e) {
        super(msg, e);
    }

    public TaobaoOauthException(String msg) {
        super(msg);
    }
}
