package com.hjy.entity.system;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: ScStore <br>
 * 描述: 仓库表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午2:44:15
 */
public class ScStore extends BaseModel {

	// 仓库编号
	private String storeCode;
	// 仓库名称
	private String storeName;
	// app名称
	private String appName;
	// app编号
	private String appCode;
	// 仓库描述
	private String remark;

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
