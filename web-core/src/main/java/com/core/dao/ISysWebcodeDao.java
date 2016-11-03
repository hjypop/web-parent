package com.core.dao;

import com.core.model.MObjMap;
import com.core.pojo.entity.system.SysWebcode;

public interface ISysWebcodeDao extends BaseDao<SysWebcode , Integer> {
	
	@SuppressWarnings("rawtypes")
	public String callUniqueCode(MObjMap param);
	
}


