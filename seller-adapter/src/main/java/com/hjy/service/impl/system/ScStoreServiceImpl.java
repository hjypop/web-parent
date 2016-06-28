package com.hjy.service.impl.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjy.dao.system.IScStoreDao;
import com.hjy.entity.system.ScStore;
import com.hjy.service.system.IScStoreService;

/**
 * 
 * 类: ScStoreServiceImpl <br>
 * 描述: sc_store表业务逻辑处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午1:56:51
 */
@Service
public class ScStoreServiceImpl implements IScStoreService {

	@Autowired
	private IScStoreDao dao;

	/**
	 * 
	 * 方法: findScStoreIsExists <br>
	 * 描述: TODO
	 * 
	 * @param storeCode
	 * @return
	 * @see com.hjy.service.system.IScStoreService#findScStoreIsExists(java.lang.String)
	 */
	@Override
	public int findScStoreIsExists(String storeCode) {
		ScStore entity = new ScStore();
		entity.setStoreCode(storeCode);
		return dao.count(entity);
	}

	/**
	 * 
	 * 方法: insertSelective <br>
	 * 描述: TODO
	 * 
	 * @param entity
	 * @return
	 * @see com.hjy.service.system.IScStoreService#insertSelective(com.hjy.entity.system.ScStore)
	 */
	@Override
	public int insertSelective(ScStore entity) {
		return dao.insertSelective(entity);
	}

}
