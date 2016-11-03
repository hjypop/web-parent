package com.hjy.quartz.job;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.core.annotation.Inject;
import com.core.base.BaseClass;
import com.core.helper.DateHelper;
import com.core.helper.FormatHelper;
import com.core.helper.LogHelper;
import com.core.helper.WebHelper;
import com.core.iface.IBaseJob;
import com.core.model.MDataMap;
import com.core.system.TopConst;
import com.hjy.pojo.entity.system.SysJob;
import com.hjy.quartz.model.MJobInfo;
import com.hjy.quartz.model.MLogJob;
import com.hjy.service.IJobService;
// properties配置信息核对完成 
public abstract class RootJobForLock extends BaseClass implements Job, IBaseJob {

	@Inject
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
			getLogger().logError(300000003 , this.getClass().getName());     
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
	

}



















