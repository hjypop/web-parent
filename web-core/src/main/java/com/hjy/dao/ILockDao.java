package com.hjy.dao;

import com.hjy.model.MObjMap;
import com.hjy.pojo.entity.system.SysLock;

public interface ILockDao extends BaseDao<SysLock, Integer> {
	@SuppressWarnings("rawtypes")
	public String addLock(MObjMap param);
	
	public Integer deleteByUuid(String uuid);
}
