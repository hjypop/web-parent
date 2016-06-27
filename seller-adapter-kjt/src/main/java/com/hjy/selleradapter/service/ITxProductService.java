package com.hjy.selleradapter.service;

import com.hjy.api.RootResult;
import com.hjy.entity.product.PcProductinfo;

public interface ITxProductService {
	public void insertProduct(PcProductinfo pc, RootResult ret, String operator);
}
