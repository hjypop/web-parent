package com.hjy.sample.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.sample.dao.IUserInfoDao;
import com.hjy.sample.entity.UserInfo;
import com.hjy.sample.service.IUserInfoService;
import com.hjy.service.BaseServiceImpl;

@Service("userInfoService")
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, Integer> implements IUserInfoService {

	@Resource
	private IUserInfoDao userInfoDao;

	@Override
	public UserInfo login(UserInfo entity) {

		return userInfoDao.login(entity);
	}

}
