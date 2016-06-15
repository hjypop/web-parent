package com.hjy.dao.login;

import com.hjy.dao.BaseDao;
import com.hjy.entity.login.UserInfo;

public interface IUserInfoDao extends BaseDao<UserInfo, Integer> {

	public UserInfo login(UserInfo entity);

}
