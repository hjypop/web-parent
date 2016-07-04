package com.hjy.entity.zapdata;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: ZaUserrole <br>
 * 描述: 用户角色表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午8:33:01
 */
public class ZaUserrole extends BaseModel{

	private String userCode;
	private String roleCode;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
}
