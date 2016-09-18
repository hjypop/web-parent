package com.hjy.dto.minspc;

/**
 * 
 * @title: com.hjy.dto.minspc.VoidDto.java 
 * @description: 查询作废的订单
 *
 * @author Yangcl
 * @date 2016年9月18日 上午10:55:01 
 * @version 1.0.0
 */
public class VoidDto {
	private String sellerCode; // 商户编码 
	// 商户订单状态，每个商户不同|还未出关，即未发货的订单可以取消
	private String sellerStatus; 
	
	
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public String getSellerStatus() {
		return sellerStatus;
	}
	public void setSellerStatus(String sellerStatus) {
		this.sellerStatus = sellerStatus;
	}
	
	
}
