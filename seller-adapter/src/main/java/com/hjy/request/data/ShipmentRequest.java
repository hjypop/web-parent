package com.hjy.request.data;

import java.util.List;

import com.hjy.request.ApiRequest;

public class ShipmentRequest extends ApiRequest {
	
	private List<OrderShipment> list; 		// 订单对应物流信息集合
	
	
	public List<OrderShipment> getList() {
		return list;
	}
	public void setList(List<OrderShipment> list) {
		this.list = list;
	}
	
}
