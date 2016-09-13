package com.hjy.dto.response.subscribeOrder;

/**
 * @descriptions minspc响应数据报文实体类
 *
 * @date 2016年9月12日 下午9:40:01
 * @author Yangcl 
 * @version 1.0.1
 */
public class DataResponse {
	
	private String ProductID; // // 民生品翠 product_code
	private String MerchantOrderID; // 惠家有订单号
	private String SOSysNo; // 民生品翠 系统订单号
	private String OrderType; // 订单类型，0为保税贸易订单，1为直邮贸易订单，2为一般贸易订单（如果一笔订单内含不同类型的产品，会对应拆单返回多个信息）
	
	
	
	public String getProductID() {
		return ProductID;
	}
	public void setProductID(String productID) {
		ProductID = productID;
	}
	public String getMerchantOrderID() {
		return MerchantOrderID;
	}
	public void setMerchantOrderID(String merchantOrderID) {
		MerchantOrderID = merchantOrderID;
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
