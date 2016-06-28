package com.hjy.service.impl.system;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.system.IScStoreSkunumDao;
import com.hjy.entity.system.ScStoreSkunum;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.system.IScStoreSkunumService;

/**
 * 
 * 类: ScStoreSkunumServiceImpl <br>
 * 描述: sc_store_skunum业务逻辑处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午1:16:55
 */
@Service
public class ScStoreSkunumServiceImpl extends BaseServiceImpl<ScStoreSkunum, Integer> implements IScStoreSkunumService {

	@Resource
	private IScStoreSkunumDao dao;

	/**
	 * 
	 * 方法: findScStoreSkunumByParams <br>
	 * 描述: TODO
	 * 
	 * @param param
	 * @return
	 * @see com.hjy.service.system.IScStoreSkunumService#findScStoreSkunumByParams(com.hjy.entity.system.ScStoreSkunum)
	 */
	@Override
	public ScStoreSkunum findScStoreSkunumByParams(ScStoreSkunum param) {
		return dao.findScStoreSkunumByParams(param);
	}
}
