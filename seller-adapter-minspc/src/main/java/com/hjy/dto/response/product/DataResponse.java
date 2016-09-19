package com.hjy.dto.response.product;

import java.util.List;

public class DataResponse {
	private String Code;
	private String Desc;
	private List<Product> ProductList;
	
	
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	public List<Product> getProductList() {
		return ProductList;
	}
	public void setProductList(List<Product> productList) {
		ProductList = productList;
	}
	
	
	
}
