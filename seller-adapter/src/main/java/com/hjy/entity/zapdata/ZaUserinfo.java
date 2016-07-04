package com.hjy.entity.zapdata;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: ZaUserinfo <br>
 * 描述: 用户信息 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午7:48:37
 */
public class ZaUserinfo extends BaseModel {

	private String userCode;
	private String userName;
	private Integer flagEnable;
	private String realName;
	private String emailAddress;
	private String creatTime;
	private String failedTime;
	private Integer failedCount;
	private String cookieUser;
	private String manageCode;
	private String loginTime;
	private String userTypeDid;
	private String passwordUpdateTime;
	private String mobile;
	private String switchMobile;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getFlagEnable() {
		return flagEnable;
	}

	public void setFlagEnable(Integer flagEnable) {
		this.flagEnable = flagEnable;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public String getFailedTime() {
		return failedTime;
	}

	public void setFailedTime(String failedTime) {
		this.failedTime = failedTime;
	}

	public Integer getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(Integer failedCount) {
		this.failedCount = failedCount;
	}

	public String getCookieUser() {
		return cookieUser;
	}

	public void setCookieUser(String cookieUser) {
		this.cookieUser = cookieUser;
	}

	public String getManageCode() {
		return manageCode;
	}

	public void setManageCode(String manageCode) {
		this.manageCode = manageCode;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getUserTypeDid() {
		return userTypeDid;
	}

	public void setUserTypeDid(String userTypeDid) {
		this.userTypeDid = userTypeDid;
	}

	public String getPasswordUpdateTime() {
		return passwordUpdateTime;
	}

	public void setPasswordUpdateTime(String passwordUpdateTime) {
		this.passwordUpdateTime = passwordUpdateTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSwitchMobile() {
		return switchMobile;
	}

	public void setSwitchMobile(String switchMobile) {
		this.switchMobile = switchMobile;
	}

}
