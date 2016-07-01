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
public class UcSellerInfo extends BaseModel{
	/**
	 * 卖家编号
	 */
	private String sellerCode ;
	/**
	 * 卖家名称
	 */
	private String sellerName;
	
	/**
	 * 卖家logo
	 */
	private String sellerPic="";

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
