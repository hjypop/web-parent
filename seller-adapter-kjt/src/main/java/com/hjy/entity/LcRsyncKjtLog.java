package com.hjy.entity;

/**
 * 
 * 类: LcRsyncKjtLog <br>
 * 描述: 同步跨境通日志表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午3:26:20
 */
public class LcRsyncKjtLog {

	private Integer zid;
	private String uid;
	// 编码
	private String code;
	// 同步目标
	private String rsyncTarget;
	// 调用接口地址
	private String rsyncUrl;
	// 请求数据
	private String requestData;
	// 响应数据
	private String responseData;
	// 请求时间
	private String requestTime;
	// 响应时间
	private String responseTime;
	// 是否调用成功
	private Integer flagSuccess;
	// 报错信息
	private String errorExpection;
	// 处理完成时间
	private String processTime;
	// 处理结果
	private String processData;
	// 状态数据
	private String statusData;
	// 预期处理数据数量
	private Integer processNum;
	// 成功处理数量
	private Integer successNum;

	public Integer getZid() {
		return zid;
	}

	public void setZid(Integer zid) {
		this.zid = zid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRsyncTarget() {
		return rsyncTarget;
	}

	public void setRsyncTarget(String rsyncTarget) {
		this.rsyncTarget = rsyncTarget;
	}

	public String getRsyncUrl() {
		return rsyncUrl;
	}

	public void setRsyncUrl(String rsyncUrl) {
		this.rsyncUrl = rsyncUrl;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public Integer getFlagSuccess() {
		return flagSuccess;
	}

	public void setFlagSuccess(Integer flagSuccess) {
		this.flagSuccess = flagSuccess;
	}

	public String getErrorExpection() {
		return errorExpection;
	}

	public void setErrorExpection(String errorExpection) {
		this.errorExpection = errorExpection;
	}

	public String getProcessTime() {
		return processTime;
	}

	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}

	public String getProcessData() {
		return processData;
	}

	public void setProcessData(String processData) {
		this.processData = processData;
	}

	public String getStatusData() {
		return statusData;
	}

	public void setStatusData(String statusData) {
		this.statusData = statusData;
	}

	public Integer getProcessNum() {
		return processNum;
	}

	public void setProcessNum(Integer processNum) {
		this.processNum = processNum;
	}

	public Integer getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}

}
