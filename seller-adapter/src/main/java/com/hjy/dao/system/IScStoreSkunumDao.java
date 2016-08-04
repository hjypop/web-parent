package com.hjy.dao.system;

import com.hjy.dao.BaseDao;
import com.hjy.entity.system.ScStoreSkunum;

/**
 * 
 * 类: IScStoreSkunumDao <br>
 * 描述: sc_store_skunum库存表数据库访问接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午2:29:16
 */
public interface IScStoreSkunumDao extends BaseDao<ScStoreSkunum, Integer> {

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

	int updateSelectiveByUuid(ScStoreSkunum sssModel);

	/**
	 * 
	 * 方法: updateSelectiveBySkuCode <br>
	 * 描述: 根据sku编码修改库存 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 下午4:23:57
	 * 
	 * @param sssModel
	 * @return
	 */
	int updateSelectiveBySkuCode(ScStoreSkunum sssModel);
}
