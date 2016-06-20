package com.hjy.entity.system;

import java.util.List;

public class SendLog {

	/*
	 * 日志模式
	 */
	private String model = "";

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	/*
	 * 日志内容
	 */
	private List<LogInfo> logs = null;

	public List<LogInfo> getLogs() {
		return logs;
	}

	public void setLogs(List<LogInfo> logs) {
		this.logs = logs;
	}

}
