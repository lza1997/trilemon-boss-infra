package com.trilemon.boss.infra.base.service;

import com.trilemon.boss.infra.base.dao.TaobaoApiUsageDAO;
import com.trilemon.boss.infra.base.model.TaobaoApiUsage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kevin
 */
@Service
public class TaobaoApiUsageService {
    @Autowired
    private TaobaoApiUsageDAO taobaoApiUsageDAO;

    public void updateApiUsage(List<TaobaoApiUsage> taobaoApiUsageList) {
        //taobaoApiUsageMapper.batchInsert(taobaoApiUsageList);
    }
}
