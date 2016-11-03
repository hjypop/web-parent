package com.hjy.request.data;

import com.hjy.annotation.ExculdeNullField;

public class OrderAddressInsert {
	@ExculdeNullField  // 标识此字段为非必传字段
	private String areaCode = "";
	private String address;
	private String postcode; // 邮政编码 
	private String mobilephone; // 收件人电话 
	private String receivePerson;  // 收货人
	@ExculdeNullField 
	private String email = "";  // 电子邮箱
	@ExculdeNullField 
	private String invoiceTitle = ""; // 发票抬头
	@ExculdeNullField 
	private String invoiceType = ""; // 发票类型
	@ExculdeNullField 
	private String invoiceContent = ""; // 发票内容
	private Integer flagInvoice; // 是否开发票 0不开 1开 默认1
	@ExculdeNullField 
	private String remark = "";
//	private String invoiceStatus; // 发票状态 449747240001:未开发票(默认) | 449747240002:已开发票
	private String authTrueName; // 真实姓名
	private String authIdcardType; // 证件类型 4497465200090001 身份证
	private String authIdcardNumber; // 证件编号 
	private String authPhoneNumber;  // 联系电话
	private String authEmail;  // 电子邮件
	private String authAddress;  // 联系地址
	
	
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
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
//	public String getInvoiceStatus() {
//		return invoiceStatus;
//	}
//	public void setInvoiceStatus(String invoiceStatus) {
//		this.invoiceStatus = invoiceStatus;
//	}
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
