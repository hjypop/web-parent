package com.hjy.entity.user;

import com.hjy.base.BaseModel;

public class UcSellercategoryProductRelation extends BaseModel {

	private String productCode;
	private String categoryCode;
	private String sellerCode;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

}
