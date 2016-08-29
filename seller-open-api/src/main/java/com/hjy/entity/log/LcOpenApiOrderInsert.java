package com.hjy.entity.log;

import java.util.Date;

public class LcOpenApiOrderInsert {
    private Integer zid;

    private String sellerCode;

    private Integer flag;

    private Date createTime;

    private String insertJson;

    private String remark;

    
    
    
    
    public LcOpenApiOrderInsert() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LcOpenApiOrderInsert(String sellerCode, Integer flag, Date createTime, String insertJson, String remark) {
		this.sellerCode = sellerCode;
		this.flag = flag;
		this.createTime = createTime;
		this.insertJson = insertJson;
		this.remark = remark;
	}

	public Integer getZid() {
        return zid;
    }

    public void setZid(Integer zid) {
        this.zid = zid;
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode == null ? null : sellerCode.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getInsertJson() {
        return insertJson;
    }

    public void setInsertJson(String insertJson) {
        this.insertJson = insertJson == null ? null : insertJson.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
}