package com.hjy.dao.product;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.entity.product.PcProductproperty;

public interface IPcProductpropertyDao extends BaseDao<PcProductproperty, Integer> {
	public List<PcProductproperty> findListByProductCode(PcProductproperty entity);

	/**
	 * alias deleteByExample
	 * 
	 * @param info
	 * @return
	 */
	Integer deleteByParam(PcProductproperty info);
}
