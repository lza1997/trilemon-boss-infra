package com.trilemon.boss360.infrastructure.base.service;

import com.trilemon.boss360.infrastructure.base.dao.TaobaoSessionMapper;
import com.trilemon.boss360.infrastructure.base.model.TaobaoApp;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;
import com.trilemon.boss360.infrastructure.base.util.TopApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kevin
 */
@Service
public class TaobaoSessionService {
    @Autowired
    private TaobaoAppService taobaoAppService;
    @Autowired
    private TaobaoSessionMapper taobaoSessionMapper;

    public TaobaoSession getTaobaoSession(Long userId, String appKey) {
        return taobaoSessionMapper.selectByTaobaoUserIdAndAppKey(userId, appKey);
    }

    public void refreshSession(TaobaoSession taobaoSession) throws Exception {
        TaobaoApp taobaoApp = taobaoAppService.getTaobaoApp(taobaoSession.getAppKey());
        TopApiUtils.refreshSessionKey(taobaoApp.getAppKey(), taobaoApp.getAppSecret(), taobaoSession.getAccessToken(),
                taobaoSession.getRefreshToken());
    }

    public void saveOrUpdateTaobaoSession(TaobaoSession taobaoSession) {
        Long userId = taobaoSession.getSubTaobaoUserId();
        String userNick = taobaoSession.getSubTaobaoUserNick();

        TaobaoSession existTaobaoSession;
        if (null != userId && null != userNick) {
            existTaobaoSession = taobaoSessionMapper.selectBySubTaobaoUserIdAppKey(taobaoSession.getSubTaobaoUserId(), taobaoSession.getAppKey());
        } else {
            existTaobaoSession = taobaoSessionMapper.selectByTaobaoUserIdAndAppKey(taobaoSession.getTaobaoUserId(), taobaoSession.getAppKey());
        }

        if (null == existTaobaoSession) {
            taobaoSessionMapper.insertSelective(taobaoSession);
        }else{
            taobaoSession.setId(existTaobaoSession.getId());
            taobaoSessionMapper.updateByPrimaryKeySelective(taobaoSession);
        }
    }
}
