package com.hjy.service.impl.product;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.product.IPcSkuinfoDao;
import com.hjy.service.product.IPcSkuinfoService;

@Service
public class PcSkuinfoServiceImpl implements IPcSkuinfoService {

	@Resource
	private IPcSkuinfoDao dao;

	/**
	 * 
	 * 方法: findSkuCodeByProductCode <br>
	 * 描述: TODO
	 * 
	 * @param productCode
	 * @return
	 * @see com.hjy.service.product.IPcSkuinfoService#findSkuCodeByProductCode(java.lang.String)
	 */
	@Override
	public String findSkuCodeByProductCode(String productCode) {
		return dao.findSkuCodeByProductCode(productCode);
	}
}
