package com.hjy.service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.system.cmodel.CacheWcSellerInfo;

/**
 * @descriptions 提供【商户平台】的所有服务
 *
 * @author Yangcl 
 * @date 2017年1月5日 下午10:48:00
 * @version 1.0.1
 */
public interface IOpenApiSellerService {
	/**
	 * @description: 商户同步自己的商品到惠家有平台|同时同步一批商品，上线100件商品
	 * 	
	 * @接口所属：惠家有商户接口|Product.SyncSellerProductList
	 * 
	 * @param products
	 * @param seller
	 * @author Yangcl 
	 * @date 2016年12月29日 下午4:45:50 
	 * @version 1.0.0.1
	 */
	public JSONObject syncSellerProductList(String products, CacheWcSellerInfo seller);
}
