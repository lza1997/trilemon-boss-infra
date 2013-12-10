package com.trilemon.boss.infra.base.service.api;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.taobao.api.domain.*;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import com.trilemon.boss.infra.base.client.BaseClient;
import com.trilemon.boss.infra.base.model.TaobaoSession;
import com.trilemon.boss.infra.base.model.dto.SellerCatExtended;
import com.trilemon.boss.infra.base.model.dto.ShowcaseNum;
import com.trilemon.boss.infra.base.service.TaobaoApiService;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoAccessControlException;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoEnhancedApiException;
import com.trilemon.boss.infra.base.service.api.exception.TaobaoSessionExpiredException;
import com.trilemon.boss.infra.base.util.TopApiUtils;
import com.trilemon.commons.web.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.trilemon.commons.Collections3.COMMA_JOINER;

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
    public List<SellerCat> getSellerCats(@NotNull Long userId) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");

        String nick = baseClient.getNick(userId);
        checkNotNull(nick, "nick must be not null.");

        SellercatsListGetRequest request = new SellercatsListGetRequest();
        request.setNick(nick);
        SellercatsListGetResponse response = taobaoApiService.request(request);
        List<SellerCat> sellerCats = response.getSellerCats();
        //获取未分类商品
        long unclassifiedItemNum = getOnSaleItemNum(userId, Lists.newArrayList(-1L));
        if (unclassifiedItemNum > 0) {
            sellerCats.add(TopApiUtils.getUnclassifiedSellerCat());
        }
        return sellerCats;
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
            TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
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
        return result;
    }

    /**
     * 获取宝贝分类和对应的库存商品数量
     *
     * @param userId
     * @return 如果宝贝分类为空返回 null
     * @throws TaobaoEnhancedApiException
     */
    @NotNull
    public Map<SellerCat, Long> getSellerCatAndInventoryItemNum(@NotNull Long userId,
                                                                List<SellerCat> sellerCats,
                                                                String banner) throws
            TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(sellerCats, "sellerCats must be not null.");

        Map<SellerCat, Long> result = Maps.newTreeMap(new Comparator<SellerCat>() {
            @Override
            public int compare(SellerCat o1, SellerCat o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (SellerCat sellerCat : sellerCats) {
            long num = getInventoryItemNum(userId, Lists.newArrayList(sellerCat.getCid()), banner);
            result.put(sellerCat, num);
        }

        for (Map.Entry<SellerCat, Long> entry1 : result.entrySet()) {
            for (Map.Entry<SellerCat, Long> entry2 : result.entrySet()) {
                if (entry1.getKey().getCid().equals(entry2.getKey().getParentCid())) {
                    entry1.setValue(entry1.getValue() + entry2.getValue());
                }
            }
        }
        return result;
    }

    /**
     * 获取库存商品数量，如果没有库存返回0
     *
     * @param userId
     * @param banner
     * @return
     * @throws TaobaoAccessControlException
     * @throws TaobaoEnhancedApiException
     * @throws TaobaoSessionExpiredException
     */
    @NotNull
    public long getInventoryItemNum(Long userId, String banner) throws TaobaoAccessControlException,
            TaobaoEnhancedApiException,
            TaobaoSessionExpiredException {
        checkNotNull(userId, "userId must be not null.");

        ItemsInventoryGetRequest request = new ItemsInventoryGetRequest();
        request.setFields("num_iid");
        request.setPageNo(1L);
        request.setPageSize(1L);
        request.setBanner(banner);
        ItemsInventoryGetResponse result = getInventoryItems(userId, request);
        Page<Item> page = Page.create(result.getTotalResults().intValue(), request.getPageNo().intValue(),
                request.getPageSize().intValue(),
                result.getItems());
        if (null != page) {
            return page.getTotalSize();
        } else {
            return 0;
        }
    }

    /**
     * 获取全部分类的库存量
     *
     * @param userId
     * @param banners
     * @return
     * @throws TaobaoAccessControlException
     * @throws TaobaoEnhancedApiException
     * @throws TaobaoSessionExpiredException
     */
    public Map<String, Long> getInventoryItemNum(Long userId, List<String> banners) throws TaobaoAccessControlException,
            TaobaoEnhancedApiException,
            TaobaoSessionExpiredException {
        checkNotNull(userId, "userId must be not null.");

        Map<String, Long> inventoryNumMap = Maps.newHashMap();
        for (String banner : banners) {
            inventoryNumMap.put(banner, getInventoryItemNum(userId, banner));
        }
        return inventoryNumMap;
    }

    /**
     * 获取指定分类的商品数量
     *
     * @param userId
     * @param sellerCats null查询所有分类
     * @param banner
     * @return
     * @throws TaobaoEnhancedApiException
     * @throws TaobaoSessionExpiredException
     * @throws TaobaoAccessControlException
     */
    @NotNull
    public long getInventoryItemNum(@NotNull Long userId, List<Long> sellerCats, String banner)
            throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must not be null.");

        if (null == sellerCats) {
            return getInventoryItemNum(userId, banner);
        } else {
            long num = 0L;
            //淘宝 api 目前只支持一次性传入32个宝贝分类（参见http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.bANlsY&path=cid:4-apiId:18）。
            Iterable<List<Long>> sellerCatsPartitions = Iterables.partition(sellerCats, 32);
            for (List<Long> sellerCatsPartition : sellerCatsPartitions) {
                ItemsInventoryGetRequest request = new ItemsInventoryGetRequest();
                request.setFields("num_iid");
                request.setSellerCids(Joiner.on(",").join(sellerCatsPartition));
                request.setBanner(banner);
                request.setPageNo(1L);
                request.setPageSize(1L);
                ItemsInventoryGetResponse result = getInventoryItems(userId, request);
                Page<Item> page = Page.create(result.getTotalResults().intValue(), request.getPageNo().intValue(),
                        request.getPageSize().intValue(),
                        result.getItems());
                if (null != page) {
                    num += page.getTotalSize();
                }
            }
            return num;
        }
    }

    /**
     * 获取所有在售商品数量
     *
     * @param userId
     * @return 如果宝贝分类为空或者没有商品返回0
     * @throws TaobaoEnhancedApiException
     */
    @NotNull
    public long getOnSaleItemNum(@NotNull Long userId) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");

        ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
        request.setFields("num_iid");
        request.setPageNo(1L);
        request.setPageSize(1L);
        ItemsOnsaleGetResponse result = getOnSaleItems(userId, request);
        Page<Item> page = Page.create(result.getTotalResults().intValue(), request.getPageNo().intValue(),
                request.getPageSize().intValue(),
                result.getItems());
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
    @NotNull
    public long getOnSaleItemNum(@NotNull Long userId, List<Long> sellerCats) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
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
                ItemsOnsaleGetResponse result = getOnSaleItems(userId, request);
                Page<Item> page = Page.create(result.getTotalResults().intValue(), request.getPageNo().intValue(),
                        request.getPageSize().intValue(),
                        result.getItems());
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
                                   Date endDate) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
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

        TradesSoldGetResponse response = taobaoApiService.requestWithAppKey(request, appKey,
                taobaoSession.getAccessToken());
        return response.getTotalResults();
    }

    /**
     * 获取订单数量
     *
     * @param userId
     * @param tradeTypes
     * @param startDate
     * @param endDate
     * @return
     * @throws TaobaoEnhancedApiException
     * @throws TaobaoSessionExpiredException
     * @throws TaobaoAccessControlException
     */
    public long getTradeNumFromTop(Long userId, List<String> tradeTypes, Date startDate,
                                   Date endDate) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
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
    public Page<Item> paginateOnSaleItems(Long userId, String query,
                                          List<String> fields, List<Long> sellerCatIds,
                                          long pageNum, long pageSize,
                                          boolean fuzzy, Boolean hasShowcase,
                                          String orderBy) throws TaobaoEnhancedApiException,
            TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(fields, "fields must be not null.");

        if (fuzzy && NumberUtils.isNumber(query)) {
            Item item = getItem(userId, Long.valueOf(query), fields);
            if (null != item) {
                return Page.create(1, 1, 1, ImmutableList.of(item));
            }
        }

        ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
        request.setFields(COMMA_JOINER.join(fields));
        request.setQ(query);
        request.setPageNo(pageNum);
        request.setPageSize(pageSize);
        request.setOrderBy(orderBy);
        if (null != hasShowcase) {
            request.setHasShowcase(hasShowcase);
        }
        if (CollectionUtils.isNotEmpty(sellerCatIds)) {
            checkArgument(sellerCatIds.size() <= 32, "seller cid num should <= 32, http://api.taobao.com/apidoc/api" +
                    ".htm?spm=0.0.0.0.bANlsY&path=cid:4-apiId:18");
            request.setSellerCids(Joiner.on(",").join(sellerCatIds));
        }
        ItemsOnsaleGetResponse result = getOnSaleItems(userId, request);
        return Page.create(result.getTotalResults().intValue(), request.getPageNo().intValue(),
                request.getPageSize().intValue(),
                result.getItems());
    }

    /**
     * 获取在售宝贝
     *
     * @param userId
     * @param request
     * @return ItemsOnsaleGetResponse
     * @throws TaobaoEnhancedApiException
     */
    public ItemsOnsaleGetResponse getOnSaleItems(Long userId, ItemsOnsaleGetRequest request) throws
            TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(request, "request must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s] appKey[{}]", userId,
                taobaoApiService.getAppKey());

        return taobaoApiService.requestWithAppKey(request, taobaoApiService.getAppKey(),
                taobaoSession.getAccessToken());
    }

    /**
     * 翻页库存宝贝
     *
     * @param userId
     * @param query        null 搜索全部类目
     * @param fields
     * @param banners      null 为搜索全部类型
     * @param sellerCatIds null 为搜索全部类目
     * @param pageNum
     * @param pageSize
     * @param fuzzy        true 为首先对宝贝 id 进行搜索；然后搜索关键词
     * @return
     * @throws TaobaoEnhancedApiException
     */
    public Page<Item> paginateInventoryItems(Long userId, String query,
                                             List<String> fields,
                                             List<String> banners,
                                             List<Long> sellerCatIds,
                                             long pageNum,
                                             long pageSize,
                                             boolean fuzzy,
                                             String orderBy) throws TaobaoEnhancedApiException,
            TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(fields, "fields must be not null.");

        if (fuzzy && NumberUtils.isNumber(query)) {
            Item item = getItem(userId, Long.valueOf(query), fields);
            if (null != item) {
                return Page.create(1, 1, 1, ImmutableList.of(item));
            }
        }

        ItemsInventoryGetRequest request = new ItemsInventoryGetRequest();
        request.setFields(COMMA_JOINER.join(fields));
        request.setQ(query);
        request.setBanner(COMMA_JOINER.join(banners));
        request.setPageNo(pageNum);
        request.setPageSize(pageSize);
        request.setOrderBy(orderBy);
        if (CollectionUtils.isNotEmpty(sellerCatIds)) {
            checkArgument(sellerCatIds.size() <= 32, "seller cid num should <= 32, http://api.taobao.com/apidoc/api" +
                    ".htm?spm=0.0.0.0.bANlsY&path=cid:4-apiId:18");
            request.setSellerCids(COMMA_JOINER.join(sellerCatIds));
        }
        ItemsInventoryGetResponse result = getInventoryItems(userId, request);
        return Page.create(result.getTotalResults().intValue(), request.getPageNo().intValue(),
                request.getPageSize().intValue(),
                result.getItems());
    }

    /**
     * 获取库存宝贝
     *
     * @param userId
     * @param request
     * @return ItemsOnsaleGetResponse
     * @throws TaobaoEnhancedApiException
     */
    public ItemsInventoryGetResponse getInventoryItems(Long userId, ItemsInventoryGetRequest request) throws
            TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(request, "request must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s]", userId);

        return taobaoApiService.requestWithAppKey(request, taobaoApiService.getAppKey(),
                taobaoSession.getAccessToken());
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
    public Item getItem(Long userId, Long numIid, List<String> fields) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(numIid, "numIid must be not null.");
        checkNotNull(fields, "fields must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s]", userId);

        ItemGetRequest request = new ItemGetRequest();
        request.setFields(Joiner.on(",").join(fields));
        request.setNumIid(numIid);
        try {
            ItemGetResponse response = taobaoApiService.requestWithAppKey(request, taobaoApiService.getAppKey(),
                    taobaoSession.getAccessToken());
            return response.getItem();
        } catch (TaobaoEnhancedApiException e) {
            String subCode = e.getTaobaoResponse().getSubCode();
            if (null != subCode)
                if (subCode.equals("isv.item-get-service-error:ITEM_NOT_FOUND") || subCode.equals("isv.item-is-delete:invalid-numIid")) {
                    return null;
                }
            throw e;
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
    public List<Item> getItems(Long userId, List<Long> numIids, List<String> fields) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(numIids, "numIids must be not null.");
        checkNotNull(fields, "fields must be not null.");

        List<Item> items = Lists.newArrayList();
        if (numIids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s]", userId);

        Iterable<List<Long>> numIidPartitions = Iterables.partition(numIids, 20);

        for (List<Long> partition : numIidPartitions) {
            ItemsListGetRequest request = new ItemsListGetRequest();
            request.setFields(Joiner.on(",").join(fields));
            request.setNumIids(COMMA_JOINER.join(partition));
            ItemsListGetResponse response = taobaoApiService.requestWithAppKey(request, taobaoApiService.getAppKey(),
                    taobaoSession.getAccessToken());
            items.addAll(response.getItems());
        }
        return items;
    }

    /**
     * 一个一个获取宝贝，略过异常
     *
     * @param userId
     * @param numIids
     * @param fields
     * @return
     * @throws TaobaoEnhancedApiException
     */
    @NotNull
    public List<Item> getItemsOneByOne(Long userId, List<Long> numIids, List<String> fields) throws
            TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(numIids, "numIids must be not null.");
        checkNotNull(fields, "fields must be not null.");
        List<Item> items = Lists.newArrayList();
        if (numIids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s]", userId);

        for (Long numIid : numIids) {
            try {
                Item item = getItem(userId, numIid, fields);
                items.add(item);
            } catch (TaobaoEnhancedApiException ex) {
                //log but ignore and catch other exception
                logger.error("userId[" + userId + "], numIid[" + numIid + "]", ex);
            }
        }
        return items;
    }

    /**
     * 取消橱窗
     *
     * @param userId
     * @param numIid
     * @return
     * @throws TaobaoEnhancedApiException
     * @throws TaobaoSessionExpiredException
     * @throws TaobaoAccessControlException
     */
    public Item removeFromShowcase(Long userId, Long numIid) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(numIid, "numIid must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s]", userId);

        ItemRecommendDeleteRequest request = new ItemRecommendDeleteRequest();
        request.setNumIid(numIid);
        ItemRecommendDeleteResponse response = taobaoApiService.requestWithAppKey(request, taobaoApiService.getAppKey(),
                taobaoSession.getAccessToken());
        return response.getItem();
    }

    /**
     * 上橱窗
     *
     * @param userId
     * @param numIid
     * @return
     * @throws TaobaoEnhancedApiException
     * @throws TaobaoSessionExpiredException
     * @throws TaobaoAccessControlException
     */
    public Item showcase(Long userId, Long numIid) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(numIid, "numIid must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s]", userId);

        ItemRecommendAddRequest request = new ItemRecommendAddRequest();
        request.setNumIid(numIid);
        ItemRecommendAddResponse response = taobaoApiService.requestWithAppKey(request, taobaoApiService.getAppKey(),
                taobaoSession.getAccessToken());
        return response.getItem();
    }

    /**
     * 获取橱窗的使用情况
     *
     * @param userId
     * @return ShowcaseNum
     * @throws TaobaoEnhancedApiException
     * @throws TaobaoSessionExpiredException
     */
    public ShowcaseNum getShowcaseStatus(Long userId) throws TaobaoEnhancedApiException, TaobaoSessionExpiredException,
            TaobaoAccessControlException {
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s]", userId);

        ShopRemainshowcaseGetRequest request = new ShopRemainshowcaseGetRequest();
        ShopRemainshowcaseGetResponse response = taobaoApiService.requestWithAppKey(request, taobaoApiService.getAppKey(),
                taobaoSession.getAccessToken());
        long allCount = response.getShop().getAllCount();
        long remainCount = response.getShop().getRemainCount();
        long usedCount = response.getShop().getUsedCount();
        return new ShowcaseNum(allCount, usedCount, remainCount);
    }

    /**
     * 根据传入的分类 id 扩展分类（设置商品数量和是否被使用）
     *
     * @param userId
     * @param usedSellCats 被使用的分类
     * @return
     * @throws TaobaoAccessControlException
     * @throws TaobaoEnhancedApiException
     * @throws TaobaoSessionExpiredException
     */
    public List<SellerCatExtended> getOnsaleSellerCatExtended(Long userId,
                                                              List<Long> usedSellCats) throws
            TaobaoAccessControlException,
            TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        List<SellerCat> sellerCats = getSellerCats(userId);
        Map<SellerCat, Long> sellerCatAndItemNum = getSellerCatAndOnSaleItemNum(userId, sellerCats);
        return getSellerCatExtended(sellerCatAndItemNum, usedSellCats);
    }

    /**
     * 根据传入的分类 id 扩展分类（设置商品数量和是否被使用）
     *
     * @param userId
     * @param sellerCatIds 被使用的分类
     * @return
     * @throws TaobaoAccessControlException
     * @throws TaobaoEnhancedApiException
     * @throws TaobaoSessionExpiredException
     */
    public List<SellerCatExtended> getInventorySellerCatExtended(Long userId,
                                                                 List<Long> sellerCatIds, String banner) throws
            TaobaoAccessControlException,
            TaobaoEnhancedApiException,
            TaobaoSessionExpiredException {
        List<SellerCat> sellerCats = getSellerCats(userId);
        Map<SellerCat, Long> sellerCatAndItemNum = getSellerCatAndInventoryItemNum(userId, sellerCats, banner);
        return getSellerCatExtended(sellerCatAndItemNum, sellerCatIds);
    }

    /**
     * 得到扩展分类
     *
     * @param sellerCatAndItemNum
     * @param usedSellCatIds
     * @return
     * @throws TaobaoAccessControlException
     * @throws TaobaoEnhancedApiException
     * @throws TaobaoSessionExpiredException
     */
    public List<SellerCatExtended> getSellerCatExtended(Map<SellerCat, Long> sellerCatAndItemNum,
                                                        List<Long> usedSellCatIds) throws
            TaobaoAccessControlException,
            TaobaoEnhancedApiException,
            TaobaoSessionExpiredException {
        List<SellerCatExtended> list = Lists.newArrayList();
        for (SellerCat sellerCat : sellerCatAndItemNum.keySet()) {
            SellerCatExtended sellerCatExtended = new SellerCatExtended();
            sellerCatExtended.setSellerCat(sellerCat);
            sellerCatExtended.setItemNum(sellerCatAndItemNum.get(sellerCat));
            //设置是否分类已经被占用了
            if (null != usedSellCatIds && usedSellCatIds.contains(sellerCat.getCid())) {
                sellerCatExtended.setUsed(true);
            }
            list.add(sellerCatExtended);
        }
        return list;
    }

    /**
     * 获取仓库商品数量
     *
     * @param userId
     * @param banners
     * @param sellerCatIds
     * @return
     * @throws TaobaoSessionExpiredException
     * @throws TaobaoEnhancedApiException
     * @throws TaobaoAccessControlException
     */
    public Map<String, Long> getInventoryItemNum(Long userId, List<Long> sellerCatIds, List<String> banners)
            throws TaobaoSessionExpiredException, TaobaoEnhancedApiException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(userId, "inventoryTypes must be not null, userId[%s]", userId);

        Map<String, Long> inventoryNumMap = Maps.newHashMap();
        for (String banner : banners) {
            inventoryNumMap.put(banner, getInventoryItemNum(userId, sellerCatIds, banner));
        }
        return inventoryNumMap;
    }

    /**
     * 上架操作
     *
     * @param userId
     * @param numIid
     * @throws TaobaoAccessControlException
     * @throws TaobaoEnhancedApiException
     * @throws TaobaoSessionExpiredException
     */
    public void listItem(Long userId, Long numIid)
            throws TaobaoAccessControlException, TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s]", userId);

        ItemUpdateListingRequest listingRequest = new ItemUpdateListingRequest();
        listingRequest.setNumIid(numIid);
        Item item = getItem(userId, numIid, ImmutableList.of("num"));
        listingRequest.setNum(item.getNum());
        taobaoApiService.request(listingRequest, taobaoSession.getAccessToken());
    }

    /**
     * 下架操作
     *
     * @param userId
     * @param numIid
     * @throws TaobaoAccessControlException
     * @throws TaobaoEnhancedApiException
     * @throws TaobaoSessionExpiredException
     */
    public void delistItem(Long userId, Long numIid) throws TaobaoAccessControlException, TaobaoEnhancedApiException, TaobaoSessionExpiredException {
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s]", userId);

        ItemUpdateDelistingRequest request = new ItemUpdateDelistingRequest();
        request.setNumIid(numIid);
        //下架
        taobaoApiService.request(request, taobaoSession.getAccessToken());
    }

    /**
     * 获取评论
     *
     * @param userId
     * @param request
     * @return ItemsOnsaleGetResponse
     * @throws TaobaoEnhancedApiException
     */
    public TraderatesGetResponse getRates(Long userId, TraderatesGetRequest request) throws
            TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(request, "request must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s]", userId);

        return taobaoApiService.requestWithAppKey(request, taobaoApiService.getAppKey(),
                taobaoSession.getAccessToken());
    }

    /**
     * 获取卖家某个trade 或者 order 的评论
     *
     * @param userId
     * @param tidOrOid
     * @return ItemsOnsaleGetResponse
     * @throws TaobaoEnhancedApiException
     */
    public List<TradeRate> getBuyerRates(Long userId, Long tidOrOid, List<String> fields) throws
            TaobaoEnhancedApiException, TaobaoSessionExpiredException, TaobaoAccessControlException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(tidOrOid, "tidOrOid must be not null.");

        TraderatesGetRequest request = new TraderatesGetRequest();
        request.setFields(COMMA_JOINER.join(fields));
        request.setRole("buyer");
        request.setTid(tidOrOid);
        request.setUseHasNext(true);
        request.setRateType("get");
        TraderatesGetResponse response = getRates(userId, request);
        return response.getTradeRates();
    }

    public User getTaobaoUser(Long userId, List<String> fields) throws TaobaoEnhancedApiException, TaobaoAccessControlException, TaobaoSessionExpiredException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(fields, "fields must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s]", userId);

        UserSellerGetRequest request = new UserSellerGetRequest();
        request.setFields(COMMA_JOINER.join(fields));
        UserSellerGetResponse response = taobaoApiService.requestWithAppKey(request, taobaoApiService.getAppKey(),
                taobaoSession.getAccessToken());
        return response.getUser();
    }

    public Shop getTaobaoShop(Long userId, String nick, List<String> fields) throws TaobaoEnhancedApiException,
            TaobaoAccessControlException, TaobaoSessionExpiredException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(nick, "nick must be not null.");
        checkNotNull(fields, "fields must be not null.");

        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        checkNotNull(taobaoSession, "taobaoSession must be not null, userId[%s]", userId);

        ShopGetRequest request = new ShopGetRequest();
        request.setFields(COMMA_JOINER.join(fields));
        request.setNick(nick);
        ShopGetResponse response = taobaoApiService.requestWithAppKey(request, taobaoApiService.getAppKey(),
                taobaoSession.getAccessToken());
        return response.getShop();
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