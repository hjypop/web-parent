package com.hjy.entity.log;

import java.util.Date;

public class LcOpenApiOrderStatus {
	private Integer zid;

	private String sellerCode;

	private String orderCode;

	private String orderStatus;

	private Date createTime;

	private String remark;

	
	
	
	public LcOpenApiOrderStatus() {
	}

	public LcOpenApiOrderStatus(String sellerCode, String orderCode, String orderStatus, Date createTime, String remark) {
		this.sellerCode = sellerCode;
		this.orderCode = orderCode;
		this.orderStatus = orderStatus;
		this.createTime = createTime;
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode == null ? null : orderCode.trim();
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus == null ? null : orderStatus.trim();
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
}
