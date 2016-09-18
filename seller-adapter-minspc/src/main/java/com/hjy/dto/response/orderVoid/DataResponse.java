package com.hjy.dto.response.orderVoid;


/**
 * @descriptions minspc响应数据报文实体类
 *
 * @date 2016年9月12日 下午9:40:01
 * @author Yangcl 
 * @version 1.0.1
 */
public class DataResponse {
	
	private String OrderID; 	// 民生品翠 系统订单号
	private String Status; 		// “0”表示作废成功，”-1”作废失败
	private String Message ; // 作废结果信息
	
	/** 例如返回信息：
		 {
		    "Code": "0",
		    "Desc": "SUCCESS",
		    "Data": [
		        {
		            "OrderID": "12849977",
		            " Status": "-1",
		            "Message": "订单已出库，不允许取消！"
		        }
		    ]
		 }
	 */

	
	
	public String getOrderID() {
		return OrderID;
	}
	public void setOrderID(String orderID) {
		OrderID = orderID;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	
	
	
	
}
