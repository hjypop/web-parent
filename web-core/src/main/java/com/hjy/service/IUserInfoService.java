package com.hjy.service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.entity.login.UserInfo;

public interface IUserInfoService extends IBaseService<UserInfo, Integer> {

	public UserInfo login(UserInfo entity);
	
	public JSONObject deleteOne(UserInfo entity);
	
	public JSONObject editInfo(UserInfo entity);
}
