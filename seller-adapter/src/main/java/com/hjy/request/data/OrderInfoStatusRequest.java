package com.hjy.request.data;

import java.util.List;

import com.hjy.request.ApiRequest;

public class OrderInfoStatusRequest extends ApiRequest {

	private List< OrderInfoStatus> list; // 订单对应物流信息集合

	private String sellerCode; // 商家编码| 非空 对应 oc_order_shipments表中 creator 字段
	private String sellerKey; // 商家秘钥| 非空
	
	
	public List<OrderInfoStatus> getList() {
		return list;
	}
	public void setList(List<OrderInfoStatus> list) {
		this.list = list;
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
