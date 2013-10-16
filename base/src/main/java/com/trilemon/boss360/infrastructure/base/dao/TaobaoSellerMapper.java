package com.trilemon.boss360.infrastructure.base.dao;

import com.trilemon.boss360.infrastructure.base.model.TaobaoSeller;
import com.trilemon.boss360.infrastructure.base.model.TaobaoSellerExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaobaoSellerMapper {
    int countByExample(TaobaoSellerExample example);

    int deleteByExample(TaobaoSellerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TaobaoSeller record);

    int insertSelective(TaobaoSeller record);

    List<TaobaoSeller> selectByExample(TaobaoSellerExample example);

    TaobaoSeller selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TaobaoSeller record, @Param("example") TaobaoSellerExample example);

    int updateByExample(@Param("record") TaobaoSeller record, @Param("example") TaobaoSellerExample example);

    int updateByPrimaryKeySelective(TaobaoSeller record);

    int updateByPrimaryKey(TaobaoSeller record);

    TaobaoSeller selectByUserId(Long userId);
}