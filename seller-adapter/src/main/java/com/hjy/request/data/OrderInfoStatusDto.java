package com.hjy.request.data;

/**
 * @descriptions 用于更新状态变更的订单
 * 
 * @date 2016年8月5日下午2:11:11
 * @author Yangcl
 * @version 1.0.1
 */
public class OrderInfoStatusDto {
	private String orderCode;
	private String orderStatus;
	private String updateTime;
	private String sellerCode;
	
	
	public OrderInfoStatusDto(String orderCode, String orderStatus, String updateTime, String sellerCode) {
		this.orderCode = orderCode;
		this.orderStatus = orderStatus;
		this.updateTime = updateTime;
		this.sellerCode = sellerCode;
	}

	
	
	public OrderInfoStatusDto(String orderCode, String sellerCode) {
		super();
		this.orderCode = orderCode;
		this.sellerCode = sellerCode;
	}



	public String getSellerCode() {
		return sellerCode;
	}



	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}



	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
}
