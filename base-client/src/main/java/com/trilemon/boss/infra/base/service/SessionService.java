package com.trilemon.boss.infra.base.service;

import com.trilemon.boss.infra.base.model.AppUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.Map;


/**
 * @author kevin
 */
public class SessionService {
    public AppUser getCurrentAppUser() {
        Subject subject = SecurityUtils.getSubject();
        Map<String, Object> map = (Map<String, Object>) subject.getPrincipals().asList().get(1);
        return (AppUser) map.get("appUser");
    }

    public Session getSession() {
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession();
    }

    public Long getUserId() {
        Subject subject = SecurityUtils.getSubject();
        return (Long) subject.getPrincipals().asList().get(0);
    }
}
