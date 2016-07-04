package com.hjy.entity.zapdata;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: ZaUsermenu <br>
 * 描述: 用户菜单表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午8:53:26
 */
public class ZaUsermenu extends BaseModel {

	private String userCode;
	private String menuCode;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

}
