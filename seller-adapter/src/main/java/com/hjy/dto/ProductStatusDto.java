package com.hjy.dto;

import java.util.List;

public class ProductStatusDto {
	private String productStatus;  // 商品上下架状态 
	private List<String> list;    // product code list
	
	
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	
	
}
