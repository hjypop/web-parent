package com.hjy.dto.request.subscribeOrder;

import java.math.BigDecimal;

public class Item {
	
	private String ProductID;
	private int Quantity;
	private BigDecimal SalePrice;
	private BigDecimal TaxPrice;
	
	
	public String getProductID() {
		return ProductID;
	}
	public void setProductID(String productID) {
		ProductID = productID;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
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
