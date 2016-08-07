package com.hjy.entity.log;

import java.util.Date;

public class LcOpenApiOperation {
    private Integer zid;

    private String uid;

    private String sellerCode;

    private String apiName;

    private String classUrl;

    private String requestJson;
    
    private String responseJson;
    
    private Date createTime;

    private String remark;
    
    

    public LcOpenApiOperation() {
		super();
		// TODO Auto-generated constructor stub
	}

    
    
	public LcOpenApiOperation(String uid, String sellerCode, String apiName, String classUrl, String requestJson,
			String responseJson, Date createTime, String remark) {
		super();
		this.uid = uid;
		this.sellerCode = sellerCode;
		this.apiName = apiName;
		this.classUrl = classUrl;
		this.requestJson = requestJson;
		this.responseJson = responseJson;
		this.createTime = createTime;
		this.remark = remark;
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

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode == null ? null : sellerCode.trim();
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName == null ? null : apiName.trim();
    }

    public String getClassUrl() {
        return classUrl;
    }

    public void setClassUrl(String classUrl) {
        this.classUrl = classUrl == null ? null : classUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson == null ? null : requestJson.trim();
    }

    public String getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson == null ? null : responseJson.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}