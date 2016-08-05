package com.hjy.request.data;

import com.hjy.request.ApiRequest;

/**
 * @descriptions ApiOrderInfoController.getOrderInfo的请求类
 * 
 * @date 2016年8月3日上午11:34:56
 * @author Yangcl
 * @version 1.0.1
 */
public class OrderInfoRequest extends ApiRequest {
	
	private String orderCode; 		// 订单编号


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
	private String orderStatus; 	// 订单状态 
	private String sellerCode; 		// 商家编码| 非空
	private String sellerKey  ;   	// 商家秘钥| 非空
	
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
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public String getSellerKey() {
		return sellerKey;
	}
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}
	 
}












