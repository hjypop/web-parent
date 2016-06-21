package com.hjy.quartz.model;

import com.hjy.iface.IBaseResult;

public class JobResult implements IBaseResult {
	private int code;
	private String message;
	
	public JobResult() {}
	
	public JobResult(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	@Override
	public int getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
