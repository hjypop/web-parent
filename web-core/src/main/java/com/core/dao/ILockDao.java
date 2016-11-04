package com.core.dao;

import com.core.model.MObjMap;
import com.core.pojo.entity.system.SysLock;

public interface ILockDao extends BaseDao<SysLock, Integer> {
	@SuppressWarnings("rawtypes")
	public String addLock(MObjMap param);
}
