package com.hjy.dao.zapdata;

import com.hjy.dao.BaseDao;
import com.hjy.entity.zapdata.ZaUserinfo;

/**
 * 
 * 类: IZaUserinfoDao <br>
 * 描述: 用户信息表数据库访问接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午7:52:48
 */
public interface IZaUserinfoDao extends BaseDao<ZaUserinfo, Integer>{

	/**
	 * 
	 * 方法: findUserInfoByCookie <br>
	 * 描述: 根据Cookie信息查询用户信息 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月4日 上午8:07:11
	 * @param cookieUser
	 * @return
	 */
	ZaUserinfo findUserInfoByCookie(String cookieUser);
}
