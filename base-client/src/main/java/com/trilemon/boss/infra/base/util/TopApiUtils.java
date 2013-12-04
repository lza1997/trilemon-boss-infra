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

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
import com.google.common.io.Files;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.SellerCat;
import com.taobao.api.internal.util.WebUtils;
import com.trilemon.boss.infra.base.model.TaobaoSession;
import com.trilemon.commons.JsonMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 开放平台工具类
 *
 * @author kevin
 */
public class TopApiUtils {
    private final static BiMap<String, Integer> TAOBAO_RATES = ImmutableBiMap.<String, Integer>builder()
            .put("无等级", 0)
            .put("1_1", 1)
            .put("1_2", 2)
            .put("1_3", 3)
            .put("1_4", 4)
            .put("1_5", 5)
            .put("2_1", 6)
            .put("2_2", 7)
            .put("2_3", 8)
            .put("2_4", 9)
            .put("2_5", 10)
            .put("3_1", 11)
            .put("3_2", 12)
            .put("3_3", 13)
            .put("3_4", 14)
            .put("3_5", 15)
            .put("4_1", 16)
            .put("4_2", 17)
            .put("4_3", 18)
            .put("4_4", 19)
            .put("4_5", 20)
            .build();
    private static Logger logger = LoggerFactory.getLogger(TopApiUtils.class);
    private static Pattern ITEM_NUM_IID_PATTERN = Pattern.compile("(http://)*item.taobao.com/item\\.htm\\?.*id=" +
            "(\\d+).*");

    public static int getTbRate(String imgSrc) {
        String fileNameWithoutExt = Files.getNameWithoutExtension(imgSrc);
        return TAOBAO_RATES.get(fileNameWithoutExt.split("_", 2)[1]);
    }

    public static String getTbRateImgUrl(int rate) {
        String tag = TAOBAO_RATES.inverse().get(rate);
        return "http://a.tbcdn.cn/sys/common/icon/rank/b_" + tag + ".gif";
    }

    /**
     * 刷新session key
     *
     *
     * @param appKey       app key
     * @param appSecret    app secret
     * @param sessionKey   session key
     * @param refreshToken refresh token
     * @return {@link TaobaoSession}
     * @throws java.io.IOException 解析{@link TaobaoSession}过程发生错误。
     */
    public static TaobaoSession refreshSessionKey(String appKey, String appSecret, String sessionKey,
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

        TaobaoSession taobaoSession = JsonMapper.nonEmptyMapper().fromJson(topParametersJson, TaobaoSession.class);
        return taobaoSession;
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

    /**
     * 根据商品 url 获取 num iid
     *
     * @param url
     * @return
     */
    public static Long getItemUrlNumIid(String url) {
        Matcher matcher = ITEM_NUM_IID_PATTERN.matcher(url);
        if (matcher.find() && matcher.groupCount() > 1) {
            String numIidStr = matcher.group(2);
            if (NumberUtils.isNumber(numIidStr)) {
                return Long.valueOf(numIidStr);
            } else {
                return null;
            }
        }
        return null;
    }
}
