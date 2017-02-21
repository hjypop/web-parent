package com.hjy.entity.user;

import com.hjy.base.BaseModel;

/**
 * ClassName: UcSellerInfo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2013年9月23日 下午2:56:34 <br/>
 * @author hxd
 * @version 
 * @since JDK 1.6
 */
public class UcSellerinfo extends BaseModel{
	
	private String sellerCode;		// 卖家所属平台编号 SI2003
	private String smallSellerCode; // 小商户编号
	private String sellerName;   // 卖家名称
	private String sellerStatus; // 商家状态    终审通过:4497172300040004
	private String flowStatus; //	合同主管审批通过:4497172300140005
	
	/**
	 * 卖家logo
	 */
	private String sellerPic ;

	public String getSmallSellerCode() {
		return smallSellerCode;
	}

	public void setSmallSellerCode(String smallSellerCode) {
		this.smallSellerCode = smallSellerCode;
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

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerPic() {
		return sellerPic;
	}

	public void setSellerPic(String sellerPic) {
		this.sellerPic = sellerPic;
	}
	
	
	
}
