package com.core.jms;

import com.core.api.RootInput;

public class ApiKeepLiveInput extends RootInput {
	/**
	 * 服务编码
	 */
	private String serverCode = "undefined";

	/**
	 * IP地址
	 */
	private String ipAddress = "";

	/**
	 * 运行模式 leader或者follower
	 */
	private String runType = "";
	
	/**
	 * 运行服务列表
	 */
	private String runList="";

	/**
	 * 本机回调地址
	 */
	private String apiHost = "";

	/**
	 * 通知时间
	 */
	private String noticeTime = "";
	
	
	/**
	 * 同步配置文件名
	 */
	private String syncConfig="";
	

	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getApiHost() {
		return apiHost;
	}

	public void setApiHost(String apiHost) {
		this.apiHost = apiHost;
	}

	public String getRunType() {
		return runType;
	}

	public void setRunType(String runType) {
		this.runType = runType;
	}

	public String getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}

	public String getSyncConfig() {
		return syncConfig;
	}

	public void setSyncConfig(String syncConfig) {
		this.syncConfig = syncConfig;
	}

	public String getRunList() {
		return runList;
	}

	public void setRunList(String runList) {
		this.runList = runList;
	}

}
