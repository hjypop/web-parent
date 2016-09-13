package com.hjy.dto.request.subscribeOrder;

import java.util.List;
/**
 * 
 * @title: com.hjy.dto.request.subscribeOrder.SeparateOrderRequest.java 
 * @description: 形成拆单后的请求数据报文封装体。
 * 							与SoRequest.java的区别在于将List<Item> 变成 Item 
 *
 * @author Yangcl
 * @date 2016年9月13日 下午6:03:04 
 * @version 1.0.0
 */
public class SeparateOrderDto {
	
	private String MerchantOrderID;
	private PayInfo PayInfo;
	private ShippingInfo ShippingInfo;
	private AuthenticationInfo AuthenticationInfo;
	private Item item ;
	
	private String SOSysNo; // 民生品翠 系统订单号
	private String OrderType; // 订单类型，0为保税贸易订单，1为直邮贸易订单，2为一般贸易订单（如果一笔订单内含不同类型的产品，会对应拆单返回多个信息）
	
	
	public String getMerchantOrderID() {
		return MerchantOrderID;
	}
	public void setMerchantOrderID(String merchantOrderID) {
		MerchantOrderID = merchantOrderID;
	}
	public PayInfo getPayInfo() {
		return PayInfo;
	}
	public void setPayInfo(PayInfo payInfo) {
		PayInfo = payInfo;
	}
	public ShippingInfo getShippingInfo() {
		return ShippingInfo;
	}
	public void setShippingInfo(ShippingInfo shippingInfo) {
		ShippingInfo = shippingInfo;
	}
	public AuthenticationInfo getAuthenticationInfo() {
		return AuthenticationInfo;
	}
	public void setAuthenticationInfo(AuthenticationInfo authenticationInfo) {
		AuthenticationInfo = authenticationInfo;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public String getSOSysNo() {
		return SOSysNo;
	}
	public void setSOSysNo(String sOSysNo) {
		SOSysNo = sOSysNo;
	}
	public String getOrderType() {
		return OrderType;
	}
	public void setOrderType(String orderType) {
		OrderType = orderType;
	}
}












