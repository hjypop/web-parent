package com.hjy.entity.member;

import java.math.BigDecimal;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: McAuthenticationInfo <br>
 * 描述: mc_authenticationInfo表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午6:26:29
 */
public class McAuthenticationInfo extends BaseModel{
	
	private String memberCode;
	// 认证信息编号
	private String authCode;
	// 真实姓名
	private String trueName;
	// 证件类型
	private String idcardType;
	// 证件编号
	private String idcardNumber;
	// 联系电话
	private String phoneNumber;
	// 电子邮件
	private String email;
	// 联系地址
	private String address;
	// 剩余额度
	private BigDecimal surmoney;
	// 创建时间
	private String createTime;
	// 更新时间
	private String updateTime;
	// 通关状态 0正常 1 通关失败
	private Integer customsStatus;
	// 地址编号
	private String addressId;


	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getIdcardType() {
		return idcardType;
	}

	public void setIdcardType(String idcardType) {
		this.idcardType = idcardType;
	}

	public String getIdcardNumber() {
		return idcardNumber;
	}

	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getSurmoney() {
		return surmoney;
	}

	public void setSurmoney(BigDecimal surmoney) {
		this.surmoney = surmoney;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCustomsStatus() {
		return customsStatus;
	}

	public void setCustomsStatus(Integer customsStatus) {
		this.customsStatus = customsStatus;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

}
