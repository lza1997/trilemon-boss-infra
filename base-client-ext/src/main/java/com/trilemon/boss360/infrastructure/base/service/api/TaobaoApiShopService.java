package com.trilemon.boss360.infrastructure.base.service.api;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.SellerCat;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.request.SellercatsListGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.SellercatsListGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.trilemon.boss360.infrastructure.base.client.BaseClient;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;
import com.trilemon.boss360.infrastructure.base.service.TaobaoApiService;
import com.trilemon.commons.web.Page;
import org.apache.commons.collections.CollectionUtils;
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
    private TaobaoApiItemService taobaoApiItemService;
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
        Page<Item> page = taobaoApiItemService.getOnSaleItems(userId, request);
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
                Page<Item> page = taobaoApiItemService.getOnSaleItems(userId, request);
                if (null != page && CollectionUtils.isNotEmpty(page.getItems())) {
                    num += page.getItems().size();
                }
            }
            return num;
        }
    }

    /**
     * 获取指定宝贝分类的在售商品列表
     *
     * @param userId
     * @param fields
     * @return
     * @throws EnhancedApiException
     */
    public List<Item> getOnSaleItems(@NotNull Long userId, @NotNull List<String> fields) throws EnhancedApiException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(fields, "fields must be not null.");

        List<Item> totalItems = Lists.newArrayList();
        long pageSize = 100;
        long pageNum = 1;
        while (true) {
            ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
            request.setFields(Joiner.on(",").join(fields));
            request.setPageSize(pageSize);
            request.setPageNo(pageNum);
            Page<Item> page = taobaoApiItemService.getOnSaleItems(userId, request);
            if (null != page && CollectionUtils.isNotEmpty(page.getItems())) {
                totalItems.addAll(page.getItems());
                pageNum++;
            } else {
                break;
            }
        }
        return totalItems;
    }

    /**
     * 获取指定宝贝分类的在售商品列表
     *
     * @param userId
     * @param sellerCats sellerCats为 null ，则返回所有的在线销售商品
     * @return
     * @throws EnhancedApiException
     */
    public List<Item> getOnSaleItems(Long userId, List<String> fields, List<Long> sellerCats) throws EnhancedApiException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(fields, "fields must be not null.");

        if (null == sellerCats) {
            return getOnSaleItems(userId, fields);
        } else {
            List<Item> totalItems = Lists.newArrayList();
            long pageSize = 100;
            //淘宝 api 目前只支持一次性传入32个宝贝分类（参见http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.bANlsY&path=cid:4-apiId:18）。
            Iterable<List<Long>> subCidIterable = Iterables.partition(sellerCats, 32);
            for (List<Long> cidList : subCidIterable) {
                long pageNum = 1;
                while (true) {

                    ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
                    request.setFields(Joiner.on(",").join(fields));
                    request.setPageSize(pageSize);
                    request.setPageNo(pageNum);
                    request.setSellerCids(Joiner.on(",").join(cidList));
                    Page<Item> page = taobaoApiItemService.getOnSaleItems(userId, request);
                    if (null != page && CollectionUtils.isNotEmpty(page.getItems())) {
                        totalItems.addAll(page.getItems());
                        pageNum++;
                    } else {
                        break;
                    }
                }
            }
            return totalItems;
        }
    }

    /**
     * 获取在售商品列表,带分页。
     *
     * @param userId
     * @return
     * @throws EnhancedApiException
     */
    public Page<Item> getOnSaleItems(Long userId, List<String> fields, int pageNum, int pageSize) throws EnhancedApiException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(fields, "fields must be not null.");

        ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
        request.setFields(Joiner.on(",").join(fields));
        request.setPageNo(Long.valueOf(pageNum));
        request.setPageSize(Long.valueOf(pageSize));
        return taobaoApiItemService.getOnSaleItems(userId, request);
    }

    /**
     * 获取指定宝贝类目的在售商品列表,带分页。
     *
     * @param userId
     * @param sellerCats 一次传入的宝贝分类不能大于32个;为 null ，则返回所有的在线销售商品
     * @return
     * @throws EnhancedApiException
     */
    public Page<Item> getOnSaleItems(Long userId, List<String> fields, List<Long> sellerCats, int pageNum, int pageSize) throws EnhancedApiException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(fields, "fields must be not null.");

        if (null == sellerCats) {
            return getOnSaleItems(userId, fields, pageNum, pageSize);
        } else {
            checkArgument(sellerCats.size() <= 32, "seller cid num should <= 32, http://api.taobao.com/apidoc/api" +
                    ".htm?spm=0.0.0.0.bANlsY&path=cid:4-apiId:18");

            ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
            request.setFields(Joiner.on(",").join(fields));
            request.setPageNo(Long.valueOf(pageNum));
            request.setPageSize(Long.valueOf(pageSize));
            if (CollectionUtils.isNotEmpty(sellerCats)) {
                request.setSellerCids(Joiner.on(",").join(sellerCats));
            }
            return taobaoApiItemService.getOnSaleItems(userId, request);
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
     * 搜索宝贝，带分页
     * @param userId
     * @param query
     * @param fields
     * @param pageNum
     * @param pageSize
     * @return
     * @throws EnhancedApiException
     */
    public Page<Item> searchOnSaleItems(Long userId, String query, List<String> fields, long pageNum, long pageSize) throws EnhancedApiException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(query, "query must be not null.");
        checkNotNull(fields, "fields must be not null.");

        ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
        request.setFields(Joiner.on(",").join(fields));
        request.setQ(query);
        request.setPageNo(pageNum);
        request.setPageSize(pageSize);
        return taobaoApiItemService.getOnSaleItems(userId, request);
    }

    /**
     * 搜索宝贝，带分页。
     * @param userId
     * @param query
     * @param fields
     * @param sellerCats
     * @param pageNum
     * @param pageSize
     * @return
     * @throws EnhancedApiException
     */
    public Page<Item> searchOnSaleItems(Long userId, String query, List<String> fields, List<Long> sellerCats,
                                        long pageNum, long pageSize) throws EnhancedApiException {
        checkNotNull(userId, "userId must be not null.");
        checkNotNull(query, "query must be not null.");
        checkNotNull(fields, "fields must be not null.");

        if (null == sellerCats) {
            return searchOnSaleItems(userId, query, fields, pageNum, pageSize);
        } else {
            checkArgument(sellerCats.size() <= 32, "seller cid num should <= 32, http://api.taobao.com/apidoc/api" +
                    ".htm?spm=0.0.0.0.bANlsY&path=cid:4-apiId:18");
            ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
            request.setFields(Joiner.on(",").join(fields));
            request.setQ(query);
            request.setPageNo(pageNum);
            request.setPageSize(pageSize);
            if (CollectionUtils.isNotEmpty(sellerCats)) {
                request.setSellerCids(Joiner.on(",").join(sellerCats));
            }
            return taobaoApiItemService.getOnSaleItems(userId, request);
        }
    }
}
