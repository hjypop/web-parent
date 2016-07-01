package com.hjy.dao.order;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.model.order.Express;

public interface IOcExpressEetailDao extends BaseDao<Express , Integer>{
	
	public List<Express> selectByOrderCode(String orderCode);
	
}
