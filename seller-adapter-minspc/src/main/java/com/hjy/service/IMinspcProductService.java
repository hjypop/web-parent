package com.hjy.service;

import java.util.List;

import com.hjy.dto.response.ResultMsg;
import com.hjy.dto.response.product.Product;
import com.hjy.entity.product.PcProductinfo;

public interface IMinspcProductService {
	
	public ResultMsg insertProductToTables(PcProductinfo entity);
	
	public ResultMsg updateProductInTables(PcProductinfo entity); 
	
	public List<PcProductinfo> productConvertion(List<Product> list);
	
	public List<PcProductinfo> getListBySellerCode(PcProductinfo entity);
}
