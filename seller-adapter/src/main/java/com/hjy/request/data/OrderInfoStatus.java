package com.hjy.request.data;

/**
 * @descriptions 用于更新状态变更的订单
 * 
 * @date 2016年8月5日下午2:11:11
 * @author Yangcl
 * @version 1.0.1
 */
public class OrderInfoStatus {
	private String orderCode;
	private String orderStatus;
	
	
	
	public OrderInfoStatus(String orderCode, String orderStatus) {
		super();
		this.orderCode = orderCode;
		this.orderStatus = orderStatus;
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
