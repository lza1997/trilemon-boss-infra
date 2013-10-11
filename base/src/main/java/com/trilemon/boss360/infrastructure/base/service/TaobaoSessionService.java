package com.trilemon.boss360.infrastructure.base.service;

import com.trilemon.boss360.infrastructure.base.dao.TaobaoSessionMapper;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kevin
 */
@Service
public class TaobaoSessionService {
    @Autowired
    private TaobaoSessionMapper taobaoSessionMapper;

    public TaobaoSession getTaobaoSession(Long userId) {
        return taobaoSessionMapper.selectByUserId(userId);
    }

    public TaobaoSession getTaobaoSession(String nick) {
        return taobaoSessionMapper.selectByNick(nick);
    }
}
