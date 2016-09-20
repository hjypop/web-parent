package com.hjy.dto.request.subscribeOrder;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

public class PayInfo {
	
	private BigDecimal ProductAmount;
	private BigDecimal ShippingAmount;
	private BigDecimal TaxAmount;
	private BigDecimal CommissionAmount;
	private int PayTypeSysNo;
	private String PaySerialNumber;
	
	@JSONField(name = "ProductAmount")
	public BigDecimal getProductAmount() {
		return ProductAmount;
	}
	public void setProductAmount(BigDecimal productAmount) {
		ProductAmount = productAmount;
	}
	
	@JSONField(name = "ShippingAmount")
	public BigDecimal getShippingAmount() {
		return ShippingAmount;
	}
	public void setShippingAmount(BigDecimal shippingAmount) {
		ShippingAmount = shippingAmount;
	}
	
	@JSONField(name = "TaxAmount")
	public BigDecimal getTaxAmount() {
		return TaxAmount;
	}
	public void setTaxAmount(BigDecimal taxAmount) {
		TaxAmount = taxAmount;
	}
	
	@JSONField(name = "CommissionAmount")
	public BigDecimal getCommissionAmount() {
		return CommissionAmount;
	}
	public void setCommissionAmount(BigDecimal commissionAmount) {
		CommissionAmount = commissionAmount;
	}
	
	@JSONField(name = "PayTypeSysNo")
	public int getPayTypeSysNo() {
		return PayTypeSysNo;
	}
	public void setPayTypeSysNo(int payTypeSysNo) {
		PayTypeSysNo = payTypeSysNo;
	}
	
	@JSONField(name = "PaySerialNumber")
	public String getPaySerialNumber() {
		return PaySerialNumber;
	}
	public void setPaySerialNumber(String paySerialNumber) {
		PaySerialNumber = paySerialNumber;
	}
	
}





















