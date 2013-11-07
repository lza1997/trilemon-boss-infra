package com.trilemon.boss360.infrastructure.sms.client;

import com.trilemon.boss360.infrastructure.sms.model.SendStatus;

/**
 * @author kevin
 */
public interface SmsClient {
    SendStatus send(String phone, String msg);

    SendStatus send(String[] phone, String msg);
}
