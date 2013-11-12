package com.trilemon.boss.infra.trade.model;

import java.util.Date;

public class TradeSync {
    private Long id;

    private Long userId;

    private String syncAppKey;

    private Date syncTradeStartTime;

    private Date syncTradeEndTime;

    private Date syncStartTime;

    private Date syncEndTime;

    private Byte syncStatus;

    private Byte syncLock;

    private String syncServiceName;

    private String syncServiceId;

    private Date checkTradeStartTime;

    private Date checkTradeEndTime;

    private Date checkStartTime;

    private Date checkEndTime;

    private Byte checkStatus;

    private Byte checkLock;

    private String checkServiceName;

    private String checkServiceId;

    private Date addTime;

    private Date updTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSyncAppKey() {
        return syncAppKey;
    }

    public void setSyncAppKey(String syncAppKey) {
        this.syncAppKey = syncAppKey == null ? null : syncAppKey.trim();
    }

    public Date getSyncTradeStartTime() {
        return syncTradeStartTime;
    }

    public void setSyncTradeStartTime(Date syncTradeStartTime) {
        this.syncTradeStartTime = syncTradeStartTime;
    }

    public Date getSyncTradeEndTime() {
        return syncTradeEndTime;
    }

    public void setSyncTradeEndTime(Date syncTradeEndTime) {
        this.syncTradeEndTime = syncTradeEndTime;
    }

    public Date getSyncStartTime() {
        return syncStartTime;
    }

    public void setSyncStartTime(Date syncStartTime) {
        this.syncStartTime = syncStartTime;
    }

    public Date getSyncEndTime() {
        return syncEndTime;
    }

    public void setSyncEndTime(Date syncEndTime) {
        this.syncEndTime = syncEndTime;
    }

    public Byte getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Byte syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Byte getSyncLock() {
        return syncLock;
    }

    public void setSyncLock(Byte syncLock) {
        this.syncLock = syncLock;
    }

    public String getSyncServiceName() {
        return syncServiceName;
    }

    public void setSyncServiceName(String syncServiceName) {
        this.syncServiceName = syncServiceName == null ? null : syncServiceName.trim();
    }

    public String getSyncServiceId() {
        return syncServiceId;
    }

    public void setSyncServiceId(String syncServiceId) {
        this.syncServiceId = syncServiceId == null ? null : syncServiceId.trim();
    }

    public Date getCheckTradeStartTime() {
        return checkTradeStartTime;
    }

    public void setCheckTradeStartTime(Date checkTradeStartTime) {
        this.checkTradeStartTime = checkTradeStartTime;
    }

    public Date getCheckTradeEndTime() {
        return checkTradeEndTime;
    }

    public void setCheckTradeEndTime(Date checkTradeEndTime) {
        this.checkTradeEndTime = checkTradeEndTime;
    }

    public Date getCheckStartTime() {
        return checkStartTime;
    }

    public void setCheckStartTime(Date checkStartTime) {
        this.checkStartTime = checkStartTime;
    }

    public Date getCheckEndTime() {
        return checkEndTime;
    }

    public void setCheckEndTime(Date checkEndTime) {
        this.checkEndTime = checkEndTime;
    }

    public Byte getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Byte checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Byte getCheckLock() {
        return checkLock;
    }

    public void setCheckLock(Byte checkLock) {
        this.checkLock = checkLock;
    }

    public String getCheckServiceName() {
        return checkServiceName;
    }

    public void setCheckServiceName(String checkServiceName) {
        this.checkServiceName = checkServiceName == null ? null : checkServiceName.trim();
    }

    public String getCheckServiceId() {
        return checkServiceId;
    }

    public void setCheckServiceId(String checkServiceId) {
        this.checkServiceId = checkServiceId == null ? null : checkServiceId.trim();
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