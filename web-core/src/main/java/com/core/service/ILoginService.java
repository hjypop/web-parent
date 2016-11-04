package com.core.service;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.core.pojo.entity.login.UserInfo;

public interface ILoginService extends IBaseService<UserInfo, Integer> {

	public JSONObject login(UserInfo userInfo, HttpSession session);

	public void logout(UserInfo userInfo);
}
