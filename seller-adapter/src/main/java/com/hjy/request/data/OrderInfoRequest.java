package com.hjy.request.data;

import com.hjy.request.ApiRequest;

/**
 * @descriptions ApiOrderInfoController.getOrderInfo的请求类
 * 
 * @date 2016年8月3日上午11:34:56
 * @author Yangcl
 * @version 1.0.1
 */
public class OrderInfoRequest {
	
	private String orderCode; 		// 订单编号
	private String startTime; // 订单表create_time的区间段
	private String endTime; // 订单表create_time的区间段
	
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

	/**
	 * 4497153900010001 下单成功-未付款 
	 * 4497153900010002 下单成功-未发货 
	 * 4497153900010003 已发货      
	 * 4497153900010004 已收货
	 * 4497153900010005 交易成功	
	 * 
	 * 4497153900010006 交易失败 
	 * 4497153900010007 交易无效
	 */
//	private String orderStatus;  // 订单状态      此字段保留          
	
	 
	
	
	
	 
}












