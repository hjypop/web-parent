package com.hjy.dto.request.subscribeOrder;

import java.math.BigDecimal;

public class PayInfo {
	
	private BigDecimal ProductAmount;
	private BigDecimal ShippingAmount;
	private BigDecimal TaxAmount;
	private BigDecimal CommissionAmount;
	private int PayTypeSysNo;
	private String PaySerialNumber;
	
	
	public BigDecimal getProductAmount() {
		return ProductAmount;
	}
	public void setProductAmount(BigDecimal productAmount) {
		ProductAmount = productAmount;
	}
	public BigDecimal getShippingAmount() {
		return ShippingAmount;
	}
	public void setShippingAmount(BigDecimal shippingAmount) {
		ShippingAmount = shippingAmount;
	}
	public BigDecimal getTaxAmount() {
		return TaxAmount;
	}
	public void setTaxAmount(BigDecimal taxAmount) {
		TaxAmount = taxAmount;
	}
	public BigDecimal getCommissionAmount() {
		return CommissionAmount;
	}
	public void setCommissionAmount(BigDecimal commissionAmount) {
		CommissionAmount = commissionAmount;
	}
	public int getPayTypeSysNo() {
		return PayTypeSysNo;
	}
	public void setPayTypeSysNo(int payTypeSysNo) {
		PayTypeSysNo = payTypeSysNo;
	}
	public String getPaySerialNumber() {
		return PaySerialNumber;
	}
	public void setPaySerialNumber(String paySerialNumber) {
		PaySerialNumber = paySerialNumber;
	}
	
}





















