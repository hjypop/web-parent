package com.hjy.dto.response.orderStatus;


/**
 * @descriptions minspc响应数据报文实体类
 *
 * @date 2016年9月12日 下午9:40:01
 * @author Yangcl 
 * @version 1.0.1
 */
public class DataResponse {
	
	private String OrderID; // 民生品翠 系统订单号
	private String SoStatus; // 还未出关：0 |无对应订单：-1  已出关：1
	private String ShipTypeID ; // 快递公司编号（可选）
	private String ExpressBill; // 快递单号（当SoStatus为1时返回）
	
	
	public String getOrderID() {
		return OrderID;
	}
	public void setOrderID(String orderID) {
		OrderID = orderID;
	}
	public String getSoStatus() {
		return SoStatus;
	}
	public void setSoStatus(String soStatus) {
		SoStatus = soStatus;
	}
	public String getShipTypeID() {
		return ShipTypeID;
	}
	public void setShipTypeID(String shipTypeID) {
		ShipTypeID = shipTypeID;
	}
	public String getExpressBill() {
		return ExpressBill;
	}
	public void setExpressBill(String expressBill) {
		ExpressBill = expressBill;
	}
	
	
	
	
}
