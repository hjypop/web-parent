package com.hjy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.IJobExectimerDao;
import com.hjy.pojo.entity.system.JobExectimer;
import com.hjy.service.IJobService;

@Service("jobService")
public class JobServiceImpl extends BaseServiceImpl<JobExectimer, Integer> implements IJobService {
	@Resource
	private IJobExectimerDao jobExectimerDao;

	public List<JobExectimer> listJobExectimer(JobExectimer entity) {
		return jobExectimerDao.findList(entity); 
	}

	public Integer updateJobExectimer(JobExectimer entity) {
		return jobExectimerDao.updateSelectiveByExecCode(entity); 
	}
	
	
	
}



































