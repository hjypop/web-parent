package com.hjy.selleradapter.kjt.model;

import java.math.BigDecimal;

public class SOPayInfo {

	private BigDecimal ProductAmount;
	private BigDecimal ShippingAmount;
	private BigDecimal TaxAmount;
	private BigDecimal CommissionAmount;
	private long PayTypeSysNo;
	private String PaySerialNumber;
	
	
	
	public SOPayInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SOPayInfo(BigDecimal productAmount, BigDecimal shippingAmount,
			BigDecimal taxAmount, BigDecimal commissionAmount,
			long payTypeSysNo, String paySerialNumber) {
		super();
		ProductAmount = productAmount;
		ShippingAmount = shippingAmount;
		TaxAmount = taxAmount;
		CommissionAmount = commissionAmount;
		PayTypeSysNo = payTypeSysNo;
		PaySerialNumber = paySerialNumber;
	}
	
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
	public long getPayTypeSysNo() {
		return PayTypeSysNo;
	}
	public void setPayTypeSysNo(long payTypeSysNo) {
		PayTypeSysNo = payTypeSysNo;
	}
	public String getPaySerialNumber() {
		return PaySerialNumber;
	}
	public void setPaySerialNumber(String paySerialNumber) {
		PaySerialNumber = paySerialNumber;
	}
	
	
}
