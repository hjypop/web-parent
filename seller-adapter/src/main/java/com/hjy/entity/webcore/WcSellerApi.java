package com.hjy.entity.webcore;

import java.util.Date;

import com.hjy.base.BaseModel;
/**
 * 
 * 类: WcSellerApi <br>
 * 描述: 商户接口关系表 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月25日 上午9:16:06
 */
public class WcSellerApi extends BaseModel {

	private String sellerCode;
	private String apiCode;
	private String creator;
	private Date createTime;

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getApiCode() {
		return apiCode;
	}

	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}