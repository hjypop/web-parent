package com.core.dao;

import com.core.pojo.entity.login.UserInfo;

public interface IUserInfoDao extends BaseDao<UserInfo, Integer> {

	public UserInfo login(UserInfo entity);

}
