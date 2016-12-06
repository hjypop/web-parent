package com.hjy.dto.product;

/**
 * @description: 返回商品状态变化的信息 
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年9月30日 下午2:26:38 
 * @version 1.0.0
 */
public class ProductStatus {

	private String productCode;
	private String productStatus;
	private String skuCode; 
	private String saleYn;  // 是否可卖 Y可卖 N不可卖
	
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getSaleYn() {
		return saleYn;
	}
	public void setSaleYn(String saleYn) {
		this.saleYn = saleYn;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	
}
