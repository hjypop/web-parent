package com.hjy.dto.minspc;

import java.math.BigDecimal;

/**
 * @description: com.hjy.job.JobForCreateSubscribeOrder.java 使用
 * 	|拼装请求数据的商品列表部分|seller-adapter-minspc(民生品粹项目)
 * 
 * @author Yangcl
 * @date 2016年9月6日 下午2:24:05 
 */
public class MinspcOrderdetailOne {
	private String productID;
	private int quantity;
	private BigDecimal salePrice;
	private BigDecimal taxRate;
	
	private String pcode;
	private String skuCode;
	private String skuName;
	
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
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
	
	
}
