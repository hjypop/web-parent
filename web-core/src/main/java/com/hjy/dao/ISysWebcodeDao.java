package com.hjy.dao;

import com.hjy.entity.system.SysWebcode;
import com.hjy.model.MObjMap;

public interface ISysWebcodeDao extends BaseDao<SysWebcode , Integer> {
	
	@SuppressWarnings("rawtypes")
	public String callUniqueCode(MObjMap param);
	
}


