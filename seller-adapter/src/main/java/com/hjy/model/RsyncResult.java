package com.hjy.model;

 

public class RsyncResult extends MWebResult {

	/**
	 * 状态数据 成功时存储状态的
	 */
	private String statusData = "";

	/**
	 * 处理的结果数据
	 */
	private String processData = "";

	/**
	 * 预期处理数量
	 */
	private int processNum = -1;

	/**
	 * 处理成功数量
	 */
	private int successNum = -1;

	public String getStatusData() {
		return statusData;
	}

	public void setStatusData(String statusData) {
		this.statusData = statusData;
	}

	public String getProcessData() {
		return processData;
	}

	public void setProcessData(String processData) {
		this.processData = processData;
	}

	public int getProcessNum() {
		return processNum;
	}

	public void setProcessNum(int processNum) {
		this.processNum = processNum;
	}

	public int getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
	}

}
