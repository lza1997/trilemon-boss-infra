package com.trilemon.boss360.infrastructure.trade;

/**
 * @author kevin
 */
public interface TradeConstants {
    //async status
    byte ASYNC_STATUS_DEFAULT = 0;
    byte ASYNC_STATUS_INIT = 1;
    byte ASYNC_STATUS_DOING = 2;
    byte ASYNC_STATUS_SUCCESSFUL = 3;
    byte ASYNC_STATUS_FAILED = 4;
    //sync status
    byte SYNC_STATUS_DEFAULT = 0;
    byte SYNC_STATUS_DOING = 2;
    byte SYNC_STATUS_SUCCESSFUL = 3;
    byte SYNC_STATUS_FAILED = 4;
    //sync check status
    byte SYNC_CHECK_STATUS_DEFAULT = 0;
    byte SYNC_CHECK_STATUS_INIT = 1;
    byte SYNC_CHECK_STATUS_DOING = 2;
    byte SYNC_CHECK_STATUS_SUCCESSFUL = 3;
    byte SYNC_CHECK_STATUS_FAILED = 4;
}
