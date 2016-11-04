package com.quartz.dao;

import com.core.dao.BaseDao;
import com.quartz.pojo.entity.system.SysJob;

public interface ISysJobDao extends BaseDao<SysJob, Integer> {
	public Integer updateSelectiveByUuid(SysJob entity);
}
