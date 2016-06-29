package com.hjy.selleradapter.service;

import java.util.List;

import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.ProductChangeFlag;

public interface IProductService {
	public int AddProductTx(PcProductinfo pc , StringBuffer error , String manageCode);
	
	public PcProductinfo getProduct(String productCode);
	
	public int UpdateProductTx(PcProductinfo productinfo, StringBuffer stringBuffer, String string, ProductChangeFlag productChangeFlag);
	
	/**
	 * @descriptions     
	 * 		p.product_code_old = :product_code_old 
	 * 		p.seller_code = :seller_code 
	 * @param entity
	 * @return
	 * @date 2016年6月29日下午6:25:53
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public List<PcProductinfo> getListBySellerCode(PcProductinfo entity);
}
