package com.quartz.quartz.model;

public class ConfigJobExec {

	/**
	 * 执行的类型 一般使用definecode
	 */
	private String execType = "";

	/**
	 * 定义最大执行次数 超过该次数将不执行 默认最多执行1000次
	 */
	private int maxExecNumber = 1000;

	/*
	 * 当调用次数等于此数字时发送通知报警邮件
	 */
	private int noticeOnce = 3;

	/**
	 * 执行单个任务的锁定时间 防止并发执行
	 */
	private int execJobLock = 600;

	public String getExecType() {
		return execType;
	}

	public void setExecType(String execType) {
		this.execType = execType;
	}

	public int getMaxExecNumber() {
		return maxExecNumber;
	}

	public void setMaxExecNumber(int maxExecNumber) {
		this.maxExecNumber = maxExecNumber;
	}

	public int getExecJobLock() {
		return execJobLock;
	}

	public void setExecJobLock(int execJobLock) {
		this.execJobLock = execJobLock;
	}

	public int getNoticeOnce() {
		return noticeOnce;
	}

	public void setNoticeOnce(int noticeOnce) {
		this.noticeOnce = noticeOnce;
	}

}
