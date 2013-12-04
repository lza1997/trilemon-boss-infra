package com.trilemon.boss.infra.base.service;

import com.taobao.api.domain.Shop;
import com.taobao.api.domain.User;
import com.trilemon.boss.infra.base.BaseConstants;
import com.trilemon.boss.infra.base.dao.BuyerBlacklistDAO;
import com.trilemon.boss.infra.base.dao.TaobaoSellerDAO;
import com.trilemon.boss.infra.base.dao.TaobaoShopDAO;
import com.trilemon.boss.infra.base.model.BuyerBlacklist;
import com.trilemon.boss.infra.base.model.TaobaoSeller;
import com.trilemon.boss.infra.base.model.TaobaoShop;
import com.trilemon.boss.infra.base.service.api.TaobaoApiShopService;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoAccessControlException;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoEnhancedApiException;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoSessionExpiredException;
import com.trilemon.commons.BeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kevin
 */
@Service
public class TaobaoShopService {
    private static Logger logger = LoggerFactory.getLogger(TaobaoShopService.class);
    @Autowired
    private TaobaoSellerDAO taobaoSellerDAO;
    @Autowired
    private TaobaoShopDAO taobaoShopDAO;
    @Autowired
    private AppService appService;
    @Autowired
    private TaobaoApiShopService taobaoApiShopService;
    @Autowired
    private BuyerBlacklistDAO buyerBlacklistDAO;

    public String getNick(Long userId) {
        TaobaoSeller taobaoSeller = taobaoSellerDAO.selectByPrimaryKey(userId);
        if (null == taobaoSeller) {
            return null;
        } else {
            return taobaoSeller.getNick();
        }
    }

    public TaobaoSeller getSeller(Long taobaoUserId) {
        TaobaoSeller taobaoSeller = taobaoSellerDAO.selectByPrimaryKey(taobaoUserId);
        return taobaoSeller;
    }

    public TaobaoSeller createSeller(Long userId) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException,
            TaobaoAccessControlException {

        User user = taobaoApiShopService.getTaobaoUser(userId, BaseConstants.SELLER_FIELDS);
        TaobaoSeller taobaoSeller = BeanMapper.map(user, TaobaoSeller.class);
        taobaoSeller.setAddTime(appService.getLocalSystemTime().toDate());
        taobaoSellerDAO.replaceSelective(taobaoSeller);
        return taobaoSeller;
    }

    public TaobaoShop createShop(Long userId, String nick) throws TaobaoEnhancedApiException,
            TaobaoSessionExpiredException, TaobaoAccessControlException {
        Shop shop = taobaoApiShopService.getTaobaoShop(userId, nick, BaseConstants.SHOP_FIELDS);
        TaobaoShop taobaoShop = BeanMapper.map(shop, TaobaoShop.class);
        taobaoShop.setAddTime(appService.getLocalSystemTime().toDate());
        taobaoShopDAO.replaceSelective(taobaoShop);
        return taobaoShop;
    }

    public void addBuyerBlacklist(BuyerBlacklist buyerBlacklist) {
        buyerBlacklistDAO.insertSelective(buyerBlacklist);
    }

    public void addBuyerBlacklists(List<BuyerBlacklist> buyerBlacklists) {
        buyerBlacklistDAO.batchInsert(buyerBlacklists);
    }

    public void updateBuyerBlacklist(BuyerBlacklist buyerBlacklist) {
        buyerBlacklistDAO.updateByUserIdAndBuyerNickAndType(buyerBlacklist);

    }

    public void deleteBuyerBlacklist(Long userId, String buyerNick, byte type) {
        buyerBlacklistDAO.deleteByUserIdAndBuyerNickAndType(userId, buyerNick, type);
    }

    public List<BuyerBlacklist> paginateBuyerBlacklist(long userId, byte type, int pageNum, int pageSize, String sortField, String sortType) {
        return buyerBlacklistDAO.paginateBuyerBlacklist(userId, type, (pageNum - 1) * pageSize, pageSize, sortField, sortType);
    }

    public BuyerBlacklist getBuyerBlacklist(Long userId, String buyerNick) {
        return buyerBlacklistDAO.selectByUserIdAndBuyerNick(userId, buyerNick);
    }
}
