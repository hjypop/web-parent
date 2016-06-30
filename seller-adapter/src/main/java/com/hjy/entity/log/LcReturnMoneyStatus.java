package com.hjy.entity.log;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: LcReturnMoneyStatus <br>
 * 描述: 退款状态日志表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月30日 下午2:14:52
 */
public class LcReturnMoneyStatus extends BaseModel {

	// 退款单号
	private String returnMoneyNo;
	// 日志信息
	private String info;
	// 创建时间
	private String createTime;
	// 创建人
	private String createUser;
	// 状态
	private String status;

	public String getReturnMoneyNo() {
		return returnMoneyNo;
	}

	public void setReturnMoneyNo(String returnMoneyNo) {
		this.returnMoneyNo = returnMoneyNo;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
