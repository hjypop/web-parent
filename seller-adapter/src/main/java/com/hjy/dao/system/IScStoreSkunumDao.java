package com.hjy.dao.system;

import com.hjy.dao.BaseDao;
import com.hjy.entity.system.ScStoreSkunum;

public interface IScStoreSkunumDao extends BaseDao<ScStoreSkunum , Integer> {

	/**
	 * 
	 * 方法: findScStoreSkunumByParams <br>
	 * 描述: 根据查询条件查询ScStoreSkunum对象 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 下午1:14:20
	 * @param param
	 * @return
	 */
	ScStoreSkunum findScStoreSkunumByParams(ScStoreSkunum param);
}
