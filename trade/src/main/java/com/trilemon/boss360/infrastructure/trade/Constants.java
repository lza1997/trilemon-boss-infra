package com.trilemon.boss360.infrastructure.trade;

/**
 * @author kevin
 */
public interface Constants {
    String[] TRADE_FIELDS = new String[]{"tid", "num", "num_iid", "status", "title", "type", "price",
            "seller_cod_fee", "discount_fee", "point_fee", "has_post_fee", "total_fee", "is_lgtype", "is_brand_sale",
            "is_force_wlb", "created", "pay_time", "modified", "end_time", "buyer_message", "alipay_id", "alipay_no",
            "buyer_memo", "buyer_flag", "seller_memo", "seller_flag", "invoice_name", "buyer_nick", "buyer_area",
            "buyer_email", "has_yfx", "yfx_fee", "yfx_id", "yfx_type", "credit_card_fee", "step_trade_status",
            "step_paid_fee", "mark_desc", "eticket_ext", "send_time", "shipping_type", "buyer_cod_fee", "express_agency_fee",
            "adjust_fee", "buyer_obtain_point_fee", "cod_fee", "trade_from", "cod_status", "can_rate", "commission_fee",
            "trade_memo", "buyer_rate", "trade_source", "seller_can_rate", "is_part_consign", "seller_nick", "pic_path", "payment",
            "snapshot_url", "seller_rate", "real_point_fee", "post_fee", "buyer_alipay_no", "receiver_name",
            "receiver_state", "receiver_city", "receiver_district", "receiver_address", "receiver_zip", "receiver_mobile",
            "receiver_phone", "consign_time", "seller_alipay_no", "seller_mobile", "seller_phone", "seller_name",
            "seller_email", "available_confirm_fee", "received_payment",
            "timeout_action_time", "is_3D", "promotion", "service_orders", "orders", "promotion_details"};
    String[] TRADE_TYPES = new String[]{"fixed", "auction", "guarantee_trade", "step",
            "independent_simple_trade", "independent_shop_trade", "auto_delivery", "ec", "cod", "game_equipment",
            "shopex_trade", "netcn_trade", "external_trade", "instant_trade", "b2c_cod", "hotel_trade", "super_market_trade",
            "super_market_cod_trade", "taohua", "waimai", "nopaid", "step", "eticket"};
}
