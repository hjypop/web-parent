package com.hjy.dao.order;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.dto.minspc.VoidDto;
import com.hjy.entity.order.OcKjSellerSeparateOrder;

public interface IOcKjSellerSeparateOrderDao extends BaseDao<OcKjSellerSeparateOrder , Integer>{

	public List<String> selectByStatus(OcKjSellerSeparateOrder entity);
	
	public List<OcKjSellerSeparateOrder> selectByTypes(OcKjSellerSeparateOrder entity);
	
	public Integer updateBySellerOrderCode(OcKjSellerSeparateOrder entity);
	
	
	/**
	 * @description: 查询作废的订单   
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月18日 上午10:45:00 
	 * @version 1.0.0.1
	 */
	public List<OcKjSellerSeparateOrder> selectVoidOrderInfo(VoidDto dto);
	
}
