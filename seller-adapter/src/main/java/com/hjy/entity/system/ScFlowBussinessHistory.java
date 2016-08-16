package com.hjy.entity.system;

public class ScFlowBussinessHistory {
    private Integer zid;

    private String uid;

    private String flowCode;

    private String flowType;

    private String creator;

    private String createTime;

    private String flowRemark;

    private String currentStatus;
    
    
    

    public ScFlowBussinessHistory() {
	}

	public ScFlowBussinessHistory(String uid, String flowCode, String flowType, String creator,
			String createTime, String flowRemark, String currentStatus) { 
		this.uid = uid;
		this.flowCode = flowCode;
		this.flowType = flowType;
		this.creator = creator;
		this.createTime = createTime;
		this.flowRemark = flowRemark;
		this.currentStatus = currentStatus;
	}

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
        this.uid = uid == null ? null : uid.trim();
    }

    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode == null ? null : flowCode.trim();
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType == null ? null : flowType.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getFlowRemark() {
        return flowRemark;
    }

    public void setFlowRemark(String flowRemark) {
        this.flowRemark = flowRemark == null ? null : flowRemark.trim();
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus == null ? null : currentStatus.trim();
    }
}