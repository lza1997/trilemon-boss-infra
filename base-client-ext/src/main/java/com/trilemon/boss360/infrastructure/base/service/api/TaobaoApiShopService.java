package com.trilemon.boss360.infrastructure.base.service.api;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.SellerCat;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.request.SellercatsListGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobao.api.response.SellercatsListGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.trilemon.boss360.infrastructure.base.client.BaseClient;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;
import com.trilemon.boss360.infrastructure.base.service.TaobaoApiService;
import com.trilemon.commons.web.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author kevin
 */
@Service
public class TaobaoApiShopService {
    private static Logger logger = LoggerFactory.getLogger(TaobaoApiShopService.class);
    @Autowired
    private TaobaoApiService taobaoApiService;
    @Autowired
    private BaseClient baseClient;

    /**
     * 根据用户昵称获取卖家的宝贝分类
     *
     * @param nick 用户昵称
     * @return {@link List<SellerCat>}
     * @throws EnhancedApiException
     */
    public List<SellerCat> getSellerCats(@NotNull String nick) throws EnhancedApiException {
        checkNotNull(nick, "nick must be not null.");

        SellercatsListGetRequest request = new SellercatsListGetRequest();
        request.setNick(nick);
        SellercatsListGetResponse response = taobaoApiService.request(request, taobaoApiService.getAppKey());
        if (response.isSuccess()) {
            return response.getSellerCats();
        } else {
            throw new EnhancedApiException(request, response);
        }
    }

    /**
     * 根据taobao user id获取宝贝分类
     *
     * @param userId 用户 淘宝 id
     * @return
     * @throws EnhancedApiException
     */
    public List<SellerCat> getSellerCats(@NotNull Long userId) throws EnhancedApiException {
        checkNotNull(userId, "userId must be not null.");

        String nick = baseClient.getNick(userId);
        return getSellerCats(nick);
    }

    /**
     * 获取宝贝分类和对应的在售商品数量
     *
     * @param userId
     * @return 如果宝贝分类为空返回 null
     * @throws EnhancedApiException
     */
    public Map<SellerCat, Long> getSellerCatAndOnSaleItemNum(@NotNull Long userId) throws EnhancedApiException {
        checkNotNull(userId, "userId must be not null.");

        List<SellerCat> sellerCats = getSellerCats(userId);
        Map<SellerCat, Long> result = Maps.newHashMap();
        for (SellerCat sellerCat : sellerCats) {
            long num = getOnSaleItemNum(userId, Lists.newArrayList(sellerCat.getCid()));
            result.put(sellerCat, num);
        }
        return result;
    }

    /**
     * 获取所有在售商品数量
     *
     * @param userId
     * @return 如果宝贝分类为空或者没有商品返回0
     * @throws EnhancedApiException
     */
    public long getOnSaleItemNum(@NotNull Long userId) throws EnhancedApiException {
        checkNotNull(userId, "userId must be not null.");

        ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
        request.setFields("num_iid");
        request.setPageNo(1L);
        request.setPageSize(1L);
        Page<Item> page = getOnSaleItems(userId, request);
        if (null != page && CollectionUtils.isNotEmpty(page.getItems())) {
            return page.getTotalSize();
        } else {
            return 0;
        }
    }

    /**
     * 获取指定宝贝分类的在售商品数量
     *
     * @param userId
     * @param sellerCats
     * @return sellerCats为 null ，则返回所有的在线销售商品
     * @throws EnhancedApiException
     */
    public long getOnSaleItemNum(@NotNull Long userId, List<Long> sellerCats) throws EnhancedApiException {
        checkNotNull(userId, "userId must be not null.");

        if (null == sellerCats) {
            return getOnSaleItemNum(userId);
        } else {
            long num = 0L;
            //淘宝 api 目前只支持一次性传入32个宝贝分类（参见http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.bANlsY&path=cid:4-apiId:18）。
            Iterable<List<Long>> sellerCatsPartitions = Iterables.partition(sellerCats, 32);
            for (List<Long> sellerCatsPartition : sellerCatsPartitions) {
                ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
                request.setFields("num_iid");
                request.setSellerCids(Joiner.on(",").join(sellerCatsPartition));
                request.setPageNo(1L);
                request.setPageSize(1L);
                Page<Item> page = getOnSaleItems(userId, request);
                if (null != page && CollectionUtils.isNotEmpty(page.getItems())) {
                    num += page.getItems().size();
                }
            }
            return num;
        }
    }

    /**
     * 从开放平台获取订单数量
     *
     * @param userId
     * @param appKey
     * @param tradeTypes
     * @param startDate
     * @param endDate
     * @return
     * @throws EnhancedApiException
     */
    public long getTradeNumFromTop(Long userId, String appKey, List<String> tradeTypes, Date startDate,
                                   Date endDate) throws EnhancedApiException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(appKey, "appKey must be not null.");
        checkNotNull(startDate, "startDate must be not null.");
        checkNotNull(endDate, "endDate must be not null.");
        checkArgument(endDate.getTime() <= endDate.getTime(), "startDate must be before endDate.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        TradesSoldGetRequest request = new TradesSoldGetRequest();
        request.setFields("tid");
        request.setType(Joiner.on(",").join(tradeTypes));
        request.setStartCreated(startDate);
        request.setEndCreated(endDate);
        request.setPageNo(1L);
        request.setPageSize(1L);
        request.setUseHasNext(false);

        TradesSoldGetResponse response = taobaoApiService.request(request, appKey,
                taobaoSession.getSessionKey());
        if (response.isSuccess()) {
            return response.getTotalResults();
        } else {
            throw new EnhancedApiException(request, response);
        }
    }

    public long getTradeNumFromTop(Long userId, List<String> tradeTypes, Date startDate,
                                   Date endDate) throws EnhancedApiException {
        return getTradeNumFromTop(userId, taobaoApiService.getAppKey(), tradeTypes, startDate, endDate);
    }

    /**
     * 翻页在售宝贝
     *
     * @param userId
     * @param query      null 搜索全部类目
     * @param fields
     * @param sellerCats null 为搜索全部类目
     * @param pageNum
     * @param pageSize
     * @param fuzzy      true 为首先对宝贝 id 进行搜索；然后搜索关键词
     * @return
     * @throws EnhancedApiException
     */
    public Page<Item> paginateOnSaleItems(Long userId, String query, List<String> fields, List<Long> sellerCats,
                                          long pageNum, long pageSize, boolean fuzzy) throws EnhancedApiException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(fields, "fields must be not null.");

        if (fuzzy && NumberUtils.isNumber(query)) {
            Item item = getItem(56912708L, Long.valueOf(query), fields);
            if (null != item) {
                return Page.create(1, 1, 1, ImmutableList.of(item));
            }
        }

        ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
        request.setFields(Joiner.on(",").join(fields));
        request.setQ(query);
        request.setPageNo(pageNum);
        request.setPageSize(pageSize);
        if (CollectionUtils.isNotEmpty(sellerCats)) {
            checkArgument(sellerCats.size() <= 32, "seller cid num should <= 32, http://api.taobao.com/apidoc/api" +
                    ".htm?spm=0.0.0.0.bANlsY&path=cid:4-apiId:18");
            request.setSellerCids(Joiner.on(",").join(sellerCats));
        }
        return getOnSaleItems(userId, request);
    }

    /**
     * 获取在售宝贝
     *
     * @param userId
     * @param request
     * @return
     * @throws EnhancedApiException
     */
    public Page<Item> getOnSaleItems(Long userId, ItemsOnsaleGetRequest request) throws
            EnhancedApiException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(request, "request must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        if (null == taobaoSession) {
            throw new EnhancedApiException("no taobaoSession of userId[" + userId + "]");
        }
        ItemsOnsaleGetResponse response = taobaoApiService.request(request, taobaoApiService.getAppKey(),
                taobaoSession.getSessionKey());
        if (response.isSuccess()) {
            List<Item> items = response.getItems();
            items = (null == items ? Lists.<Item>newArrayList() : items);
            return Page.create(response.getTotalResults().intValue(), request.getPageNo().intValue(),
                    request.getPageSize().intValue(),
                    items);
        } else {
            throw new EnhancedApiException(request, response);
        }
    }

    /**
     * 获取宝贝
     *
     * @param userId
     * @param numIid
     * @param fields
     * @return
     * @throws EnhancedApiException
     */
    public Item getItem(Long userId, Long numIid, List<String> fields) throws EnhancedApiException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(numIid, "numIid must be not null.");
        checkNotNull(fields, "fields must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        if (null == taobaoSession) {
            throw new EnhancedApiException("no taobaoSession of userId[" + userId + "]");
        }
        ItemGetRequest request = new ItemGetRequest();
        request.setFields(Joiner.on(",").join(fields));
        request.setNumIid(numIid);
        ItemGetResponse response = taobaoApiService.request(request, taobaoSession.getSessionKey());
        if (response.isSuccess()) {
            return response.getItem();
        } else {
            String subCode=response.getSubCode();
            if (subCode.equals("isv.item-get-service-error:ITEM_NOT_FOUND")||subCode.equals("isv.item-is-delete:invalid-numIid")) {
                return null;
            } else {
                throw new EnhancedApiException(request, response);
            }
        }
    }
}
