package com.hjy.entity;

import java.util.Date;

/**
 * @descriptions 用于记录【Subscribe.ProductList】接口的请求日志信息。
 * 							  处理获取的订阅产品，记录到数据库日志。 
 * 							  RsyncProductList.java中使用此类。
 *
 * @date 2016年9月10日 下午9:53:15
 * @author Yangcl 
 * @version 1.0.1
 */
public class LcRsyncMinspcProduct {
    private Integer zid;

    private Date createTime;

    private String remark;
    
    private String responseJson;

    private String successList;

    private String errorList;
    
    
    public LcRsyncMinspcProduct(){
    	
    }

    public LcRsyncMinspcProduct(String remark, String responseJson, String successList, String errorList) {
		this.createTime = new Date();
		this.remark = remark;
		this.responseJson = responseJson;
		this.successList = successList;
		this.errorList = errorList;
	}

	public Integer getZid() {
        return zid;
    }

    public void setZid(Integer zid) {
        this.zid = zid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    

    public String getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson == null ? null : responseJson.trim();
    }

    public String getSuccessList() {
        return successList;
    }

    public void setSuccessList(String successList) {
        this.successList = successList == null ? null : successList.trim();
    }

    public String getErrorList() {
        return errorList;
    }

    public void setErrorList(String errorList) {
        this.errorList = errorList == null ? null : errorList.trim();
    }
}