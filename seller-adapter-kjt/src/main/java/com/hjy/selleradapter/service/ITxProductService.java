package com.hjy.selleradapter.service;

import com.hjy.api.RootResult;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.ProductChangeFlag;

public interface ITxProductService {
	public void insertProduct(PcProductinfo pc, RootResult ret, String operator);

//	public void updateProduct(PcProductinfo pc, RootResult rr, String manageCode, ProductChangeFlag pcf); 
}
