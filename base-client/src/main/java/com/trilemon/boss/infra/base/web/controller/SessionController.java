package com.trilemon.boss.infra.base.web.controller;

import com.google.common.collect.Lists;
import com.trilemon.boss.infra.base.BaseUtils;
import com.trilemon.boss.infra.base.service.AppService;
import com.trilemon.boss.infra.base.service.TaobaoApiService;
import com.trilemon.commons.web.shiro.WebUtils2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author kevin
 */
@Controller
@RequestMapping("/session")
public class SessionController {
    @Autowired
    private Environment environment;
    @Autowired
    private TaobaoApiService taobaoApiService;
    @Autowired
    private AppService appService;

    @RequestMapping(value = "/cas",method = RequestMethod.GET)
    public String cas() {
        String url = WebUtils2.getSavedTargetUrl();
        if (Lists.newArrayList(environment.getActiveProfiles()).contains("development")) {
            return "redirect:" + appService.getCasUrl() + "/signIn?redirect=" + url;
        } else {
            String getCodeUrl = BaseUtils.buildDefaultTaobaoOauthAuthorizeURI(taobaoApiService.getAppKey(),
                    appService.getTaobaoCallbackUrl(), "");
            return "redirect:" + getCodeUrl;
        }
    }
}
