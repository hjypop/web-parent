package com.hjy.entity.zapdata;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: ZaRolemenu <br>
 * 描述: 角色菜单表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午8:43:48
 */
public class ZaRolemenu extends BaseModel {

	private String roleCode;
	private String menuCode;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

}
