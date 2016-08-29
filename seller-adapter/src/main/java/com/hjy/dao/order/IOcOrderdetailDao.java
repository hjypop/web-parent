package com.hjy.dao.order;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.model.order.OrderDetail;

public interface IOcOrderdetailDao extends BaseDao<OrderDetail , Integer> {

	/**
	 * @descriptions 批量插入
	 * 
	 * @param list
	 * @return 
	 * @date 2016年8月29日下午3:59:16
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public Integer apiBatchInsert(List<OrderDetail> list);
	
}
