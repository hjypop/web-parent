package com.hjy.selleradapter.service;

import com.hjy.selleradapter.kjt.model.PcProductinfo;

public interface IProductService {
	public int AddProductTx(PcProductinfo pc , StringBuffer error , String manageCode);
}
