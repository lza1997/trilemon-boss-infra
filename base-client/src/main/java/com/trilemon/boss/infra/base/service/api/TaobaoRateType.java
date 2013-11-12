package com.trilemon.boss.infra.base.service.api;

/**
 * @author kevin
 */
public enum TaobaoRateType {
    RATE_TYPE_GOOD("good"),
    RATE_TYPE_NEUTRAL("neutral"),
    RATE_TYPE_BAD("bad");
    private String type;

    private TaobaoRateType(String type) {
        this.type = type;
    }
}
