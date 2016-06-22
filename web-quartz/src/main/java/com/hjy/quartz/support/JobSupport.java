package com.hjy.quartz.support;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.ClassUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.hjy.base.BaseClass;
import com.hjy.iface.IBaseJob;
import com.hjy.quartz.model.MJobInfo;
import com.hjy.system.TopConst;

public class JobSupport extends BaseClass {

	private static JobSupport jobSupport = null;

	private Scheduler scheduler = null;

	private int iNumIndex = 0;

	public static JobSupport getInstance() {
		if (jobSupport == null) {

			jobSupport = new JobSupport();

		}
		return jobSupport;
	}

	/**
	 * 添加定时任务
	 * 
	 * @param sClassName
	 *            类名称
	 * @param sTriger
	 *            定时表达式 标准quartz结构体
	 * @param sJobName
	 *            任务名称 可以为空 为空则自动生成
	 */
	public synchronized void addJob(MJobInfo mJobInfo) {

		try {
			if (scheduler == null) {
				SchedulerFactory sf = new StdSchedulerFactory();

				scheduler = sf.getScheduler();
				scheduler.start();

			}
			@SuppressWarnings("unchecked")
			Class<IBaseJob> jClass = ClassUtils
					.getClass(mJobInfo.getJobClass());

			JobDetail job = JobBuilder
					.newJob(jClass)
					.withIdentity(mJobInfo.getJobName(),
							Scheduler.DEFAULT_GROUP).build(); // 设置作业，具体操作在SimpleJob类里

			CronTrigger trigger = (CronTrigger) TriggerBuilder
					.newTrigger()
					.withIdentity("trigger_" + mJobInfo.getJobName(),
							Scheduler.DEFAULT_GROUP)
					.withSchedule(
							CronScheduleBuilder.cronSchedule(mJobInfo
									.getJobTriger())).build(); // 设置触发器

			Set<CronTrigger> sTriggers = new HashSet<CronTrigger>();
			sTriggers.add(trigger);

			// job.getJobDataMap().put(TopConst.CONST_JOB_START+ "job_uid",
			// sJobName);

			job.getJobDataMap().put(TopConst.CONST_JOB_START + "status",
					mJobInfo);

			scheduler.scheduleJob(job, sTriggers, true); // 设置调度作业

		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

	}

	/**
	 * 删除任务
	 * 
	 * @param sJobName
	 * @return
	 */
	public boolean deleteJob(String sJobName) {
		try {
			scheduler.deleteJob(JobKey
					.jobKey(sJobName, Scheduler.DEFAULT_GROUP));
		} catch (SchedulerException e) {

			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 关闭所有定时器
	 * 
	 * @return
	 */
	public boolean shutDown() {

		try {

			if (scheduler != null) {
				scheduler.shutdown();

				// 延迟一秒 静候所有任务停止
				Thread.sleep(1000);

				while (!scheduler.isShutdown()) {
					Thread.sleep(1000);
				}
				scheduler.shutdown(true);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return true;
	}

	public boolean start() {
		try {
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {

			e.printStackTrace();
		}

		return true;
	}

	public boolean pauseAll() {

		try {
			scheduler.pauseAll();
		} catch (SchedulerException e) {

			e.printStackTrace();
		}
		return true;
	}

	public boolean resumeAll() {
		try {

			scheduler.resumeAll();
		} catch (SchedulerException e) {

			e.printStackTrace();
		}
		return true;
	}

	public boolean restart() {
		shutDown();
		return start();
	}

}
