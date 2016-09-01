package com.hjy.dto;

public class QueryKjtLog {
	private String rsyncTarget;
	private String requestTime;
	private String responseData;
	private String requestData;
	private String orderCode;
	
	
	public String getRsyncTarget() {
		return rsyncTarget;
	}
	public void setRsyncTarget(String rsyncTarget) {
		this.rsyncTarget = rsyncTarget;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getResponseData() {
		return responseData;
	}
	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}
	public String getRequestData() {
		return requestData;
	}
	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
}
