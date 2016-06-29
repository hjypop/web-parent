package com.hjy.dao.product;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.entity.product.PcProductproperty;
import com.hjy.model.PcProductpropertyExample;

public interface IPcProductpropertyDao extends BaseDao<PcProductproperty , Integer> {
	public List<PcProductproperty> findListByProductCode(PcProductproperty entity);

	public int deleteByExample(PcProductpropertyExample ppteample);	
}
