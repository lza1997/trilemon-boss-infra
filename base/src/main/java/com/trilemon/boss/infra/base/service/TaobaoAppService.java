package com.trilemon.boss.infra.base.service;

import com.trilemon.boss.infra.base.dao.TaobaoAppDAO;
import com.trilemon.boss.infra.base.model.TaobaoApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kevin
 */
@Service
public class TaobaoAppService {
    @Autowired
    private TaobaoAppDAO taobaoAppDAO;

    public TaobaoApp getTaobaoApp(String taobaoAppKey) {
        return taobaoAppDAO.selectByAppKey(taobaoAppKey);
    }
}
