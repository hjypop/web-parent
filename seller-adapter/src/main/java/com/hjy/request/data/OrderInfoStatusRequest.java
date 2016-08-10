package com.hjy.request.data;

import java.util.List;

import com.hjy.request.ApiRequest;

public class OrderInfoStatusRequest extends ApiRequest {

	private List< OrderInfoStatus> list; // 订单对应物流信息集合
	
	
	public List<OrderInfoStatus> getList() {
		return list;
	}
	public void setList(List<OrderInfoStatus> list) {
		this.list = list;
	}
	
	
}
