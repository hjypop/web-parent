package com.hjy.sample.service;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.hjy.sample.entity.UserInfo;
import com.hjy.service.BaseService;

public interface ILoginService extends BaseService<UserInfo, Integer> {

	public JSONObject login(UserInfo userInfo, HttpSession session);

	public void logout(UserInfo userInfo);
}
