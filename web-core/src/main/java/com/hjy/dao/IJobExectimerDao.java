package com.hjy.dao;

import com.hjy.pojo.entity.system.JobExectimer;

public interface IJobExectimerDao extends BaseDao<JobExectimer, Integer> {

	/**
	 * @descriptions JobServiceImpl 使用一次
	 * 
	 * @param entity
	 * @return
	 * @date 2016年6月22日下午3:38:32
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public Integer updateSelectiveByExecCode(JobExectimer entity);
}
