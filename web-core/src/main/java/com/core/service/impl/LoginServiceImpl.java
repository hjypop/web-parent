package com.core.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.core.dao.IUserInfoDao;
import com.core.pojo.entity.login.UserInfo;
import com.core.service.ILoginService;

@Service("loginService")
public class LoginServiceImpl extends BaseServiceImpl<UserInfo, Integer> implements ILoginService {

	@Resource
	private IUserInfoDao userInfoDao;

	/**
	 * @descriptions 用户登录操作
	 * 
	 * @param userInfo
	 * @return
	 * @refactor no
	 * @date 2016年5月20日上午11:27:59
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
	public JSONObject login(UserInfo userInfo, HttpSession session) {
		JSONObject result = new JSONObject();

		if (StringUtils.isBlank(userInfo.getUserName()) || StringUtils.isBlank(userInfo.getPassword())) {
			result.put("status", "error");
			result.put("msg", "用户名或密码不得为空");
			return result;
		}
		UserInfo info = userInfoDao.login(userInfo);
		if (null != info) {
			session.setAttribute("userInfo", info); // 写入session
			result.put("data", info);
			result.put("status", "success");
			result.put("msg", "调用成功");
			return result;
		} else {
			result.put("status", "error");
			result.put("msg", "用户名或密码错误");
			return result;
		}
	}

	/**
	 * @descriptions 用户退出
	 * 
	 * @param userInfo
	 * @refactor no
	 * @date 2016年5月20日上午11:29:28
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
	public void logout(UserInfo userInfo) {
	}

}
