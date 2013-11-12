package com.trilemon.boss.infra.trade.model;

import java.util.Date;

public class TradeAsync {
    private Long id;

    private Long userId;

    private String syncAppKey;

    private Byte syncStatus;

    private Date tradeStartTime;

    private Date tradeEndTime;

    private String downloadFile;

    private Date syncStartTime;

    private Date syncEndTime;

    private String serviceName;

    private String serviceId;

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

    public Byte getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Byte syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Date getTradeStartTime() {
        return tradeStartTime;
    }

    public void setTradeStartTime(Date tradeStartTime) {
        this.tradeStartTime = tradeStartTime;
    }

    public Date getTradeEndTime() {
        return tradeEndTime;
    }

    public void setTradeEndTime(Date tradeEndTime) {
        this.tradeEndTime = tradeEndTime;
    }

    public String getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(String downloadFile) {
        this.downloadFile = downloadFile == null ? null : downloadFile.trim();
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId == null ? null : serviceId.trim();
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