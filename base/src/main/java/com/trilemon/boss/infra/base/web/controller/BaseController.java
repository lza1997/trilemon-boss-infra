package com.trilemon.boss.infra.base.web.controller;

import com.google.common.collect.Lists;
import com.trilemon.boss.infra.base.BaseConstants;
import com.trilemon.boss.infra.base.client.BaseClient;
import com.trilemon.boss.infra.base.model.BuyerBlacklist;
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
public class BaseController {
    @Autowired
    private BaseClient baseClient;

    @ResponseBody
    @RequestMapping(value = "/addBlacklist",method = RequestMethod.GET)
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
    @RequestMapping(value = "/addBlacklists",method = RequestMethod.GET)
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
    @RequestMapping(value = "/deleteBlacklists",method = RequestMethod.GET)
    public String deleteBlacklists(@RequestParam Long userId,
                                   @RequestParam String buyerNick) {
        baseClient.deleteBuyerBlacklist(userId, buyerNick, BaseConstants.BLACKLIST_TYPE_RATE);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/updateBlacklist",method = RequestMethod.GET)
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
}
