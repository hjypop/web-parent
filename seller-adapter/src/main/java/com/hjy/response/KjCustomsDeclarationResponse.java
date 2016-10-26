package com.hjy.response;

import java.math.BigDecimal;

public class KjCustomsDeclarationResponse {
	private String sellerCode;
	private String sellerName;
	private String orderCode;
	private String bigOrderCode;
	private BigDecimal dueMoney;
	private String bankOrderId;
	private BigDecimal orderAmount;
	private Integer moneyType;
	
	
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getBigOrderCode() {
		return bigOrderCode;
	}
	public void setBigOrderCode(String bigOrderCode) {
		this.bigOrderCode = bigOrderCode;
	}
	public String getBankOrderId() {
		return bankOrderId;
	}
	public void setBankOrderId(String bankOrderId) {
		this.bankOrderId = bankOrderId;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Integer getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(Integer moneyType) {
		this.moneyType = moneyType;
	}
	public BigDecimal getDueMoney() {
		return dueMoney;
	}
	public void setDueMoney(BigDecimal dueMoney) {
		this.dueMoney = dueMoney;
	}
	
	
}
