package com.hjy.service;

import java.util.List;

import com.hjy.dto.response.ResultMsg;
import com.hjy.dto.response.product.Product;
import com.hjy.entity.product.PcProductinfo;

public interface IMinspcProductService {
	
	public ResultMsg insertProductToTables(PcProductinfo entity);
	
	/**
	 * @description: 
	 * 
	 * @param entity 新数据
	 * @param info 数据库中的记录
	 * @return
	 * @author Yangcl 
	 * @date 2017年2月17日 上午10:32:12 
	 * @version 1.0.0.1
	 */
	public ResultMsg updateProductInTables(PcProductinfo entity , PcProductinfo info); 
	
	public List<PcProductinfo> productConvertion(List<Product> list);
	
	public List<PcProductinfo> getListBySellerCode(PcProductinfo entity);
}
