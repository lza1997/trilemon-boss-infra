package com.trilemon.boss360.infrastructure.base.dao;

import com.trilemon.boss360.infrastructure.base.model.TaobaoApp;
import com.trilemon.boss360.infrastructure.base.model.TaobaoAppExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaobaoAppMapper {
    int countByExample(TaobaoAppExample example);

    int deleteByExample(TaobaoAppExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TaobaoApp record);

    int insertSelective(TaobaoApp record);

    List<TaobaoApp> selectByExample(TaobaoAppExample example);

    TaobaoApp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TaobaoApp record, @Param("example") TaobaoAppExample example);

    int updateByExample(@Param("record") TaobaoApp record, @Param("example") TaobaoAppExample example);

    int updateByPrimaryKeySelective(TaobaoApp record);

    int updateByPrimaryKey(TaobaoApp record);

    TaobaoApp selectByAppKey(String taobaoAppKey);
}