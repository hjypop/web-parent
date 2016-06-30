package com.hjy.model.order;


/**   
*  
* 订单地址发票表
*   
* 项目名称：ordercenter   
* 类名称：OrderAddress   
* 类描述：   
* 创建人：yanzj  
* 创建时间：2013-9-2 上午11:03:13   
* 修改人：yanzj
* 修改时间：2013-9-2 上午11:03:13   
* 修改备注：   
* @version    
*    
*/
public class OrderAddress {
	
	/**
	 * 订单编号(创建接口勿传,此值会被覆盖，请勿传)
	 */
	private String orderCode = "";
	
	/**
	 * 地区编码
	 */
	private String areaCode = "";
	
	/**
	 * 地址信息
	 */
	private String address="";
	
	/**
	 * 邮政编码
	 */
	private String postCode="";
	
	/**
	 * 电话
	 */
	private String mobilephone="";
	
	/**
	 * 固定电话
	 */
	private String telephone = "";
	
	/**
	 * 收货人
	 */
	private String receivePerson = "";
	
	/**
	 * 电子邮箱
	 */
	private String email = "";
	
	/**
	 * 发票抬头
	 */
	private String invoiceTitle = "";
	
	/**
	 * 是否开发票 1 开， 0 不开 
	 */
	private String flagInvoice="";
	
	
	
	/**
	 * 发票类型
	 * 449746310001	普通发票
	 * 449746310002	增值税发票
	 */
	private String invoiceType="";
	
	
	/**
	 * 发票内容-暂时请添明细
	 */
	private String invoiceContent="";
	
	
	/**
	 * 订单备注
	 */
	private String remark = "";
	
	/**  真实姓名 **/
	private String authTrueName = "";
	
	/**  证件类型 **/
	private String authIdcardType = "";
	
	/**  证件编号 **/
	private String authIdcardNumber = "";
	
	/**  联系电话 **/
	private String authPhoneNumber = "";
	
	/**  电子邮件**/
	private String authEmail = "";
	
	/**  联系地址 **/
	private String authAddress = "";

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

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
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

	public String getFlagInvoice() {
		return flagInvoice;
	}

	public void setFlagInvoice(String flagInvoice) {
		this.flagInvoice = flagInvoice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getAuthIdcardNumber() {
		return authIdcardNumber;
	}

	public void setAuthIdcardNumber(String authIdcardNumber) {
		this.authIdcardNumber = authIdcardNumber;
	}
	
}
