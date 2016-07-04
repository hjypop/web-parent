package com.hjy.dao.zapdata;

import java.util.List;

import com.hjy.entity.zapdata.ZaRolemenu;

/**
 * 
 * 类: IZaRolemenuDao <br>
 * 描述: 角色菜单表数据库访问接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午8:44:32
 */
public interface IZaRolemenuDao {

	/**
	 * 
	 * 方法: findMenuByRoleCode <br>
	 * 描述: 根据角色编号查询角色菜单表 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月4日 上午8:50:28
	 * @param roleCodes
	 * @return
	 */
	List<ZaRolemenu> findMenuByRoleCode(List<String> roleCodes);
}
