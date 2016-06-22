package com.hjy.pojo.entity.system;

import java.util.Date;

public class JobExectimer {
    private Integer zid;
    private String uid;
    private String execCode;
    private String execType;
    private String execInfo;
    private String beginTime;
    private String endTime;
    private String execTime;
    private Integer flagSuccess;
    private Integer execNumber;
    private String remark;
    private Date createTime;
    
    
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
	public String getExecCode() {
		return execCode;
	}
	public void setExecCode(String execCode) {
		this.execCode = execCode;
	}
	public String getExecType() {
		return execType;
	}
	public void setExecType(String execType) {
		this.execType = execType;
	}
	public String getExecInfo() {
		return execInfo;
	}
	public void setExecInfo(String execInfo) {
		this.execInfo = execInfo;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getExecTime() {
		return execTime;
	}
	public void setExecTime(String execTime) {
		this.execTime = execTime;
	}
	public Integer getFlagSuccess() {
		return flagSuccess;
	}
	public void setFlagSuccess(Integer flagSuccess) {
		this.flagSuccess = flagSuccess;
	}
	public Integer getExecNumber() {
		return execNumber;
	}
	public void setExecNumber(Integer execNumber) {
		this.execNumber = execNumber;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
    
}
