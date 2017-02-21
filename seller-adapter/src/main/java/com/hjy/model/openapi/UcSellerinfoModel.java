package com.hjy.model.openapi;

/**
 * @descriptions 返回查询视图
 *
 * @author Yangcl 
 * @date 2017年2月21日 下午9:27:43
 * @version 1.0.1
 */
public class UcSellerinfoModel {

	//	UcSellerinfo 表主要字段
	private String sellerCode;		// 卖家所属平台编号 SI2003
	private String smallSellerCode; // 小商户编号
	private String sellerName;   // 卖家名称
	private String sellerStatus; // 商家状态    终审通过:4497172300040004
	private String flowStatus; //	合同主管审批通过:4497172300140005
	
	// 依赖字段如下
	private String businessScope; // 经营范围
	private String ucSellerType; // 商户类型 449747810005000* : LD系统0  普通商户1  跨境商户2  跨境直邮3  平台入驻4
	
	
	
	
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public String getSmallSellerCode() {
		return smallSellerCode;
	}
	public void setSmallSellerCode(String smallSellerCode) {
		this.smallSellerCode = smallSellerCode;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerStatus() {
		return sellerStatus;
	}
	public void setSellerStatus(String sellerStatus) {
		this.sellerStatus = sellerStatus;
	}
	public String getFlowStatus() {
		return flowStatus;
	}
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}
	public String getBusinessScope() {
		return businessScope;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	public String getUcSellerType() {
		return ucSellerType;
	}
	public void setUcSellerType(String ucSellerType) {
		this.ucSellerType = ucSellerType;
	}
	
	
	
}
