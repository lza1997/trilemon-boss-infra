package com.trilemon.boss.infra.base.model.dto;

import com.taobao.api.domain.SellerCat;

import java.io.Serializable;

/**
 * @author kevin
 */
public class SellerCatExtended implements Serializable {
    private SellerCat sellerCat;
    private Long itemNum;
    private boolean isUsed = false;

    public SellerCat getSellerCat() {
        return sellerCat;
    }

    public void setSellerCat(SellerCat sellerCat) {
        this.sellerCat = sellerCat;
    }

    public Long getItemNum() {
        return itemNum;
    }

    public void setItemNum(Long itemNum) {
        this.itemNum = itemNum;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
