package com.trilemon.boss.infra.base.web.controller;

import com.google.common.collect.Lists;
import com.trilemon.boss.infra.base.BaseConstants;
import com.trilemon.boss.infra.base.client.BaseClient;
import com.trilemon.boss.infra.base.model.AppUser;
import com.trilemon.boss.infra.base.model.BuyerBlacklist;
import com.trilemon.boss.infra.base.model.TaobaoSession;
import com.trilemon.boss.infra.base.model.dto.SignIn;
import com.trilemon.boss.infra.base.service.AppUserService;
import com.trilemon.boss.infra.base.service.TaobaoSessionService;
import com.trilemon.boss.infra.base.web.auth.shiro.ShiroTaobaoAuthenticationToken;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author kevin
 */
@RequestMapping("/test")
@Controller
public class TestController {
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private TaobaoSessionService taobaoSessionService;

    @ResponseBody
    @RequestMapping(value = "/addBlacklist", method = RequestMethod.GET)
    public String addBlacklist(@RequestParam Long userId,
                               @RequestParam String buyerNick) {
        BuyerBlacklist buyerBlacklist = new BuyerBlacklist();
        buyerBlacklist.setUserId(userId);
        buyerBlacklist.setAddTime(DateTime.now().toDate());
        buyerBlacklist.setStatus((byte) 1);
        buyerBlacklist.setType(BaseConstants.BLACKLIST_TYPE_RATE);
        buyerBlacklist.setBuyerNick(buyerNick);
        baseClient.addBuyerBlacklist(buyerBlacklist);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/addBlacklists", method = RequestMethod.GET)
    public String addBlacklists() {
        BuyerBlacklist buyerBlacklist1 = new BuyerBlacklist();
        buyerBlacklist1.setUserId(1L);
        buyerBlacklist1.setAddTime(DateTime.now().toDate());
        buyerBlacklist1.setStatus((byte) 1);
        buyerBlacklist1.setType(BaseConstants.BLACKLIST_TYPE_RATE);
        buyerBlacklist1.setBuyerNick("guyan");
        baseClient.addBuyerBlacklist(buyerBlacklist1);

        BuyerBlacklist buyerBlacklist2 = new BuyerBlacklist();
        buyerBlacklist2.setUserId(2L);
        buyerBlacklist2.setAddTime(DateTime.now().toDate());
        buyerBlacklist2.setStatus((byte) 1);
        buyerBlacklist2.setType(BaseConstants.BLACKLIST_TYPE_RATE);
        buyerBlacklist2.setBuyerNick("chenlu");
        baseClient.addBuyerBlacklists(Lists.newArrayList(buyerBlacklist1, buyerBlacklist2));
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteBlacklists", method = RequestMethod.GET)
    public String deleteBlacklists(@RequestParam Long userId,
                                   @RequestParam String buyerNick) {
        baseClient.deleteBuyerBlacklist(userId, buyerNick, BaseConstants.BLACKLIST_TYPE_RATE);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/updateBlacklist", method = RequestMethod.GET)
    public String updateBlacklist() {
        BuyerBlacklist buyerBlacklist1 = new BuyerBlacklist();
        buyerBlacklist1.setUserId(1L);
        buyerBlacklist1.setAddTime(DateTime.now().toDate());
        buyerBlacklist1.setStatus((byte) 2);
        buyerBlacklist1.setType(BaseConstants.BLACKLIST_TYPE_RATE);
        buyerBlacklist1.setBuyerNick("guyan");

        baseClient.updateBuyerBlacklist(buyerBlacklist1);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/getAppUser", method = RequestMethod.GET)
    public AppUser getAppUser(@RequestParam Long userId,
                              @RequestParam String appKey) {
        return appUserService.getAppUser(userId, appKey);
    }

    @ResponseBody
    @RequestMapping(value = "/expire", method = RequestMethod.GET)
    public int expire(@RequestParam Long userId,
                      @RequestParam String appKey) {
        return appUserService.expire(userId, appKey);
    }

    @ResponseBody
    @RequestMapping(value = "/refreshSession", method = RequestMethod.GET)
    public String refreshSession(@RequestParam Long userId,
                                 @RequestParam String appKey) throws Exception {
        taobaoSessionService.refreshSession(userId, appKey);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/getTaobaoSession", method = RequestMethod.GET)
    public TaobaoSession getTaobaoSession(@RequestParam Long userId, @RequestParam(required = false) Long subUserId,
                                          @RequestParam String appKey) throws Exception {
        return taobaoSessionService.getTaobaoSession(userId, subUserId, appKey);
    }

    @ResponseBody
    @RequestMapping(value = "/getTaobaoSessionBySubTaobaoUserId", method = RequestMethod.GET)
    public TaobaoSession getTaobaoSessionBySubTaobaoUserId(@RequestParam Long subUserId,
                                                           @RequestParam String appKey) throws Exception {
        return taobaoSessionService.getTaobaoSessionBySubTaobaoUserId(subUserId, appKey);
    }

    @ResponseBody
    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public AppUser sign() throws Exception {
        TaobaoSession taobaoSession = taobaoSessionService.getTaobaoSession(56912708L, null, "21685043");
        taobaoSession.setId(null);
        SignIn signIn = new SignIn();
        return taobaoSessionService.signIn(taobaoSession, signIn);
    }

    @ResponseBody
    @RequestMapping(value = "/signIn2", method = RequestMethod.GET)
    public AppUser signIn2() throws Exception {
        ShiroTaobaoAuthenticationToken token = new ShiroTaobaoAuthenticationToken();
        return taobaoSessionService.singIn(token);
    }
}
