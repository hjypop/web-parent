package com.hjy.sample.service;

import com.hjy.sample.entity.UserInfo;
import com.hjy.service.BaseService;

public interface IUserInfoService extends BaseService<UserInfo, Integer> {

	public UserInfo login(UserInfo entity);
}
