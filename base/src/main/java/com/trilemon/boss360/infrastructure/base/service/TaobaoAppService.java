package com.trilemon.boss360.infrastructure.base.service;

import com.trilemon.boss360.infrastructure.base.dao.TaobaoAppMapper;
import com.trilemon.boss360.infrastructure.base.module.TaobaoApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kevin
 */
@Service
public class TaobaoAppService {
    @Autowired
    private TaobaoAppMapper taobaoAppMapper;

    public TaobaoApp getTaobaoApp(String taobaoAppKey) {
        return taobaoAppMapper.selectByAppKey(taobaoAppKey);
    }
}
