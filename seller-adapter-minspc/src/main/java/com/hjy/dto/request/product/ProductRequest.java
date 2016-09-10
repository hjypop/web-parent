package com.hjy.dto.request.product;

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
	private String status;
	private String startTime;
	private String endTime;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
