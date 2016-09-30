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
	private String productCodeOld;
	private String productStatus;
	
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductCodeOld() {
		return productCodeOld;
	}
	public void setProductCodeOld(String productCodeOld) {
		this.productCodeOld = productCodeOld;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	
}
