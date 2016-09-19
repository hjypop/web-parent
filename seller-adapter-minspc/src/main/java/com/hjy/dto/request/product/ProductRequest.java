package com.hjy.dto.request.product;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @descriptions RsyncProductList.java类请求报文封装体
 *
 * @date 2016年9月10日 下午9:25:46
 * @author Yangcl 
 * @version 1.0.1
 */
public class ProductRequest {
	
	//	Status：-1 （当前有库存的全部订阅产品列表）
	//	Status：0 （当前有库存的保税贸易订阅产品列表）
	//	Status：1 （当前有库存的海外直邮订阅产品列表）
	//	Status：2 （当前有库存的一般贸易订阅产品列表）
	private String Status;
	private String StartTime;
	private String EndTime;
	
	@JSONField(name = "Status")
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	
	@JSONField(name = "StartTime")
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	
	@JSONField(name = "EndTime") 
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	
	
}
