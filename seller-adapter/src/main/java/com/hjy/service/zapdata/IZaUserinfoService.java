package com.hjy.service.zapdata;

import com.hjy.entity.zapdata.ZaUserinfo;
import com.hjy.service.IBaseService;

/**
 * 
 * 类: IZaUserinfoService <br>
 * 描述: 用户信息表业务处理接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午8:07:48
 */
public interface IZaUserinfoService extends IBaseService<ZaUserinfo, Integer> {

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
