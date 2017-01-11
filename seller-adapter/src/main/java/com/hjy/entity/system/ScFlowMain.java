package com.hjy.entity.system;

/**
 * @description: 审批流 
 * 
 * @author Yangcl
 * @date 2017年1月11日 下午9:56:01 
 * @version 1.0.0
 */
public class ScFlowMain {
    private Integer zid;
    private String uid;
    private String flowCode;
    private String flowType;
    private String creator;
    private String updator;
    private String createTime;
    private String updateTime;
    private String outerCode;       // 即产品编号  
    private String flowTitle;
    private String flowUrl;
    private String flowRemark;
    private Integer flowIsend;
    private String currentStatus;
    private String lastStatus;
    private String nextOperators;
    private String nextOperatorStatus;
    private String nextOperatorId;
    
    
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
	public String getFlowCode() {
		return flowCode;
	}
	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getUpdator() {
		return updator;
	}
	public void setUpdator(String updator) {
		this.updator = updator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getOuterCode() {
		return outerCode;
	}
	public void setOuterCode(String outerCode) {
		this.outerCode = outerCode;
	}
	public String getFlowTitle() {
		return flowTitle;
	}
	public void setFlowTitle(String flowTitle) {
		this.flowTitle = flowTitle;
	}
	public String getFlowUrl() {
		return flowUrl;
	}
	public void setFlowUrl(String flowUrl) {
		this.flowUrl = flowUrl;
	}
	public String getFlowRemark() {
		return flowRemark;
	}
	public void setFlowRemark(String flowRemark) {
		this.flowRemark = flowRemark;
	}
	public Integer getFlowIsend() {
		return flowIsend;
	}
	public void setFlowIsend(Integer flowIsend) {
		this.flowIsend = flowIsend;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getLastStatus() {
		return lastStatus;
	}
	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}
	public String getNextOperators() {
		return nextOperators;
	}
	public void setNextOperators(String nextOperators) {
		this.nextOperators = nextOperators;
	}
	public String getNextOperatorStatus() {
		return nextOperatorStatus;
	}
	public void setNextOperatorStatus(String nextOperatorStatus) {
		this.nextOperatorStatus = nextOperatorStatus;
	}
	public String getNextOperatorId() {
		return nextOperatorId;
	}
	public void setNextOperatorId(String nextOperatorId) {
		this.nextOperatorId = nextOperatorId;
	}
}