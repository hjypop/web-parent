package com.hjy.request.data;

public class OrderInfoRequestDto {
	
	private String sellerCode; 		// 商家编码| 非空
	private String orderCode; 		// 订单编号

	private String startTime;
	
	private String endTime;

	
	
	public OrderInfoRequestDto(String sellerCode, String orderCode, String startTime, String endTime) {
		this.sellerCode = sellerCode;
		this.orderCode = orderCode;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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
