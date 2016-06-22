package com.hjy.service;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.hjy.pojo.entity.login.UserInfo;

public interface ILoginService extends IBaseService<UserInfo, Integer> {

	public JSONObject login(UserInfo userInfo, HttpSession session);

	public void logout(UserInfo userInfo);
}
