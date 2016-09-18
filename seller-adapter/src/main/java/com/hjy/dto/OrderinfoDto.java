package com.hjy.dto;

/**
 * 
 * @title: com.hjy.dto.OrderinfoDto.java 
 * @description: oc_orderinfo表的数据查询模型，此类可以随意添加辅助查询字段，但不要删除字段
 *
 * @author Yangcl
 * @date 2016年9月18日 下午4:45:39 
 * @version 1.0.0
 */
public class OrderinfoDto {
	private String smallSellerCode;
	private String startTime;
	private String endTime;
	
	


	public OrderinfoDto() {
	}


	/**
	 * @description:  此构造函数用于 JobForKjSellerOrder.java 的数据查询模型封装
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月18日 下午4:47:53 
	 * @version 1.0.0.1
	 */
	public OrderinfoDto(String smallSellerCode, String startTime, String endTime) {
		this.smallSellerCode = smallSellerCode;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	
	
	
	
	public String getSmallSellerCode() {
		return smallSellerCode;
	}

	public void setSmallSellerCode(String smallSellerCode) {
		this.smallSellerCode = smallSellerCode;
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
