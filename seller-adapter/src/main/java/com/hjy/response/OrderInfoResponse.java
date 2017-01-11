package com.hjy.response;

import java.math.BigDecimal;


/**
 * @descriptions 订单查询所响应的实体类
 * 
 * @date 2016年8月4日下午3:19:30
 * @author Yangcl
 * @version 1.0.1
 */
public class OrderInfoResponse {
	private String orderCode;
	private String productCode;   // 商家的产品编号
	private String productName;
	private BigDecimal transportMoney;   // 运费
	private BigDecimal orderMoney;
	private BigDecimal payedMoney;
	private String skuCode;   // SKU编码
	private String skuName;
	private BigDecimal skuPrice;  // SKU价格
	private Integer skuNum;   // SKU数量
	private String address;
	private String postcode; 
	private String remark;
	private String receivePerson;  // 收件人姓名  
	private String authName;        // 真实姓名
	private String authIdcard;		// 身份证编号|如果订单包含跨境商品
	private String mobile;           // 手机号 
	
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public BigDecimal getTransportMoney() {
		return transportMoney;
	}
	public void setTransportMoney(BigDecimal transportMoney) {
		this.transportMoney = transportMoney;
	}
	public BigDecimal getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}
	public BigDecimal getPayedMoney() {
		return payedMoney;
	}
	public void setPayedMoney(BigDecimal payedMoney) {
		this.payedMoney = payedMoney;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public BigDecimal getSkuPrice() {
		return skuPrice;
	}
	public void setSkuPrice(BigDecimal skuPrice) {
		this.skuPrice = skuPrice;
	}
	public Integer getSkuNum() {
		return skuNum;
	}
	public void setSkuNum(Integer skuNum) {
		this.skuNum = skuNum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReceivePerson() {
		return receivePerson;
	}
	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public String getAuthIdcard() {
		return authIdcard;
	}
	public void setAuthIdcard(String authIdcard) {
		this.authIdcard = authIdcard;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}














