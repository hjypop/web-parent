package com.hjy.dao;

import com.hjy.model.MObjMap;
import com.hjy.pojo.entity.system.SysWebcode;

public interface ISysWebcodeDao extends BaseDao<SysWebcode , Integer> {
	
	@SuppressWarnings("rawtypes")
	public String callUniqueCode(MObjMap param);
	
}


