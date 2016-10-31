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
	
	private String transNum;  // 支付网关订单号  oc_payment_paygate -> c_transnum
	private BigDecimal transportMoney; //  运费 oc_orderinfo-> transport_money
	private String authName;  // 支付人姓名  oc_orderadress -> auth_true_name 
	private String idcard;  // 支付人的身份证编号 oc_orderadress -> auth_idcard_number
	private String payGate; // 支付网关类型 oc_payment_paygate -> c_paygate
	
	
    
    public String getPayGate() {
		return payGate;
	}
	public void setPayGate(String payGate) {
		this.payGate = payGate;
	}
	public String getTransNum() {
		return transNum;
	}
	public void setTransNum(String transNum) {
		this.transNum = transNum;
	}
	public BigDecimal getTransportMoney() {
		return transportMoney;
	}
	public void setTransportMoney(BigDecimal transportMoney) {
		this.transportMoney = transportMoney;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
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
