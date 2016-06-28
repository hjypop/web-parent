package com.hjy.entity;

import java.math.BigDecimal;

/**
 * 
 * 类: OcOrderKjtList <br>
 * 描述: oc_order_kjt_list表对象 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午4:41:40
 */
public class OcOrderKjtList {

	private Integer zid;
	private String uid;
	private String orderCodeSeq;
	private String orderCode;
	private String orderCodeOut;
	private BigDecimal productAmount;
	private BigDecimal taxAmount;
	private BigDecimal shippingAmount;
	private String createTime;
	private String updateTime;
	private String sostatus;
	private String localStatus;
	private String rsyncDesc;

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

	public String getOrderCodeSeq() {
		return orderCodeSeq;
	}

	public void setOrderCodeSeq(String orderCodeSeq) {
		this.orderCodeSeq = orderCodeSeq;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderCodeOut() {
		return orderCodeOut;
	}

	public void setOrderCodeOut(String orderCodeOut) {
		this.orderCodeOut = orderCodeOut;
	}

	public BigDecimal getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getShippingAmount() {
		return shippingAmount;
	}

	public void setShippingAmount(BigDecimal shippingAmount) {
		this.shippingAmount = shippingAmount;
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

	public String getSostatus() {
		return sostatus;
	}

	public void setSostatus(String sostatus) {
		this.sostatus = sostatus;
	}

	public String getLocalStatus() {
		return localStatus;
	}

	public void setLocalStatus(String localStatus) {
		this.localStatus = localStatus;
	}

	public String getRsyncDesc() {
		return rsyncDesc;
	}

	public void setRsyncDesc(String rsyncDesc) {
		this.rsyncDesc = rsyncDesc;
	}

}
