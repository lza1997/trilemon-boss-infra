package com.trilemon.boss.infra.base.model.dto;

import java.io.Serializable;

/**
 * @author kevin
 */
public class SignIn implements Serializable {
    private String signInIp;
    private String itemCode;
    private String version;
    private String outerTradeCode;
    private String scope;
    private String sign;

    public String getSignInIp() {
        return signInIp;
    }

    public void setSignInIp(String signInIp) {
        this.signInIp = signInIp;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getOuterTradeCode() {
        return outerTradeCode;
    }

    public void setOuterTradeCode(String outerTradeCode) {
        this.outerTradeCode = outerTradeCode;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
