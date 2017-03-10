package com.hjy.dao.product;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.entity.product.PcProductAuthorityLogo;

public interface IPcProductAuthorityLogoDao extends BaseDao<PcProductAuthorityLogo, Integer> {
	public List<PcProductAuthorityLogo> getAllInformation();
}
