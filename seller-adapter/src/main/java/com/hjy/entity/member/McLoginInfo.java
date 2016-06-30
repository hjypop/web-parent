package com.hjy.entity.member;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: McLoginInfo <br>
 * 描述: 登陆信息表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月30日 上午10:34:58
 */
public class McLoginInfo extends BaseModel {

	// 登陆编号
	private String loginCode;
	// 应用编号
	private String manageCode;
	// 用户登陆名
	private String loginName;
	// 登陆密码
	private String loginPass;
	// 用户编号
	private String memberCode;
	// 创建时间
	private String createTime;
	// 最后登陆失败时间
	private String failedTime;
	// 登陆失败次数
	private Integer failedCount;
	// 是否可用
	private Integer flagEnable;
	// 最后登陆时间
	private String lastTime;
	// 登陆信息类型
	private String loginType;
	// 登陆信息分组
	private String loginGroup;
	// 0:表示删除1:不删除
	private Integer ifDelete;

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public String getManageCode() {
		return manageCode;
	}

	public void setManageCode(String manageCode) {
		this.manageCode = manageCode;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPass() {
		return loginPass;
	}

	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public Integer getFlagEnable() {
		return flagEnable;
	}

	public void setFlagEnable(Integer flagEnable) {
		this.flagEnable = flagEnable;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getLoginGroup() {
		return loginGroup;
	}

	public void setLoginGroup(String loginGroup) {
		this.loginGroup = loginGroup;
	}

	public Integer getIfDelete() {
		return ifDelete;
	}

	public void setIfDelete(Integer ifDelete) {
		this.ifDelete = ifDelete;
	}

}
