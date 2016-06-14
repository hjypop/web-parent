package com.hjy.sample.dao;

import com.hjy.dao.BaseDao;
import com.hjy.sample.entity.UserInfo;

public interface IUserInfoDao extends BaseDao<UserInfo, Integer> {

	public UserInfo login(UserInfo entity);

}
