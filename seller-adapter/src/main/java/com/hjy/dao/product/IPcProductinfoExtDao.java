package com.hjy.dao.product;

import com.hjy.dao.BaseDao;
import com.hjy.entity.product.PcProductinfoExt;

public interface IPcProductinfoExtDao extends BaseDao<PcProductinfoExt, Integer> {
	int updateSelectiveByProductCode(PcProductinfoExt ppExtModel);

	PcProductinfoExt findByType(PcProductinfoExt info);
}
