package com.hjy.selleradapter.service;

import com.hjy.api.RootResult;
import com.hjy.selleradapter.kjt.model.PcProductinfo;

public interface ITxProductService {
	public void insertProduct(PcProductinfo pc, RootResult ret, String operator);
}
