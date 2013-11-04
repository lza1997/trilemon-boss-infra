package com.trilemon.boss360.infrastructure.base.service.api;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.SellerCat;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import com.trilemon.boss360.infrastructure.base.client.BaseClient;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;
import com.trilemon.boss360.infrastructure.base.service.TaobaoApiService;
import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoEnhancedApiException;
import com.trilemon.boss360.infrastructure.base.service.api.exception.TaobaoSessionExpiredException;
import com.trilemon.boss360.infrastructure.base.util.TopApiUtils;
import com.trilemon.commons.Collections3;
import com.trilemon.commons.web.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author kevin
 */
public class TaobaoApiShopService {
    private static Logger logger = LoggerFactory.getLogger(TaobaoApiShopService.class);
    private TaobaoApiService taobaoApiService;
    private BaseClient baseClient;

    /**
     * 根据taobao user id获取宝贝分类
     *
     * @param userId 用户 淘宝 id
     * @return
     * @throws TaobaoEnhancedApiException
     */
    public List<SellerCat> getSellerCats(@NotNull Long userId) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        checkNotNull(userId, "userId must be not null.");

        String nick = baseClient.getNick(userId);
        checkNotNull(nick, "nick must be not null.");

        SellercatsListGetRequest request = new SellercatsListGetRequest();
        request.setNick(nick);
        SellercatsListGetResponse response;
        try {
            response = taobaoApiService.request(request);
        } catch (TaobaoSessionExpiredException e) {
            e.setNick(nick);
            throw e;
        }
        if (null == response) {
            throw new TaobaoEnhancedApiException("response is null", request);
        }
        if (response.isSuccess()) {
            List<SellerCat> sellerCats = response.getSellerCats();
            //获取未分类商品
            long unclassifiedItemNum = getOnSaleItemNum(userId, Lists.newArrayList(-1L));
            if (unclassifiedItemNum > 0) {
                sellerCats.add(TopApiUtils.getUnclassifiedSellerCat());
            }
            return sellerCats;
        } else {
            throw new TaobaoEnhancedApiException(nick, request, response);
        }
    }

    /**
     * 获取宝贝分类和对应的在售商品数量
     *
     * @param userId
     * @return 如果宝贝分类为空返回 null
     * @throws TaobaoEnhancedApiException
     */
    @NotNull
    public Map<SellerCat, Long> getSellerCatAndOnSaleItemNum(@NotNull Long userId, List<SellerCat> sellerCats) throws
            TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        checkNotNull(userId, "userId must be not null.");

        Map<SellerCat, Long> result = Maps.newTreeMap(new Comparator<SellerCat>() {
            @Override
            public int compare(SellerCat o1, SellerCat o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (SellerCat sellerCat : sellerCats) {
            long num = getOnSaleItemNum(userId, Lists.newArrayList(sellerCat.getCid()));
            result.put(sellerCat, num);
        }

        for (Map.Entry<SellerCat, Long> entry1 : result.entrySet()) {
            for (Map.Entry<SellerCat, Long> entry2 : result.entrySet()) {
                if (entry1.getKey().getCid().equals(entry2.getKey().getParentCid())) {
                    entry1.setValue(entry1.getValue() + entry2.getValue());
                }
            }
        }
        System.out.println(result);
        return result;
    }

    /**
     * 获取所有在售商品数量
     *
     * @param userId
     * @return 如果宝贝分类为空或者没有商品返回0
     * @throws TaobaoEnhancedApiException
     */
    public long getOnSaleItemNum(@NotNull Long userId) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        checkNotNull(userId, "userId must be not null.");

        ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
        request.setFields("num_iid");
        request.setPageNo(1L);
        request.setPageSize(1L);
        Pair<List<Item>, Long> result = getOnSaleItems(userId, request);
        Page<Item> page = Page.create(result.getRight().intValue(), request.getPageNo().intValue(),
                request.getPageSize().intValue(),
                result.getLeft());
        if (null != page) {
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
     * @throws TaobaoEnhancedApiException
     */
    public long getOnSaleItemNum(@NotNull Long userId, List<Long> sellerCats) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException {
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
                Pair<List<Item>, Long> result = getOnSaleItems(userId, request);
                Page<Item> page = Page.create(result.getRight().intValue(), request.getPageNo().intValue(),
                        request.getPageSize().intValue(),
                        result.getLeft());
                if (null != page) {
                    num += page.getTotalSize();
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
     * @throws TaobaoEnhancedApiException
     */
    public long getTradeNumFromTop(Long userId, String appKey, List<String> tradeTypes, Date startDate,
                                   Date endDate) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(appKey, "appKey must be not null.");
        checkNotNull(startDate, "startDate must be not null.");
        checkNotNull(endDate, "endDate must be not null.");
        checkArgument(endDate.getTime() <= endDate.getTime(), "startDate must be before endDate.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, appKey);
        TradesSoldGetRequest request = new TradesSoldGetRequest();
        request.setFields("tid");
        request.setType(Joiner.on(",").join(tradeTypes));
        request.setStartCreated(startDate);
        request.setEndCreated(endDate);
        request.setPageNo(1L);
        request.setPageSize(1L);
        request.setUseHasNext(false);

        TradesSoldGetResponse response;
        try {
            response = taobaoApiService.requestWithAppKey(request, appKey,
                    taobaoSession.getAccessToken());
        } catch (TaobaoSessionExpiredException e) {
            e.setUserId(userId);
            throw e;
        }
        if (null == response) {
            throw new TaobaoEnhancedApiException("response is null", request);
        }
        if (response.isSuccess()) {
            return response.getTotalResults();
        } else {
            throw new TaobaoEnhancedApiException(String.valueOf(userId), request, response);
        }
    }

    public long getTradeNumFromTop(Long userId, List<String> tradeTypes, Date startDate,
                                   Date endDate) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        return getTradeNumFromTop(userId, taobaoApiService.getAppKey(), tradeTypes, startDate, endDate);
    }

    /**
     * 翻页在售宝贝
     *
     * @param userId
     * @param query        null 搜索全部类目
     * @param fields
     * @param sellerCatIds null 为搜索全部类目
     * @param pageNum
     * @param pageSize
     * @param fuzzy        true 为首先对宝贝 id 进行搜索；然后搜索关键词
     * @return
     * @throws TaobaoEnhancedApiException
     */
    public Page<Item> paginateOnSaleItems(Long userId, String query, List<String> fields, List<Long> sellerCatIds,
                                          long pageNum, long pageSize, boolean fuzzy) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException {
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
        if (CollectionUtils.isNotEmpty(sellerCatIds)) {
            checkArgument(sellerCatIds.size() <= 32, "seller cid num should <= 32, http://api.taobao.com/apidoc/api" +
                    ".htm?spm=0.0.0.0.bANlsY&path=cid:4-apiId:18");
            request.setSellerCids(Joiner.on(",").join(sellerCatIds));
        }
        Pair<List<Item>, Long> result = getOnSaleItems(userId, request);
        return Page.create(result.getRight().intValue(), request.getPageNo().intValue(),
                request.getPageSize().intValue(),
                result.getLeft());
    }

    /**
     * 获取在售宝贝
     *
     * @param userId
     * @param request
     * @return {@link Pair}<{@link List}<{@link Item}>, {@link Long}>
     * @throws TaobaoEnhancedApiException
     */
    public Pair<List<Item>, Long> getOnSaleItems(Long userId, ItemsOnsaleGetRequest request) throws
            TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(request, "request must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        if (null == taobaoSession) {
            throw new TaobaoEnhancedApiException("no taobaoSession of userId[" + userId + "]");
        }
        ItemsOnsaleGetResponse response;
        try {
            response = taobaoApiService.requestWithAppKey(request,taobaoApiService.getAppKey(),
                    taobaoSession.getAccessToken());
        } catch (TaobaoSessionExpiredException e) {
            e.setUserId(userId);
            throw e;
        }
        if (null == response) {
            throw new TaobaoEnhancedApiException("response is null", request);
        }
        if (response.isSuccess()) {
            List<Item> items = response.getItems();
            items = (null == items ? Lists.<Item>newArrayList() : items);
            return Pair.of(items, response.getTotalResults());
        } else {
            throw new TaobaoEnhancedApiException(String.valueOf(userId), request, response);
        }
    }

    /**
     * 获取宝贝
     *
     * @param userId
     * @param numIid
     * @param fields
     * @return
     * @throws TaobaoEnhancedApiException
     */
    public Item getItem(Long userId, Long numIid, List<String> fields) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(numIid, "numIid must be not null.");
        checkNotNull(fields, "fields must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        if (null == taobaoSession) {
            throw new TaobaoEnhancedApiException("no taobaoSession of userId[" + userId + "]");
        }
        ItemGetRequest request = new ItemGetRequest();
        request.setFields(Joiner.on(",").join(fields));
        request.setNumIid(numIid);
        ItemGetResponse response;
        try {
            response = taobaoApiService.requestWithAppKey(request,taobaoApiService.getAppKey(),
                    taobaoSession.getAccessToken());
        } catch (TaobaoSessionExpiredException e) {
            e.setUserId(userId);
            throw e;
        }
        if (null == response) {
            throw new TaobaoEnhancedApiException("response is null", request);
        }
        if (response.isSuccess()) {
            return response.getItem();
        } else {
            String subCode = response.getSubCode();
            if (subCode.equals("isv.item-get-service-error:ITEM_NOT_FOUND") || subCode.equals("isv.item-is-delete:invalid-numIid")) {
                return null;
            } else {
                throw new TaobaoEnhancedApiException(String.valueOf(userId), request, response);
            }
        }
    }

    /**
     * 批量获取宝贝
     *
     * @param userId
     * @param numIids
     * @param fields
     * @return
     * @throws TaobaoEnhancedApiException
     */
    @NotNull
    public List<Item> getItems(Long userId, List<Long> numIids, List<String> fields) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(numIids, "numIids must be not null.");
        checkNotNull(fields, "fields must be not null.");

        List<Item> items = Lists.newArrayList();
        if (numIids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        if (null == taobaoSession) {
            throw new TaobaoEnhancedApiException("no taobaoSession of userId[" + userId + "]");
        }
        Iterable<List<Long>> numIidPartitions = Iterables.partition(numIids, 20);

        for (List<Long> partition : numIidPartitions) {
            ItemsListGetRequest request = new ItemsListGetRequest();
            request.setFields(Joiner.on(",").join(fields));
            request.setNumIids(Collections3.COMMA_JOINER.join(partition));
            ItemsListGetResponse response;
            try {
                response = taobaoApiService.requestWithAppKey(request,taobaoApiService.getAppKey(),
                        taobaoSession.getAccessToken());
            } catch (TaobaoSessionExpiredException e) {
                e.setUserId(userId);
                throw e;
            }
            if (null == response) {
                throw new TaobaoEnhancedApiException("response is null", request);
            }
            if (response.isSuccess()) {
                items.addAll(response.getItems());
            } else {
                throw new TaobaoEnhancedApiException(String.valueOf(userId), request, response);
            }
        }
        return items;
    }

    /**
     * 一个一个获取宝贝
     *
     * @param userId
     * @param numIids
     * @param fields
     * @return
     * @throws TaobaoEnhancedApiException
     */
    @NotNull
    public List<Item> getItemsOneByOne(Long userId, List<Long> numIids, List<String> fields) throws
            TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(numIids, "numIids must be not null.");
        checkNotNull(fields, "fields must be not null.");
        List<Item> items = Lists.newArrayList();
        if (numIids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        if (null == taobaoSession) {
            throw new TaobaoEnhancedApiException("no taobaoSession of userId[" + userId + "]");
        }
        for (Long numIid : numIids) {
            try {
                Item item = getItem(userId, numIid, fields);
                items.add(item);
            } catch (TaobaoEnhancedApiException ex) {
                //log but ignore
                logger.error("userId[" + userId + "], numIid[" + numIid + "]", ex);
            }
        }
        return items;
    }

    public Item removeFromShowcase(Long userId, Long numIid) throws TaobaoEnhancedApiException,
            TaobaoSessionExpiredException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(numIid, "numIid must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        if (null == taobaoSession) {
            throw new TaobaoEnhancedApiException("no taobaoSession of userId[" + userId + "]");
        }

        ItemRecommendDeleteRequest request = new ItemRecommendDeleteRequest();
        request.setNumIid(numIid);
        ItemRecommendDeleteResponse response;
        try {
            response = taobaoApiService.requestWithAppKey(request, taobaoApiService.getAppKey(),
                    taobaoSession.getAccessToken());
        } catch (TaobaoSessionExpiredException e) {
            e.setUserId(userId);
            throw e;
        }
        if (null == response) {
            throw new TaobaoEnhancedApiException("response is null", request);
        }
        if (response.isSuccess()) {
            return response.getItem();
        } else {
            throw new TaobaoEnhancedApiException(String.valueOf(userId), request, response);
        }
    }

    public Item showcase(Long userId, Long numIid) throws TaobaoEnhancedApiException,
            TaobaoSessionExpiredException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(numIid, "numIid must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        if (null == taobaoSession) {
            throw new TaobaoEnhancedApiException("no taobaoSession of userId[" + userId + "]");
        }

        ItemRecommendAddRequest request = new ItemRecommendAddRequest();
        request.setNumIid(numIid);
        ItemRecommendAddResponse response;
        try {
            response = taobaoApiService.requestWithAppKey(request,taobaoApiService.getAppKey(),
                    taobaoSession.getAccessToken());
        } catch (TaobaoSessionExpiredException e) {
            e.setUserId(userId);
            throw e;
        }
        if (null == response) {
            throw new TaobaoEnhancedApiException("response is null", request);
        }
        if (response.isSuccess()) {
            return response.getItem();
        } else {
            throw new TaobaoEnhancedApiException(String.valueOf(userId), request, response);
        }
    }

    /**
     * 获取橱窗的使用情况，[总数，已使用，未使用]
     *
     * @param userId
     * @return
     * @throws TaobaoEnhancedApiException
     * @throws TaobaoSessionExpiredException
     */
    public Long[] getShowcaseRemain(Long userId) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        if (null == taobaoSession) {
            throw new TaobaoEnhancedApiException("no taobaoSession of userId[" + userId + "]");
        }

        ShopRemainshowcaseGetRequest request = new ShopRemainshowcaseGetRequest();
        ShopRemainshowcaseGetResponse response;

        try {
            response = taobaoApiService.requestWithAppKey(request,taobaoApiService.getAppKey(),
                    taobaoSession.getAccessToken());
        } catch (TaobaoSessionExpiredException e) {
            e.setUserId(userId);
            throw e;
        }
        if (null == response) {
            throw new TaobaoEnhancedApiException("response is null", request);
        }
        if (response.isSuccess()) {
            long allCount = response.getShop().getAllCount();
            long remainCount = response.getShop().getRemainCount();
            long usedCount = response.getShop().getUsedCount();
            return new Long[]{allCount, usedCount, remainCount};
        } else {
            throw new TaobaoEnhancedApiException(String.valueOf(userId), request, response);
        }
    }

    public TaobaoApiService getTaobaoApiService() {
        return taobaoApiService;
    }

    public void setTaobaoApiService(TaobaoApiService taobaoApiService) {
        this.taobaoApiService = taobaoApiService;
    }

    public BaseClient getBaseClient() {
        return baseClient;
    }

    public void setBaseClient(BaseClient baseClient) {
        this.baseClient = baseClient;
    }

}
