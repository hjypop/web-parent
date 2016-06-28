package com.hjy.service.system;

import com.hjy.entity.system.ScStore;
import com.hjy.service.IBaseService;

/**
 * 
 * 类: IScStoreService <br>
 * 描述: sc_store仓库表业务逻辑处理接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午1:56:51
 */
public interface IScStoreService extends IBaseService<ScStore, Integer> {

	/**
	 * 
	 * 方法: findScStoreIsExists <br>
	 * 描述: 判断对象在sc_store中是否存在 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 下午2:22:40
	 * 
	 * @param storeCode
	 * @return
	 */
	int findScStoreIsExists(String storeCode);
}
