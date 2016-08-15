package com.hjy.entity.system;

public class ScFlowStatuschange {
    private Integer zid;

    private String uid;

    private String flowType;

    private String fromStatus;

    private String toStatus;

    private String roleId;

    private String changStatusFunc;

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

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType == null ? null : flowType.trim();
    }

    public String getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus == null ? null : fromStatus.trim();
    }

    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus == null ? null : toStatus.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getChangStatusFunc() {
        return changStatusFunc;
    }

    public void setChangStatusFunc(String changStatusFunc) {
        this.changStatusFunc = changStatusFunc == null ? null : changStatusFunc.trim();
    }
}