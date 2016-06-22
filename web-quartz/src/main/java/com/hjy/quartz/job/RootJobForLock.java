package com.hjy.quartz.job;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hjy.base.BaseClass;
import com.hjy.helper.DateHelper;
import com.hjy.helper.FormatHelper;
import com.hjy.helper.LogHelper;
import com.hjy.helper.WebHelper;
import com.hjy.iface.IBaseJob;
import com.hjy.model.MDataMap;
import com.hjy.pojo.entity.system.SysJob;
import com.hjy.quartz.model.MJobInfo;
import com.hjy.quartz.model.MLogJob;
import com.hjy.service.IJobService;
import com.hjy.system.config.TopConst;

@Component
public abstract class RootJobForLock extends BaseClass implements Job, IBaseJob {

	@Autowired
	public IJobService jobService;
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String sLockKey = "";
		boolean bFlagExec = true;
		Date sBeginTime = new Date();
		Date sNextTime = null;
		Date sEndTime = null;

		MJobInfo mJobInfo = new MJobInfo();
		try {
			if (context != null && context.getMergedJobDataMap() != null && context.getMergedJobDataMap().containsKey( TopConst.CONST_JOB_START + "status")) {

				if (context.getNextFireTime() != null) {
					sNextTime = context.getNextFireTime(); 
				}
				mJobInfo = (MJobInfo) context.getMergedJobDataMap().get(TopConst.CONST_JOB_START + "status");

				// 判断如果记日志
				if (mJobInfo.getExtendTypeLog() == 1) {
					MLogJob mLogJob = new MLogJob();
					mLogJob.setNextExecTime(DateHelper.formatDate(sNextTime));
					mLogJob.setJobInfo(mJobInfo);
					LogHelper.addLog("run_job", mLogJob);
				}

				// 判断如果加锁 则开始加锁处理
				if (mJobInfo.getExtendLockTimer() > 0) {
					sLockKey = WebHelper.getInstance().addLock(mJobInfo.getExtendLockTimer(), mJobInfo.getJobName());
					if (StringUtils.isBlank(sLockKey)) {
						bFlagExec = false;
					}
				}
			}

			if (bFlagExec) {
				doExecute(context);
				sEndTime = new Date();
			}
		} catch (Exception e) {
			bLogError(967905003, this.getClass().getName());      // 967905003=定时任务{0}执行失败  info.zapcom.9679.properties line5
			WebHelper.getInstance().errorMessage(mJobInfo.getJobName(), "jobexecerror", 9, "rootjobforlock", mJobInfo.getJobClass(), e);
			e.printStackTrace();
		}

		// 如果key不为空 则直接解锁
		if (StringUtils.isNotBlank(sLockKey)) {
			WebHelper.getInstance().unLock(sLockKey);
		}

		// 开始更新执行日志
		if (mJobInfo != null && StringUtils.isNotBlank(mJobInfo.getJobName())) {
			SysJob entity = new SysJob();
			entity.setUid(mJobInfo.getJobName());
			entity.setBeginTime(sBeginTime);
			entity.setEndTime(sEndTime);
			entity.setNextTime(sNextTime);
			jobService.updateSysJobByUuid(entity);
		}
	}
	
	
	
	/**
	 * @deprecated
	 * @descriptions 
	 * 
	 * @param context
	 * @throws JobExecutionException
	 * @date 2016年6月22日下午6:25:59
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public void execute222222(JobExecutionContext context) throws JobExecutionException {
		String sLockKey = "";
		boolean bFlagExec = true;
		String sBeginTime = FormatHelper.upDateTime();
		String sNextTime = "";
		String sEndTime = "";

		MJobInfo mJobInfo = new MJobInfo();
		try {
			if (context != null && context.getMergedJobDataMap() != null && context.getMergedJobDataMap().containsKey( TopConst.CONST_JOB_START + "status")) {

				if (context.getNextFireTime() != null) {
					sNextTime = DateHelper.formatDate(context.getNextFireTime());
				}

				mJobInfo = (MJobInfo) context.getMergedJobDataMap().get(TopConst.CONST_JOB_START + "status");

				// 判断如果记日志
				if (mJobInfo.getExtendTypeLog() == 1) {
					MLogJob mLogJob = new MLogJob();
					mLogJob.setNextExecTime(sNextTime);
					mLogJob.setJobInfo(mJobInfo);
					LogHelper.addLog("run_job", mLogJob);
				}

				// 判断如果加锁 则开始加锁处理
				if (mJobInfo.getExtendLockTimer() > 0) {
					sLockKey = WebHelper.getInstance().addLock(mJobInfo.getExtendLockTimer(), mJobInfo.getJobName());
					if (StringUtils.isBlank(sLockKey)) {
						bFlagExec = false;
					}
				}
			}

			if (bFlagExec) {
				doExecute(context);
				sEndTime = FormatHelper.upDateTime();
			}
		} catch (Exception e) {
			bLogError(967905003, this.getClass().getName());   
			WebHelper.getInstance().errorMessage(mJobInfo.getJobName(), "jobexecerror", 9, "rootjobforlock", mJobInfo.getJobClass(), e);
			e.printStackTrace();
		}

		// 如果key不为空 则直接解锁
		if (StringUtils.isNotBlank(sLockKey)) {
			WebHelper.getInstance().unLock(sLockKey);
		}

		// 开始更新执行日志
		if (mJobInfo != null && StringUtils.isNotBlank(mJobInfo.getJobName())) {

			MDataMap mUpdateMap = new MDataMap();
			mUpdateMap.initKeyValues("uid", mJobInfo.getJobName(), "begin_time",
					sBeginTime, "end_time", sEndTime, "next_time", sNextTime);

			//za_job -> sys_job
			//#jobservice#
//			DbUp.upTable("za_job").dataUpdate(mUpdateMap, "begin_time,end_time,next_time", "uid");

		}
	}

}



















