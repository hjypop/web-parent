package com.hjy.selleradapter.service;

import com.hjy.entity.product.PcProductinfo;

public interface IProductService {
	public int AddProductTx(PcProductinfo pc , StringBuffer error , String manageCode);
}
