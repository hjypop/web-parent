package com.hjy.service.zapdata;

import java.util.List;

import com.hjy.entity.zapdata.ZaRolemenu;
import com.hjy.service.IBaseService;

/**
 * 
 * 类: IZaRolemenuService <br>
 * 描述: 角色菜单表业务处理接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午8:45:23
 */
public interface IZaRolemenuService extends IBaseService<ZaRolemenu, Integer> {
	/**
	 * 
	 * 方法: findMenuByRoleCode <br>
	 * 描述: 根据角色编号查询角色菜单表 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月4日 上午8:50:28
	 * 
	 * @param roleCodes
	 * @return
	 */
	List<ZaRolemenu> findMenuByRoleCode(List<String> roleCodes);
}
