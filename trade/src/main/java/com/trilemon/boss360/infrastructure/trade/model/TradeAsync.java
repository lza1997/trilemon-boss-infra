package com.trilemon.boss360.infrastructure.trade.model;

import java.util.Date;

public class TradeAsync {
    private Long id;
    private Long userId;
    private Long shopId;
    private String syncAppKey;
    private Byte syncStatus;
    private Date tradeStartTime;
    private Date tradeEndTime;
    private String downloadFile;
    private Date syncStartTime;
    private Date syncEndTime;
    private String serverName;
    private String serverId;
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

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
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

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId == null ? null : serverId.trim();
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