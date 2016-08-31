package com.hjy.request.data;

import com.hjy.annotation.ExculdeNullField;

public class OrderShipmentsRequest {
	
	@ExculdeNullField
	private String sellerCode; 		// 商家编码
	
	private String startTime;
	private String endTime;
	
	
	
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
