package com.quartz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.core.pojo.entity.system.JobExectimer;
import com.core.service.impl.BaseServiceImpl;
import com.quartz.dao.IJobExectimerDao;
import com.quartz.dao.ISysJobDao;
import com.quartz.pojo.entity.system.SysJob;
import com.quartz.service.IJobService;

@Service("jobService")
public class JobServiceImpl extends BaseServiceImpl<JobExectimer, Integer> implements IJobService {
	@Resource
	private IJobExectimerDao jobExectimerDao;
	@Resource
	private ISysJobDao sysJobDao;

	public List<JobExectimer> listJobExectimer(JobExectimer entity) {
		return jobExectimerDao.findList(entity); 
	}

	public Integer updateJobExectimer(JobExectimer entity) {
		return jobExectimerDao.updateSelectiveByExecCode(entity); 
	}

	public Integer updateSysJobByUuid(SysJob entity) {
		return sysJobDao.updateSelectiveByUuid(entity); 
	}

	public List<SysJob> findSysJobList(SysJob entity) {
		return sysJobDao.findList(entity);
	}
}



































