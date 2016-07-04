package com.hjy.dao.zapdata;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.entity.zapdata.ZaUsermenu;

/**
 * 
 * 类: IZaUsermenuDao <br>
 * 描述: 用户菜单表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午8:54:57
 */
public interface IZaUsermenuDao extends BaseDao<ZaUsermenu, Integer> {

	/**
	 * 
	 * 方法: findMenuByUserCode <br>
	 * 描述: 根据用户编号查询用户菜单 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月4日 上午8:56:15
	 * 
	 * @param userCode
	 * @return
	 */
	List<ZaUsermenu> findMenuByUserCode(String userCode);
}
