package com.hjy.dao.zapdata;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.entity.zapdata.ZaUserrole;

/**
 * 
 * 类: IZaUserroleDao <br>
 * 描述: 用户角色表数据库访问接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午8:34:12
 */
public interface IZaUserroleDao extends BaseDao<ZaUserrole, Integer> {

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
