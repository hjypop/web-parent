package com.hjy.pojo.entity.system;

import java.util.Date;
import java.util.List;

public class SysJob {
	
	private Integer zid;
    private String uid;
    private String jobTitle;
    private String jobClass;
    private String jobTriger;
    private String runGroupDid;
    private Integer flagEnable;
    private String jobRemark;
    private Date beginTime;
    private Integer flagParallel;
    private Integer maxExecTime;
    private Date endTime;
    private String setExtend;
    private Date nextTime;
    
    private List<String> rglist; //  runGroupDid list - Yangcl 
    
    
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
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
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
	public String getRunGroupDid() {
		return runGroupDid;
	}
	public void setRunGroupDid(String runGroupDid) {
		this.runGroupDid = runGroupDid;
	}
	public Integer getFlagEnable() {
		return flagEnable;
	}
	public void setFlagEnable(Integer flagEnable) {
		this.flagEnable = flagEnable;
	}
	public String getJobRemark() {
		return jobRemark;
	}
	public void setJobRemark(String jobRemark) {
		this.jobRemark = jobRemark;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Integer getFlagParallel() {
		return flagParallel;
	}
	public void setFlagParallel(Integer flagParallel) {
		this.flagParallel = flagParallel;
	}
	public Integer getMaxExecTime() {
		return maxExecTime;
	}
	public void setMaxExecTime(Integer maxExecTime) {
		this.maxExecTime = maxExecTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getSetExtend() {
		return setExtend;
	}
	public void setSetExtend(String setExtend) {
		this.setExtend = setExtend;
	}
	public Date getNextTime() {
		return nextTime;
	}
	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}
	public List<String> getRglist() {
		return rglist;
	}
	public void setRglist(List<String> rglist) {
		this.rglist = rglist;
	}
    
    
}
