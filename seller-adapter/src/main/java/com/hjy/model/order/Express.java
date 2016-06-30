package com.hjy.model.order;


/**
 * 快递
 * @author wangkecheng
 *
 */
public class Express  {
	


	/**
	 * 快递状态描述
	 */
	private String context = "";
	
	
	/**
	 * 处理时间
	 */
	private String time = "";
	
	
	
	/**
	 * 订单编号
	 */
	private String orderCode ="";
	
	
	/**
	 * 配送商编号
	 */
	private String logisticseCode="";
	
	
	/**
	 * 配送商名字
	 */
	private String logisticseName="";
	
	
	/**
	 * 运单号
	 */
	private String waybill = "";
	
	

	
	
	

	public String getLogisticseName() {
		return logisticseName;
	}


	public void setLogisticseName(String logisticseName) {
		this.logisticseName = logisticseName;
	}


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


	public String getWaybill() {
		return waybill;
	}


	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}


	public String getContext() {
		return context;
	}


	public void setContext(String context) {
		this.context = context;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}
	
	
}
