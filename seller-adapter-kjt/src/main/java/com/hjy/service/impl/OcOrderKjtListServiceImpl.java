package com.hjy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.IOcOrderKjtListDao;
import com.hjy.entity.OcOrderKjtList;
import com.hjy.service.IOcOrderKjtListService;

/**
 * 
 * 类: OcOrderKjtListServiceImpl <br>
 * 描述: 跨境通订单业务逻辑处理实现接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午4:42:45
 */
@Service
public class OcOrderKjtListServiceImpl extends BaseServiceImpl<OcOrderKjtList, Integer>
		implements IOcOrderKjtListService {

	@Resource
	private IOcOrderKjtListDao dao;

	/**
	 * 
	 * 方法: findOrderListByStatus <br>
	 * 描述: TODO
	 * 
	 * @param sostatus
	 * @return
	 * @see com.hjy.service.IOcOrderKjtListService#findOrderListByStatus(java.lang.String)
	 */
	@Override
	public List<OcOrderKjtList> findOrderListByStatus(String sostatus) {
		return dao.findOrderListByStatus(sostatus);
	}

	/**
	 * 
	 * 方法: findOrderByOutCode <br>
	 * 描述: TODO
	 * 
	 * @param orderCodeOut
	 * @return
	 * @see com.hjy.service.IOcOrderKjtListService#findOrderByOutCode(java.lang.String)
	 */
	@Override
	public OcOrderKjtList findOrderByOutCode(String orderCodeOut) {
		return dao.findOrderByOutCode(orderCodeOut);
	}

	/**
	 * 
	 * 方法: findLocalStatusByOrderCode <br>
	 * 描述: TODO
	 * 
	 * @param orderCode
	 * @return
	 * @see com.hjy.service.IOcOrderKjtListService#findLocalStatusByOrderCode(java.lang.String)
	 */
	@Override
	public List<String> findLocalStatusByOrderCode(String orderCode) {
		return dao.findLocalStatusByOrderCode(orderCode);
	}

	@Override
	public int updateCodeByCodeSeq(OcOrderKjtList entity) {
		return dao.updateCodeByCodeSeq(entity);
	}

	@Override
	public OcOrderKjtList findOrderListByCodeSeq(String orderCodeSeq) {
		return dao.findOrderListByCodeSeq(orderCodeSeq);
	}

	@Override
	public List<OcOrderKjtList> findListByOrderCode(String orderCode) {
		return dao.findListByOrderCode(orderCode);
	}
}
