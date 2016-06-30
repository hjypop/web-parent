package com.hjy.entity.order;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: OcOrderaddress <br>
 * 描述: 订单地址发票表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午5:57:30
 */
public class OcOrderaddress extends BaseModel {

	private String orderCode;

	private String areaCode;
	private String address;

	private String postcode;

	private String mobilephone;

	private String telephone;

	private String receivePerson;

	private String email;

	private String invoiceTitle;

	private String invoiceType;

	private String invoiceContent;

	private Integer flagInvoice;

	private String remark;

	private String invoiceStatus;

	private String authTrueName;

	private String authIdcardType;

	private String authIdcardNumber;

	private String authPhoneNumber;

	private String authEmail;

	private String authAddress;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getReceivePerson() {
		return receivePerson;
	}

	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public Integer getFlagInvoice() {
		return flagInvoice;
	}

	public void setFlagInvoice(Integer flagInvoice) {
		this.flagInvoice = flagInvoice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getAuthTrueName() {
		return authTrueName;
	}

	public void setAuthTrueName(String authTrueName) {
		this.authTrueName = authTrueName;
	}

	public String getAuthIdcardType() {
		return authIdcardType;
	}

	public void setAuthIdcardType(String authIdcardType) {
		this.authIdcardType = authIdcardType;
	}

	public String getAuthIdcardNumber() {
		return authIdcardNumber;
	}

	public void setAuthIdcardNumber(String authIdcardNumber) {
		this.authIdcardNumber = authIdcardNumber;
	}

	public String getAuthPhoneNumber() {
		return authPhoneNumber;
	}

	public void setAuthPhoneNumber(String authPhoneNumber) {
		this.authPhoneNumber = authPhoneNumber;
	}

	public String getAuthEmail() {
		return authEmail;
	}

	public void setAuthEmail(String authEmail) {
		this.authEmail = authEmail;
	}

	public String getAuthAddress() {
		return authAddress;
	}

	public void setAuthAddress(String authAddress) {
		this.authAddress = authAddress;
	}

}