package com.trilemon.boss.infra.base.service;

import com.trilemon.boss.infra.base.BaseConstants;
import com.trilemon.boss.infra.base.dao.AppUserDAO;
import com.trilemon.boss.infra.base.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kevin
 */
@Service
public class AppUserService {
    @Autowired
    private AppUserDAO appUserDAO;

    public AppUser getAppUser(Long userId,String appKey) {
        return appUserDAO.selectByUserIdAndAppKey(userId,appKey);
    }

    public int expire(Long userId,String appKey) {
        AppUser appUser = new AppUser();
        appUser.setUserId(userId);
        appUser.setAppKey(appKey);
        appUser.setStatus(BaseConstants.APP_USER_STATUS_EXPIRED);
        return appUserDAO.updateByUserIdAndAppKeySelective(appUser);
    }
}
