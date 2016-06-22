package com.hjy.service;

import java.util.List;

import com.hjy.pojo.entity.system.JobExectimer;

public interface IJobService  extends IBaseService<JobExectimer , Integer> {
	
	public List<JobExectimer> listJobExectimer(JobExectimer entity);
	
	public Integer updateJobExectimer(JobExectimer entity);
}











