package com.hjy.service.login;

import com.hjy.entity.login.UserInfo;
import com.hjy.service.BaseService;

public interface IUserInfoService extends BaseService<UserInfo, Integer> {

	public UserInfo login(UserInfo entity);
}
