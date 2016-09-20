package com.hjy.dto.request.subscribeOrder;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

public class Item {
	
	private String ProductID;
	private int Quantity;
	private BigDecimal SalePrice;
	private BigDecimal TaxPrice;
	
	@JSONField(name = "ProductID")
	public String getProductID() {
		return ProductID;
	}
	public void setProductID(String productID) {
		ProductID = productID;
	}
	
	@JSONField(name = "Quantity")
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	
	@JSONField(name = "SalePrice")
	public BigDecimal getSalePrice() {
		return SalePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		SalePrice = salePrice;
	}
	
	@JSONField(name = "TaxPrice")
	public BigDecimal getTaxPrice() {
		return TaxPrice;
	}
	public void setTaxPrice(BigDecimal taxPrice) {
		TaxPrice = taxPrice;
	}
	
	
}
