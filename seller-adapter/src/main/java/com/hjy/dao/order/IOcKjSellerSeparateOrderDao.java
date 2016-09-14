package com.hjy.dao.order;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.entity.order.OcKjSellerSeparateOrder;

public interface IOcKjSellerSeparateOrderDao extends BaseDao<OcKjSellerSeparateOrder , Integer>{

	public List<String> selectByStatus(OcKjSellerSeparateOrder entity);
}
