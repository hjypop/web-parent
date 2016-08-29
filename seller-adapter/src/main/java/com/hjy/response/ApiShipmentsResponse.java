package com.hjy.response;

public class ApiShipmentsResponse {
	private String orderCode;
	private String logisticseCode;
	private String logisticseName;
	private String waybill;
	private String createTime;
	private String remark;
	
	
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getLogisticseCode() {
		return logisticseCode;
	}
	public void setLogisticseCode(String logisticseCode) {
		this.logisticseCode = logisticseCode;
	}
	public String getLogisticseName() {
		return logisticseName;
	}
	public void setLogisticseName(String logisticseName) {
		this.logisticseName = logisticseName;
	}
	public String getWaybill() {
		return waybill;
	}
	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
