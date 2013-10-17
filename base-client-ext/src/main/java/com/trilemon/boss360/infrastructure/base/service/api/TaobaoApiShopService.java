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
import com.trilemon.boss360.infrastructure.base.BaseConstants;
import com.trilemon.boss360.infrastructure.base.client.BaseClient;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSession;
import com.trilemon.boss360.infrastructure.base.service.TaobaoApiService;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

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
     * 根据昵称获取卖家的自定义类目
     *
     * @param nick
     * @return
     * @throws EnhancedApiException
     */
    public List<SellerCat> getSellerCats(String nick) throws EnhancedApiException {
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
     * 根据taobao user id获取卖家的自定义类目
     *
     * @param userId
     * @return
     * @throws EnhancedApiException
     */
    public List<SellerCat> getSellerCats(Long userId) throws EnhancedApiException {
        String nick = baseClient.getNick(userId);
        return getSellerCats(nick);
    }

    /**
     * 获取卖家的自定义类目和类目对应的在售商品数量
     *
     * @param userId
     * @return
     * @throws EnhancedApiException
     */
    public Map<SellerCat, Long> getSellerCatAndOnSaleItemNum(Long userId, List<String> fields) throws EnhancedApiException {
        List<SellerCat> cids = getSellerCats(userId);
        Map<SellerCat, Long> result = Maps.newHashMap();
        for (SellerCat sellerCat : cids) {
            long num = getOnSaleItemNum(userId, Lists.newArrayList(sellerCat.getCid()), fields);
            result.put(sellerCat, num);
        }
        System.out.println(result);
        return result;
    }

    /**
     * 获取指定类目的在售商品数量
     *
     * @param userId
     * @param sellerCats
     * @return
     * @throws EnhancedApiException
     */
    public long getOnSaleItemNum(Long userId, List<Long> sellerCats, List<String> fields) throws EnhancedApiException {
        long num = 0L;

        //淘宝 api 目前只支持一次性传入32个宝贝分类（参见http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.bANlsY&path=cid:4-apiId:18）。
        Iterable<List<Long>> subCidIterable = Iterables.partition(sellerCats, 32);
        for (List<Long> cidList : subCidIterable) {
            ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
            request.setFields(Joiner.on(",").join(fields));
            request.setSellerCids(Joiner.on(",").join(cidList));
            request.setPageNo(1L);
            request.setPageSize(1L);
            Pair<List<Item>, Long> result = taobaoApiItemService.getOnSaleItems(userId, request);
            if (null != result.getRight()) {
                num += result.getRight();
            }
        }
        return num;
    }

    /**
     * 获取指定类目的在售商品列表
     *
     * @param userId
     * @param sellerCats
     * @return
     * @throws EnhancedApiException
     */
    public List<Item> getOnSaleItems(Long userId, List<Long> sellerCats,
                                     List<String> fields) throws EnhancedApiException {
        List<Item> totalItems = Lists.newArrayList();
        long pageSize = 100;

        //淘宝 api 目前只支持一次性传入32个宝贝分类（参见http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.bANlsY&path=cid:4-apiId:18）。
        Iterable<List<Long>> subCidIterable = Iterables.partition(sellerCats, 32);
        for (List<Long> cidList : subCidIterable) {
            long pageNum = 1;
            while (true) {
                ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
                request.setFields(Joiner.on(",").join(fields));
                request.setPageNo(pageNum);
                request.setPageSize(pageSize);
                request.setSellerCids(Joiner.on(",").join(cidList));
                Pair<List<Item>, Long> onSaleItems = taobaoApiItemService.getOnSaleItems(userId, request);

                List<Item> items=onSaleItems.getLeft();
                if (null != items) {
                    totalItems.addAll(items);
                }
                if (null == items ||items.size() == 0) {
                    break;
                } else {
                    pageNum++;
                }
            }
        }
        return totalItems;
    }

    /**
     * 获取指定类目的在售商品列表
     *
     * @param userId
     * @param sellerCats
     * @return
     * @throws EnhancedApiException
     */
    public Pair<List<Item>, Long> getOnSaleItems(Long userId, List<Long> sellerCats,
                                     List<String> fields, long pageNum, long pageSize) throws EnhancedApiException {

        checkArgument(sellerCats.size() <= 32, "seller cid num should <= 32, http://api.taobao.com/apidoc/api" +
                ".htm?spm=0.0.0.0.bANlsY&path=cid:4-apiId:18");
        ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
        request.setFields(Joiner.on(",").join(fields));
        request.setPageNo(pageNum);
        request.setPageSize(pageSize);
        request.setSellerCids(Joiner.on(",").join(sellerCats));
        Pair<List<Item>, Long> onSaleItems=taobaoApiItemService.getOnSaleItems(userId, request);
        return onSaleItems;
    }

    public long getTradeNumFromTop(Long userId, String appKey, Date startHour,
                                   Date endHour) throws EnhancedApiException {
        TaobaoSession taobaoSession = baseClient.getTaobaoSession(userId, taobaoApiService.getAppKey());
        TradesSoldGetRequest request = new TradesSoldGetRequest();
        request.setFields("tid");
        request.setType(Joiner.on(",").join(BaseConstants.TRADE_TYPES));
        request.setStartCreated(startHour);
        request.setEndCreated(endHour);
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

    public long getTradeNumFromTop(Long userId, Date startHour,
                                   Date endHour) throws EnhancedApiException {
        return getTradeNumFromTop(userId, taobaoApiService.getAppKey(), startHour, endHour);
    }

    public Pair<List<Item>, Long> searchOnSaleItems(Long userId, String query,List<Long> sellerCats,
                            List<String> fields, long pageNum, long pageSize) throws EnhancedApiException {
        checkArgument(sellerCats.size() <= 32, "seller cid num should <= 32, http://api.taobao.com/apidoc/api" +
                ".htm?spm=0.0.0.0.bANlsY&path=cid:4-apiId:18");
        ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
        request.setFields(Joiner.on(",").join(fields));
        request.setQ(query);
        request.setPageNo(pageNum);
        request.setPageSize(pageSize);
        request.setSellerCids(Joiner.on(",").join(sellerCats));
        Pair<List<Item>, Long> onSaleItems=taobaoApiItemService.getOnSaleItems(userId, request);
        return onSaleItems;

    }
}
