package com.hjy.service.zapdata;

import java.util.List;

import com.hjy.entity.zapdata.ZaUsermenu;
import com.hjy.service.IBaseService;

/**
 * 
 * 类: IZaUsermenuService <br>
 * 描述: 用户菜单表业务处理接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午8:56:43
 */
public interface IZaUsermenuService extends IBaseService<ZaUsermenu, Integer> {
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
