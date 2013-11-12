package com.trilemon.boss.infra.sms.client;

import com.trilemon.boss.infra.sms.model.SendStatus;

/**
 * @author kevin
 */
public interface SmsClient {
    SendStatus send(String phone, String msg);

    SendStatus send(String[] phone, String msg);
}
