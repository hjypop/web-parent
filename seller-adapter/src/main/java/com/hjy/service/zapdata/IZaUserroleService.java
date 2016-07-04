package com.hjy.service.zapdata;

import java.util.List;

import com.hjy.entity.zapdata.ZaUserrole;
import com.hjy.service.IBaseService;

/**
 * 
 * 类: IZaUserroleService <br>
 * 描述: 用户角色表业务处理接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午8:35:27
 */
public interface IZaUserroleService extends IBaseService<ZaUserrole, Integer> {
	/**
	 * 
	 * 方法: findRoleByUserCode <br>
	 * 描述: 根据用户编号查询用户角色信息 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月4日 上午8:40:33
	 * @param userCode
	 * @return
	 */
	List<ZaUserrole> findRoleByUserCode(String userCode);
}
