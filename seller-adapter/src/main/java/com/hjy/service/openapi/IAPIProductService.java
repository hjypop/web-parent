package com.hjy.service.openapi;

import com.hjy.model.openapi.APIResult;

/**
 * 
 * 类: IAPIProductService <br>
 * 描述: 商品相关接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月1日 下午3:49:57
 */
public interface IAPIProductService {

	public APIResult addProduct(String json);
}
