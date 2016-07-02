package com.hjy.selleradapter.kjt.model;

import java.math.BigDecimal;

public class SOItemInfo {

	private String ProductID;
	private long Quantity;
	private BigDecimal SalePrice;
	private BigDecimal TaxPrice;
	
	
	
	
	public SOItemInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SOItemInfo(String productID, long quantity, BigDecimal salePrice,
			BigDecimal taxPrice) {
		super();
		ProductID = productID;
		Quantity = quantity;
		SalePrice = salePrice;
		TaxPrice = taxPrice;
	}
	public String getProductID() {
		return ProductID;
	}
	public void setProductID(String productID) {
		ProductID = productID;
	}
	public long getQuantity() {
		return Quantity;
	}
	public void setQuantity(long quantity) {
		Quantity = quantity;
	}
	public BigDecimal getSalePrice() {
		return SalePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		SalePrice = salePrice;
	}
	public BigDecimal getTaxPrice() {
		return TaxPrice;
	}
	public void setTaxPrice(BigDecimal taxPrice) {
		TaxPrice = taxPrice;
	}
	
}
