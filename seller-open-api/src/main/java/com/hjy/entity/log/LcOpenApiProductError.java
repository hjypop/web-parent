package com.hjy.entity.log;

import java.util.Date;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: LcOpenApiProductError <br>
 * 描述: openapi产品错误日志表 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月11日 上午11:06:57
 */
public class LcOpenApiProductError extends BaseModel {
	/**
	 * openapi调用方法
	 */
	private String method;

	/**
	 * 外部商品编码
	 */
	private String productOutCode;
	/**
	 * 商家编码
	 */
	private String sellerCode;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 商品信息json存储
	 */
	private String productData;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getProductOutCode() {
		return productOutCode;
	}

	public void setProductOutCode(String productOutCode) {
		this.productOutCode = productOutCode;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getProductData() {
		return productData;
	}

	public void setProductData(String productData) {
		this.productData = productData;
	}

}