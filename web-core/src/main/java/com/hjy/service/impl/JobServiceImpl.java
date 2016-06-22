package com.hjy.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.IJobExectimerDao;
import com.hjy.entity.system.JobExectimer;
import com.hjy.service.IJobService;

@Service("systemService")
public class JobServiceImpl extends BaseServiceImpl<JobExectimer, Integer> implements IJobService {
	@Resource
	private IJobExectimerDao jobExectimerDao;
	
	
	
}



































