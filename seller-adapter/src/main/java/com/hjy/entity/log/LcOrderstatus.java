package com.hjy.entity.log;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: LcOrderstatus <br>
 * 描述: 订单状态日志表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月30日 上午9:17:59
 */
public class LcOrderstatus extends BaseModel {

	// 编码
	private String code;
	// 日志信息
	private String info;
	// 创建时间
	private String createTime;
	// 创建人
	private String createUser;
	// 原先状态
	private String oldStatus;
	// 当前状态
	private String nowStatus;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public String getNowStatus() {
		return nowStatus;
	}

	public void setNowStatus(String nowStatus) {
		this.nowStatus = nowStatus;
	}

}
