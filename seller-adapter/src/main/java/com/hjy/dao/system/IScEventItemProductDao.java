package com.hjy.dao.system;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.entity.system.ScEventItemProduct;

public interface IScEventItemProductDao  extends BaseDao<ScEventItemProduct , Integer>{

	public List<ScEventItemProduct> findEntityListByProduct(String productCode);
	
}
