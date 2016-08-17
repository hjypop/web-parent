package com.hjy.dto.webcore;

import com.hjy.dto.PageDto;

public class WcSellerinfoDto extends PageDto {

	/**
	 * 商家编号
	 */
	private String sellerCode;
	/**
	 * 商家名称
	 */
	private String sellerName;

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

}
