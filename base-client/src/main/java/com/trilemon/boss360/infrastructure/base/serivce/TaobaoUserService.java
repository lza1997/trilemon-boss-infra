package com.trilemon.boss360.infrastructure.base.serivce;

import com.trilemon.boss360.infrastructure.base.client.BaseClient;
import com.trilemon.boss360.infrastructure.base.module.TaobaoSession;
import org.springframework.stereotype.Service;

/**
 * @author kevin
 */
@Service
public class TaobaoUserService {
    private BaseClient baseClient;
    public TaobaoSession getTaobaoSession(String nick) {
        return baseClient.getTaobaoSession(nick);
    }

    public TaobaoSession getTaobaoSession(long userId) {
        return baseClient.getTaobaoSession(userId);
    }
}
