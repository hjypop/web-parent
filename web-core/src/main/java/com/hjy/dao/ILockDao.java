package com.hjy.dao;

import com.hjy.entity.system.SysLock;
import com.hjy.model.MObjMap;

public interface ILockDao extends BaseDao<SysLock, Integer> {
	@SuppressWarnings("rawtypes")
	public String addLock(MObjMap param);
}
