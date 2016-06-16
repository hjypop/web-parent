package com.hjy.service.login.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.login.IUserInfoDao;
import com.hjy.entity.login.UserInfo;
import com.hjy.service.BaseServiceImpl;
import com.hjy.service.login.IUserInfoService;

@Service("userInfoService")
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, Integer> implements IUserInfoService {

//	@Resource
	private IUserInfoDao userInfoDao;

	@Override
	public UserInfo login(UserInfo entity) {
		return userInfoDao.login(entity);
	}
}
