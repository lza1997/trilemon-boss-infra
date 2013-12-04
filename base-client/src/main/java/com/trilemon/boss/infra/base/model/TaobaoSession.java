package com.trilemon.boss.infra.base.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class TaobaoSession implements Serializable {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String appKey;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("taobao_user_id")
    private Long taobaoUserId;
    @JsonProperty("taobao_user_nick")
    private String taobaoUserNick;
    @JsonProperty("expires_iun")
    private Integer expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("re_expires_in")
    private Integer reExpiresIn;
    @JsonProperty("sub_taobao_user_id")
    private Long subTaobaoUserId;
    @JsonProperty("sub_taobao_user_nick")
    private String subTaobaoUserNick;
    @JsonProperty("r1_expires_in")
    private Integer r1ExpiresIn;
    @JsonProperty("w1_expires_in")
    private Integer w1ExpiresIn;
    @JsonProperty("r2_expires_in")
    private Integer r2ExpiresIn;
    @JsonProperty("w2_expires_in")
    private Integer w2ExpiresIn;
    @JsonProperty("state")
    private String state;
    @JsonIgnore
    private Date addTime;
    @JsonIgnore
    private Date updTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType == null ? null : tokenType.trim();
    }

    public Long getTaobaoUserId() {
        return taobaoUserId;
    }

    public void setTaobaoUserId(Long taobaoUserId) {
        this.taobaoUserId = taobaoUserId;
    }

    public String getTaobaoUserNick() {
        return taobaoUserNick;
    }

    public void setTaobaoUserNick(String taobaoUserNick) {
        this.taobaoUserNick = taobaoUserNick == null ? null : taobaoUserNick.trim();
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken == null ? null : refreshToken.trim();
    }

    public Integer getReExpiresIn() {
        return reExpiresIn;
    }

    public void setReExpiresIn(Integer reExpiresIn) {
        this.reExpiresIn = reExpiresIn;
    }

    public Long getSubTaobaoUserId() {
        return subTaobaoUserId;
    }

    public void setSubTaobaoUserId(Long subTaobaoUserId) {
        this.subTaobaoUserId = subTaobaoUserId;
    }

    public String getSubTaobaoUserNick() {
        return subTaobaoUserNick;
    }

    public void setSubTaobaoUserNick(String subTaobaoUserNick) {
        this.subTaobaoUserNick = subTaobaoUserNick == null ? null : subTaobaoUserNick.trim();
    }

    public Integer getR1ExpiresIn() {
        return r1ExpiresIn;
    }

    public void setR1ExpiresIn(Integer r1ExpiresIn) {
        this.r1ExpiresIn = r1ExpiresIn;
    }

    public Integer getW1ExpiresIn() {
        return w1ExpiresIn;
    }

    public void setW1ExpiresIn(Integer w1ExpiresIn) {
        this.w1ExpiresIn = w1ExpiresIn;
    }

    public Integer getR2ExpiresIn() {
        return r2ExpiresIn;
    }

    public void setR2ExpiresIn(Integer r2ExpiresIn) {
        this.r2ExpiresIn = r2ExpiresIn;
    }

    public Integer getW2ExpiresIn() {
        return w2ExpiresIn;
    }

    public void setW2ExpiresIn(Integer w2ExpiresIn) {
        this.w2ExpiresIn = w2ExpiresIn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }
}