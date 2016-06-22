package com.hjy.dao;

import com.hjy.pojo.entity.login.UserInfo;

public interface IUserInfoDao extends BaseDao<UserInfo, Integer> {

	public UserInfo login(UserInfo entity);

}
