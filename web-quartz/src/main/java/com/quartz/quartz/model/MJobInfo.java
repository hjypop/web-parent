package com.quartz.quartz.model;

public class MJobInfo {

	/**
	 * 任务名称
	 */
	private String jobName = "";

	/**
	 * 任务执行类
	 */
	private String jobClass = "";

	/**
	 * 任务执行周期
	 */
	private String jobTriger = "";

	/**
	 * 日志类型 默认为1 为0时标记为不记录日志
	 */
	private int extendTypeLog = 1;

	/**
	 * 锁定时间 为0标记为不锁定 标记为大于0时锁定对应的秒数
	 */
	private int extendLockTimer = 0;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getJobTriger() {
		return jobTriger;
	}

	public void setJobTriger(String jobTriger) {
		this.jobTriger = jobTriger;
	}

	public int getExtendTypeLog() {
		return extendTypeLog;
	}

	public void setExtendTypeLog(int extendTypeLog) {
		this.extendTypeLog = extendTypeLog;
	}

	public int getExtendLockTimer() {
		return extendLockTimer;
	}

	public void setExtendLockTimer(int extendLockTimer) {
		this.extendLockTimer = extendLockTimer;
	}

}
