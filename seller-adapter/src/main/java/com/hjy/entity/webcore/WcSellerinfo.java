package com.hjy.entity.webcore;

import java.util.Date;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: WcSellerinfo <br>
 * 描述: 商家信息 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月17日 上午11:34:13
 */
public class WcSellerinfo extends BaseModel {

	/**
	 * 商家编号
	 */
	private String sellerCode;

	/**
	 * 商家名称
	 */
	private String sellerName;

	/**
	 * 商家简称
	 */
	private String sellerShortName;

	/**
	 * 商家描述
	 */
	private String sellerDescrption;

	/**
	 * 所在地
	 */
	private String sellerArea;

	/**
	 * 联系电话
	 */
	private String sellerTelephone;

	/**
	 * 退货地址
	 */
	private String sellerReturnAddress;

	/**
	 * 退货邮编
	 */
	private String sellerReturnPostcode;

	/**
	 * 退货联系人地址
	 */
	private String sellerReturnContact;

	/**
	 * 退货联系人电话
	 */
	private String sellerReturnTelephone;

	/**
	 * 公司名称
	 */
	private String sellerCompanyName;

	/**
	 * 商家信箱
	 */
	private String sellerEmail;

	/**
	 * 店铺类型
	 */
	private String sellerType;

	/**
	 * 商户状态 0 未开通 1 已开通 2 已禁用
	 */
	private Integer status;
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改人
	 */
	private String updator;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getSellerShortName() {
		return sellerShortName;
	}

	public void setSellerShortName(String sellerShortName) {
		this.sellerShortName = sellerShortName;
	}

	public String getSellerDescrption() {
		return sellerDescrption;
	}

	public void setSellerDescrption(String sellerDescrption) {
		this.sellerDescrption = sellerDescrption;
	}

	public String getSellerArea() {
		return sellerArea;
	}

	public void setSellerArea(String sellerArea) {
		this.sellerArea = sellerArea;
	}

	public String getSellerTelephone() {
		return sellerTelephone;
	}

	public void setSellerTelephone(String sellerTelephone) {
		this.sellerTelephone = sellerTelephone;
	}

	public String getSellerReturnAddress() {
		return sellerReturnAddress;
	}

	public void setSellerReturnAddress(String sellerReturnAddress) {
		this.sellerReturnAddress = sellerReturnAddress;
	}

	public String getSellerReturnPostcode() {
		return sellerReturnPostcode;
	}

	public void setSellerReturnPostcode(String sellerReturnPostcode) {
		this.sellerReturnPostcode = sellerReturnPostcode;
	}

	public String getSellerReturnContact() {
		return sellerReturnContact;
	}

	public void setSellerReturnContact(String sellerReturnContact) {
		this.sellerReturnContact = sellerReturnContact;
	}

	public String getSellerReturnTelephone() {
		return sellerReturnTelephone;
	}

	public void setSellerReturnTelephone(String sellerReturnTelephone) {
		this.sellerReturnTelephone = sellerReturnTelephone;
	}

	public String getSellerCompanyName() {
		return sellerCompanyName;
	}

	public void setSellerCompanyName(String sellerCompanyName) {
		this.sellerCompanyName = sellerCompanyName;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
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

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}