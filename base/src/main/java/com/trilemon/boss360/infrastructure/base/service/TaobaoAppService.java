package com.trilemon.boss360.infrastructure.base.service;

import com.trilemon.boss360.infrastructure.base.dao.TaobaoAppMapper;
import com.trilemon.boss360.infrastructure.base.model.TaobaoApp;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kevin
 */
public class TaobaoAppService {
    @Autowired
    private TaobaoAppMapper taobaoAppMapper;

    public TaobaoApp getTaobaoApp(String taobaoAppKey){
         return taobaoAppMapper.selectByAppKey(taobaoAppKey);
    }
}
