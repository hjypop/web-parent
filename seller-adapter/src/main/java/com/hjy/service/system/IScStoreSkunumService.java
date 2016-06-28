package com.hjy.service.system;

import com.hjy.entity.system.ScStoreSkunum;
import com.hjy.service.IBaseService;

/**
 * 
 * 类: IScStoreSkunumService <br>
 * 描述: sc_store_skunum库存信息业务逻辑处理接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午1:16:55
 */
public interface IScStoreSkunumService extends IBaseService<ScStoreSkunum, Integer> {
	/**
	 * 
	 * 方法: findScStoreSkunumByParams <br>
	 * 描述: 根据查询条件查询库存信息 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 下午1:14:20
	 * 
	 * @param param
	 * @return
	 */
	ScStoreSkunum findScStoreSkunumByParams(ScStoreSkunum param);
}
