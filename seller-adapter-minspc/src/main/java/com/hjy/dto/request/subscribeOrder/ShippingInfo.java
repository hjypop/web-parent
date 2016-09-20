package com.hjy.dto.request.subscribeOrder;

import com.alibaba.fastjson.annotation.JSONField;

public class ShippingInfo {
	
	private String ReceiveName;
	private String ReceivePhone;
	private String ReceiveAddress;
	private String ReceiveAreaCode;
	private String ShipTypeID;
	private String ReceiveAreaName;
	
	@JSONField(name = "ReceiveName")
	public String getReceiveName() {
		return ReceiveName;
	}
	public void setReceiveName(String receiveName) {
		ReceiveName = receiveName;
	}
	
	@JSONField(name = "ReceivePhone")
	public String getReceivePhone() {
		return ReceivePhone;
	}
	public void setReceivePhone(String receivePhone) {
		ReceivePhone = receivePhone;
	}
	
	@JSONField(name = "ReceiveAddress")
	public String getReceiveAddress() {
		return ReceiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		ReceiveAddress = receiveAddress;
	}
	
	@JSONField(name = "ReceiveAreaCode")
	public String getReceiveAreaCode() {
		return ReceiveAreaCode;
	}
	public void setReceiveAreaCode(String receiveAreaCode) {
		ReceiveAreaCode = receiveAreaCode;
	}
	
	@JSONField(name = "ShipTypeID")
	public String getShipTypeID() {
		return ShipTypeID;
	}
	public void setShipTypeID(String shipTypeID) {
		ShipTypeID = shipTypeID;
	}
	
	@JSONField(name = "ReceiveAreaName")
	public String getReceiveAreaName() {
		return ReceiveAreaName;
	}
	public void setReceiveAreaName(String receiveAreaName) {
		ReceiveAreaName = receiveAreaName;
	}
}





















