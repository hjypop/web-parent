package com.hjy.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
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
	
	@Override
	public JSONObject deleteOne(UserInfo entity) {
		JSONObject result = new JSONObject();
		if(StringUtils.isNotBlank(entity.getId().toString())){
			Integer count = userInfoDao.deleteById(entity.getId());
			if(count == 1){
				result.put("status", "success");
				result.put("msg", "删除成功");
			}else{
				result.put("status", "error");
				result.put("msg", "删除失败");
			}
		}else{
			result.put("status", "error");
			result.put("msg", "删除记录Id不可为空");
		}
		return result;
	}

	@Override
	public JSONObject editInfo(UserInfo entity) {
		JSONObject result = new JSONObject();
		if(StringUtils.isNotBlank(entity.getId().toString())){
			Integer count = userInfoDao.updateSelective(entity);
			if(count == 1){
				result.put("status", "success");
				result.put("msg", "更新成功");
			}else{
				result.put("status", "error");
				result.put("msg", "更新失败");
			}
		}else{
			result.put("status", "error");
			result.put("msg", "更新记录Id不可为空");
		}
		return result;
	}
}
