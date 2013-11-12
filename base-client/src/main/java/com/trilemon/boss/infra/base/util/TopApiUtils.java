/*
 * Copyright (c) 2013 Raycloud.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trilemon.boss.infra.base.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.SellerCat;
import com.taobao.api.internal.util.WebUtils;
import com.trilemon.commons.BeanMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 开放平台工具类
 *
 * @author kevin
 */
public class TopApiUtils {
    public final static ObjectMapper jsonMapper = new ObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(TopApiUtils.class);

    /**
     * 刷新session key
     *
     * @param appKey       app key
     * @param appSecret    app secret
     * @param refreshToken refresh token
     * @param sessionKey   session key
     * @return {@link TopParameters}
     * @throws java.io.IOException 解析{@link TopParameters}过程发生错误。
     */
    public static TopParameters refreshSessionKey(String appKey, String appSecret, String sessionKey,
                                                  String refreshToken) throws IOException {
        Map<String, String> signParams = Maps.newTreeMap();
        signParams.put("appkey", appKey);
        signParams.put("refresh_token", refreshToken);
        signParams.put("sessionkey", sessionKey);

        StringBuilder params = new StringBuilder();
        for (Map.Entry paramEntry : signParams.entrySet()) {
            params.append(paramEntry.getKey()).append(paramEntry.getValue());
        }
        String sign = DigestUtils.md5Hex((params.toString() + appSecret).getBytes("utf-8")).toUpperCase();
        String signEncoder = URLEncoder.encode(sign, "utf-8");
        String appkeyEncoder = URLEncoder.encode(appKey, "utf-8");
        String refreshTokenEncoder = URLEncoder.encode(refreshToken, "utf-8");
        String sessionKeyEncoder = URLEncoder.encode(sessionKey, "utf-8");

        String freshUrl = "http://container.api.taobao.com/container/refresh?appkey=" + appkeyEncoder
                + "&refresh_token=" + refreshTokenEncoder + "&sessionkey=" + sessionKeyEncoder + "&sign="
                + signEncoder;
        String topParametersJson = WebUtils.doPost(freshUrl, null, "utf-8", 30 * 1000 * 60, 30 * 1000 * 60);

        TopParameters topParameters = jsonMapper.readValue(topParametersJson, TopParameters.class);
        return topParameters;
    }

    @Nullable
    public static String getRefreshToken(String queryUrl) throws UnsupportedEncodingException {
        return getTopSessionParameter(queryUrl).getRefreshToken();
    }

    public static TopSessionParameter getTopSessionParameter(String queryUrl) throws UnsupportedEncodingException {
        Map<String, String> parameters = Maps.newHashMap();
        for (String topParameter : queryUrl.split("&")) {
            String[] kv = topParameter.split("=");
            if (kv.length != 2) {
                logger.warn("parameters[{}] length is not valid.", topParameter);
            } else {
                if (kv[0].equals("top_parameters")) {
                    String topParameterValue = kv[1];
                    String decodeTopParameterValue = new String(Base64.decodeBase64(URLDecoder
                            .decode(topParameterValue, "GBK")), "GBK");
                    for (String parameter : decodeTopParameterValue.split("&")) {
                        String[] parameterKv = parameter.split("=");
                        parameters.put(parameterKv[0], parameterKv[1]);
                    }
                } else {
                    parameters.put(kv[0], kv[1]);
                }
            }
        }
        return BeanMapper.map(parameters, TopSessionParameter.class);
    }

//    @Nullable
//    public static com.taobao.api.domain.Trade convertJson2TopTrade(String json) {
//        ObjectJsonParser<TradeFullinfoGetResponse> parser = new ObjectJsonParser(TradeFullinfoGetResponse.class);
//        try {
//            return parser.parse(json).getTrade();
//        } catch (Exception e) {
//            logger.error(json, e);
//            return null;
//        }
//    }
//
//    @Nullable
//    public static Trade convertTopTrade2SuperTrade(com.taobao.api.domain.Trade topTrade) {
//        if (null == topTrade) {
//            return null;
//        }
//        Trade trade;
//        try {
//            trade = BeanMapper.map(topTrade, Trade.class);
//        } catch (Throwable t) {
//            logger.error(ToStringBuilder.reflectionToString(topTrade), t);
//            return null;
//        }
//        Collection<Order> orders = trade.getOrders();
//        Collection<ServiceOrder> serviceOrders = trade.getServiceOrders();
//        Collection<PromotionDetail> promotionDetails = trade.getPromotionDetails();
//
//        if (null != orders) {
//            for (Order order : orders) {
//                order.setTradeId(trade.getId());
//                order.setTradeCreated(trade.getCreated());
//            }
//        }
//        if (null != serviceOrders) {
//            for (ServiceOrder serviceOrder : serviceOrders) {
//                serviceOrder.setTradeId(trade.getId());
//                serviceOrder.setTradeCreated(trade.getCreated());
//            }
//        }
//        if (null != promotionDetails) {
//            for (PromotionDetail promotionDetail : promotionDetails) {
//                promotionDetail.setTradeId(trade.getId());
//                promotionDetail.setTradeCreated(trade.getCreated());
//            }
//        }
//        return trade;
//    }
//
//    @Nullable
//    public static List<Trade> batchConvertTopTrade2SuperTrade(List<com.taobao.api.domain.Trade> topTrades) {
//        List<Trade> trades = Lists.newArrayList();
//        for (com.taobao.api.domain.Trade topTrade : topTrades) {
//            Trade trade = convertTopTrade2SuperTrade(topTrade);
//            if (null == trade) {
//                return null;
//            }
//            trades.add(convertTopTrade2SuperTrade(topTrade));
//        }
//        return null;
//    }
//
//    @Nullable
//    public static User convertTopUser2SuperUser(com.taobao.api.domain.User topUser) {
//        if (null == topUser) {
//            return null;
//        }
//        try {
//            return BeanMapper.map(topUser, User.class);
//        } catch (Throwable t) {
//            logger.error(ToStringBuilder.reflectionToString(topUser), t);
//            return null;
//        }
//    }
//
//    @Nullable
//    public static Shop convertTopShop2SuperShop(com.taobao.api.domain.Shop topShop) {
//        if (null == topShop) {
//            return null;
//        }
//        try {
//            return BeanMapper.map(topShop, Shop.class);
//        } catch (Throwable t) {
//            logger.error(ToStringBuilder.reflectionToString(topShop), t);
//            return null;
//        }
//    }
//
//    public static Refund convertTopRefund2SuperRefund(com.taobao.api.domain.Refund topRefund) {
//        if (null == topRefund) {
//            return null;
//        }
//        try {
//            return BeanMapper.map(topRefund, Refund.class);
//        } catch (Throwable t) {
//            logger.error(ToStringBuilder.reflectionToString(topRefund), t);
//            return null;
//        }
//    }
//

    public static List<Item> excludeItems(List<Item> items, List<Long> excludeItemIds) {
        List<Item> newItems = Lists.newArrayList();
        for (Item item : items) {
            if (!excludeItemIds.contains(item.getNumIid())) {
                newItems.add(item);
            }
        }
        return newItems;
    }

    public static List<Long> getItemNumIids(List<Item> items) {
        return Lists.transform(items, new Function<Item, Long>() {
            @Nullable
            @Override
            public Long apply(@Nullable Item input) {
                return input.getNumIid();
            }
        });
    }

    public static List<Item> getItems(List<Item> items, Collection<Long> numIids) {
        List<Item> filerItems = Lists.newArrayList();

        for (Item item : items) {
            if (numIids.contains(item.getNumIid())) {
                filerItems.add(item);
            }
        }
        return filerItems;
    }

    public static Item getItem(List<Item> items, Long numIid) {

        for (Item item : items) {
            if (numIid == item.getNumIid()) {
                return item;
            }
        }
        return null;
    }

    /**
     * 获取宝贝分类 id
     *
     * @param sellerCidsStr
     * @return
     */
    public static List<Long> getSellerCatIds(String sellerCidsStr) {
        List<Long> sellerCatIds = Lists.newArrayList();
        for (String sellerCidStr : Splitter.on(",").split(sellerCidsStr)) {
            sellerCatIds.add(Long.valueOf(sellerCidStr));
        }
        return sellerCatIds;
    }

    /**
     * 批量获取宝贝分类 id
     *
     * @param sellerCats
     * @return
     */
    public static List<Long> getSellerCatIds(List<SellerCat> sellerCats) {
        return Lists.transform(sellerCats, new Function<SellerCat, Long>() {
            @Nullable
            @Override
            public Long apply(@Nullable SellerCat input) {
                return input.getCid();
            }
        });
    }

    /**
     * 获取指定橱窗状态的宝贝
     *
     * @param items    候选宝贝
     * @param showcase true 橱窗；false 非橱窗
     * @return
     */
    public static Iterable<Item> getShowcaseItems(Iterable<Item> items, final boolean showcase) {
        return Iterables.filter(items, new Predicate<Item>() {
            @Override
            public boolean apply(@Nullable Item input) {
                if (showcase) {
                    return input.getHasShowcase();
                } else {
                    return !input.getHasShowcase();
                }
            }
        });

    }

    /**
     * 定义未分类的{@link SellerCat}对象。
     *
     * @return
     */
    public static SellerCat getUnclassifiedSellerCat() {
        SellerCat sellerCat = new SellerCat();
        sellerCat.setName("未分类");
        sellerCat.setCid(-1L);
        sellerCat.setParentCid(0L);
        return sellerCat;
    }
}
