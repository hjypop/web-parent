package com.hjy.entity.webcore;

import java.util.Date;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: WcSellerinfo <br>
 * 描述: 接口商家信息表 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月24日 上午11:27:21
 */
public class WcSellerinfo extends BaseModel {

	/**
	 * 商家编码
	 */
	private String sellerCode;
	/**
	 * 商家名称
	 */
	private String sellerName;
	/**
	 * 商家描述
	 */
	private String sellerDescrption;
	/**
	 * 联系电话
	 */
	private String sellerTelephone;
	/**
	 * 邮箱
	 */
	private String sellerEmail;
	/**
	 * 商户状态 0 未开通 1 已开通 2 已禁用
	 */
	private Integer status;
	/**
	 * 价格类型 0 成本价 1 销售价
	 */
	private Integer priceType;
	/**
	 * 佣金比例 json存储
	 */
	private String commission;
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 修改人
	 */
	private String updator;
	/**
	 * 修改时间
	 */
	private String updateTime;

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

	public String getSellerDescrption() {
		return sellerDescrption;
	}

	public void setSellerDescrption(String sellerDescrption) {
		this.sellerDescrption = sellerDescrption;
	}

	public String getSellerTelephone() {
		return sellerTelephone;
	}

	public void setSellerTelephone(String sellerTelephone) {
		this.sellerTelephone = sellerTelephone;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}