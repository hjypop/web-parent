package com.hjy.service.login;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.hjy.entity.login.UserInfo;
import com.hjy.service.BaseService;

public interface ILoginService extends BaseService<UserInfo, Integer> {

	public JSONObject login(UserInfo userInfo, HttpSession session);

	public void logout(UserInfo userInfo);
}
