package com.hjy.dao.order;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.entity.order.OcOrderPay;

public interface IOcOrderPayDao extends BaseDao<OcOrderPay , Integer> {
	
	
	public List<OcOrderPay> findListByOrderCode(String orderCode);
}
