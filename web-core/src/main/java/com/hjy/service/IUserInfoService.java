package com.hjy.service;

import com.hjy.entity.login.UserInfo;

public interface IUserInfoService extends IBaseService<UserInfo, Integer> {

	public UserInfo login(UserInfo entity);
}
