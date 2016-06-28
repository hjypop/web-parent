package com.hjy.service.system;

import com.hjy.entity.system.ScStoreSkunum;

/**
 * 
 * 类: ScStoreSkunumServiceImpl <br>
 * 描述: sc_store_skunum业务逻辑处理接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午1:16:55
 */
public interface IScStoreSkunumService {
	/**
	 * 
	 * 方法: findScStoreSkunumByParams <br>
	 * 描述: 根据查询条件查询ScStoreSkunum对象 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 下午1:14:20
	 * 
	 * @param param
	 * @return
	 */
	ScStoreSkunum findScStoreSkunumByParams(ScStoreSkunum param);
	
	/**
	 * 
	 * 方法: updateSelective <br>
	 * 描述: 根据zid更新ScStoreSkunum <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 下午1:41:29
	 * @param entity
	 * @return
	 */
	Integer updateSelective(ScStoreSkunum entity);
}
