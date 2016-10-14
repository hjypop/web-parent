package com.hjy.request.data;

import java.util.List;


public class OrderShipmentsRequest {
	
	private String sellerCode; 		// 商家编码
	
	private List<String> list; // 外部订单编号：第三方平台的订单编号

	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}

}
