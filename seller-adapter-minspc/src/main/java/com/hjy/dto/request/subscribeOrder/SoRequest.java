package com.hjy.dto.request.subscribeOrder;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @title: com.hjy.dto.request.subscribeOrder.SoRequest.java 
 * @description: 请求minspc的实体封装类
 *
 * @author Yangcl
 * @date 2016年9月7日 下午3:32:25 
 * @version 1.0.0
 */
public class SoRequest {
	
	private String MerchantOrderID;
	private PayInfo PayInfo;
	private ShippingInfo ShippingInfo;
	private AuthenticationInfo AuthenticationInfo;
	private List<Item> ItemList ;
	
	@JSONField(name = "MerchantOrderID")
	public String getMerchantOrderID() {
		return MerchantOrderID;
	}
	public void setMerchantOrderID(String merchantOrderID) {
		MerchantOrderID = merchantOrderID;
	}
	
	@JSONField(name = "PayInfo")
	public PayInfo getPayInfo() {
		return PayInfo;
	}
	public void setPayInfo(PayInfo payInfo) {
		PayInfo = payInfo;
	}
	
	@JSONField(name = "ShippingInfo")
	public ShippingInfo getShippingInfo() {
		return ShippingInfo;
	}
	public void setShippingInfo(ShippingInfo shippingInfo) {
		ShippingInfo = shippingInfo;
	}
	
	@JSONField(name = "AuthenticationInfo")
	public AuthenticationInfo getAuthenticationInfo() {
		return AuthenticationInfo;
	}
	public void setAuthenticationInfo(AuthenticationInfo authenticationInfo) {
		AuthenticationInfo = authenticationInfo;
	}
	
	@JSONField(name = "ItemList")
	public List<Item> getItemList() {
		return ItemList;
	}
	public void setItemList(List<Item> itemList) {
		ItemList = itemList;
	}
	
	
}
