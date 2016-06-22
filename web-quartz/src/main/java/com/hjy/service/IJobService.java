package com.hjy.service;

import java.util.List;

import com.hjy.pojo.entity.system.JobExectimer;
import com.hjy.pojo.entity.system.SysJob;

public interface IJobService  extends IBaseService<JobExectimer , Integer> {
	
	public List<JobExectimer> listJobExectimer(JobExectimer entity);
	
	public Integer updateJobExectimer(JobExectimer entity);
	
	public Integer updateSysJobByUuid(SysJob entity);
	
	public List<SysJob> findSysJobList(SysJob entity);
}











