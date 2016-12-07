package com.hjy.dto.order;

/**
 * @description: 描述open-api里的订单信息中的发票信息
 * 
 * @author Yangcl
 * @date 2016年12月7日 下午1:45:55 
 * @version 1.0.0
 */
public class BillInfo {
	private String bill_Type;  //		发票类型	发票类型 449746310001:普通发票
	private String bill_detail	;  // 发票内容
	private String bill_title;  // 发票抬头 
	
	public String getBill_Type() {
		return bill_Type;
	}
	public void setBill_Type(String bill_Type) {
		this.bill_Type = bill_Type;
	}
	public String getBill_detail() {
		return bill_detail;
	}
	public void setBill_detail(String bill_detail) {
		this.bill_detail = bill_detail;
	}
	public String getBill_title() {
		return bill_title;
	}
	public void setBill_title(String bill_title) {
		this.bill_title = bill_title;
	}
}
