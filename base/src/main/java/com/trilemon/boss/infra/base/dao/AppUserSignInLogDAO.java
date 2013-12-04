package com.trilemon.boss.infra.base.dao;

import com.trilemon.boss.infra.base.model.AppUserSignInLog;

public interface AppUserSignInLogDAO {
    int deleteByPrimaryKey(Long id);

    void insert(AppUserSignInLog record);

    void insertSelective(AppUserSignInLog record);

    AppUserSignInLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppUserSignInLog record);

    int updateByPrimaryKey(AppUserSignInLog record);
}