package com.hjy.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.IUserInfoDao;
import com.hjy.entity.login.UserInfo;
import com.hjy.service.IUserInfoService;

@Service("userInfoService")
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, Integer> implements IUserInfoService {

	@Resource
	private IUserInfoDao userInfoDao;

	@Override
	public UserInfo login(UserInfo entity) {
		return userInfoDao.login(entity);
	}
}
