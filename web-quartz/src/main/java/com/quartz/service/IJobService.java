package com.quartz.service;

import java.util.List;

import com.core.pojo.entity.system.JobExectimer;
import com.core.service.IBaseService;
import com.quartz.pojo.entity.system.SysJob;

public interface IJobService  extends IBaseService<JobExectimer , Integer> {
	
	public List<JobExectimer> listJobExectimer(JobExectimer entity);
	
	public Integer updateJobExectimer(JobExectimer entity);
	
	public Integer updateSysJobByUuid(SysJob entity);
	
	public List<SysJob> findSysJobList(SysJob entity);
}










