package com.trilemon.boss360.infrastructure.base.dao;

import com.trilemon.boss360.infrastructure.base.module.TaobaoShop;
import com.trilemon.boss360.infrastructure.base.module.TaobaoShopExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaobaoShopMapper {
    int countByExample(TaobaoShopExample example);

    int deleteByExample(TaobaoShopExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TaobaoShop record);

    int insertSelective(TaobaoShop record);

    List<TaobaoShop> selectByExample(TaobaoShopExample example);

    TaobaoShop selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TaobaoShop record, @Param("example") TaobaoShopExample example);

    int updateByExample(@Param("record") TaobaoShop record, @Param("example") TaobaoShopExample example);

    int updateByPrimaryKeySelective(TaobaoShop record);

    int updateByPrimaryKey(TaobaoShop record);
}