package com.trilemon.boss.infra.base.dao;

import com.trilemon.boss.infra.base.model.AppUser;

import java.util.List;

public interface AppUserDAO {
    int deleteByPrimaryKey(Long id);

    void insert(AppUser record);

    void insertSelective(AppUser record);

    AppUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppUser record);

    int updateByPrimaryKey(AppUser record);

    AppUser selectByUserIdAndAppKey(Long userId,String appKey);

    int updateByUserIdAndAppKeySelective(AppUser appUser);

    List<Long> paginateIds(int offset, int limit);

    AppUser selectByUserId(Long userId);
}