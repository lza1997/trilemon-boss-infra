package com.trilemon.boss.infra.base.service;

import com.taobao.api.domain.User;
import com.taobao.api.request.UserSellerGetRequest;
import com.taobao.api.response.UserSellerGetResponse;
import com.trilemon.boss.infra.base.dao.BuyerBlacklistMapper;
import com.trilemon.boss.infra.base.dao.TaobaoSellerMapper;
import com.trilemon.boss.infra.base.model.BuyerBlacklist;
import com.trilemon.boss.infra.base.model.TaobaoSeller;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoAccessControlException;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoEnhancedApiException;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoSessionExpiredException;
import com.trilemon.commons.BeanMapper;
import com.trilemon.commons.mybatis.MyBatisBatchWriter;
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
    private TaobaoSellerMapper taobaoSellerMapper;
    @Autowired
    private TaobaoApiService taobaoApiService;
    @Autowired
    private BuyerBlacklistMapper blacklistMapper;
    @Autowired
    private MyBatisBatchWriter<BuyerBlacklist> myBatisBatchWriter;

    public String getNick(Long userId) {
        TaobaoSeller taobaoSeller = taobaoSellerMapper.selectByUserId(userId);
        if (null == taobaoSeller) {
            return null;
        } else {
            return taobaoSeller.getNick();
        }
    }

    public TaobaoSeller getSeller(Long taobaoUserId) {
        TaobaoSeller taobaoSeller = taobaoSellerMapper.selectByUserId(taobaoUserId);
        return taobaoSeller;
    }

    public TaobaoSeller createSeller(String accessToken, String appKey) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        UserSellerGetRequest req = new UserSellerGetRequest();
        req.setFields("nick");
        UserSellerGetResponse response = taobaoApiService.requestWithAppKey(req, appKey, accessToken);
        User user = response.getUser();
        TaobaoSeller taobaoSeller = BeanMapper.map(user, TaobaoSeller.class);
        taobaoSellerMapper.insertSelective(taobaoSeller);
        return taobaoSeller;
    }

    public void addBuyerBlacklist(BuyerBlacklist buyerBlacklist) {
        blacklistMapper.insertSelective(buyerBlacklist);
    }

    public void addBuyerBlacklists(List<BuyerBlacklist> buyerBlacklists) {
        myBatisBatchWriter.write("com.trilemon.boss.infra.base.dao.BuyerBlacklistMapper.insertSelective", buyerBlacklists);
    }

    public void updateBuyerBlacklist(BuyerBlacklist buyerBlacklist) {
        blacklistMapper.updateByUserIdAndBuyerNickAndType(buyerBlacklist);

    }

    public void deleteBuyerBlacklist(Long userId,String buyerNick,byte type) {
        blacklistMapper.deleteByUserIdAndBuyerNickAndType(userId,buyerNick,type);
    }

    public List<BuyerBlacklist>  paginateBuyerBlacklist(long userId, byte type, int pageNum, int pageSize, String sortField, String sortType) {
       return blacklistMapper.paginateBuyerBlacklist(userId,type,(pageNum-1)*pageSize,pageSize,sortField,sortType);
    }

    public BuyerBlacklist getBuyerBlacklist(Long userId, String buyerNick) {
        return blacklistMapper.selectByUserIdAndBuyerNick(userId,buyerNick);
    }
}
