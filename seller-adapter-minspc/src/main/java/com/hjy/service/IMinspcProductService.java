package com.hjy.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hjy.dto.response.product.Product;
import com.hjy.entity.product.PcProductinfo;

public interface IMinspcProductService {
	
	public Integer insertProductToTables(PcProductinfo entity);
	
	public Integer updateProductInTables(PcProductinfo entity); 
	
	public List<PcProductinfo> productConvertion(List<Product> list);
	
	public List<PcProductinfo> getListBySellerCode(PcProductinfo entity);
}
