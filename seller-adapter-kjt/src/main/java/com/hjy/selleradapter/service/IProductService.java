package com.hjy.selleradapter.service;

import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.ProductChangeFlag;

public interface IProductService {
	public int AddProductTx(PcProductinfo pc , StringBuffer error , String manageCode);
	
	public PcProductinfo getProduct(String productCode);
	
	public int UpdateProductTx(PcProductinfo productinfo, StringBuffer stringBuffer, String string, ProductChangeFlag productChangeFlag);
}
