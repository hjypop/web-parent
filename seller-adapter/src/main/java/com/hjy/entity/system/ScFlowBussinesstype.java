package com.hjy.entity.system;

public class ScFlowBussinesstype {
    private Integer zid;

    private String uid;

    private String flowType;

    private String tableName;

    private String columnName;

    private String isCommonlog;

    private String creatorColumnname;

    private String createtimeColumnname;

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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? null : columnName.trim();
    }

    public String getIsCommonlog() {
        return isCommonlog;
    }

    public void setIsCommonlog(String isCommonlog) {
        this.isCommonlog = isCommonlog == null ? null : isCommonlog.trim();
    }

    public String getCreatorColumnname() {
        return creatorColumnname;
    }

    public void setCreatorColumnname(String creatorColumnname) {
        this.creatorColumnname = creatorColumnname == null ? null : creatorColumnname.trim();
    }

    public String getCreatetimeColumnname() {
        return createtimeColumnname;
    }

    public void setCreatetimeColumnname(String createtimeColumnname) {
        this.createtimeColumnname = createtimeColumnname == null ? null : createtimeColumnname.trim();
    }
}