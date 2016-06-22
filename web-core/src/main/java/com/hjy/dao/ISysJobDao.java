package com.hjy.dao;

import com.hjy.pojo.entity.system.SysJob;

public interface ISysJobDao extends BaseDao<SysJob, Integer> {
	public Integer updateSelectiveByUuid(SysJob entity);
}
