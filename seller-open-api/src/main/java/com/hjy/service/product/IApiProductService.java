package com.hjy.service.product;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 类: IApiProductService <br>
 * 描述: openapi商品业务处理接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月4日 上午9:20:31
 */
public interface IApiProductService {

	/**
	 * 
	 * 方法: addProduct <br>
	 * 描述: 添加商品 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 上午9:36:53
	 * 
	 * @param product
	 * @return
	 */
	JSONObject addProduct(String product);

	/**
	 * 
	 * 方法: editProduct <br>
	 * 描述: 编辑商品 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 上午9:37:00
	 * 
	 * @param product
	 * @return
	 */
	JSONObject editProduct(String product);

	/**
	 * 
	 * 方法: syncProductList <br>
	 * 描述: 批量商品处理 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 上午9:37:07
	 * 
	 * @param products
	 * @return
	 */
	JSONObject batchProducts(String products);

	/**
	 * 
	 * 方法: syncProductPrice <br>
	 * 描述: 同步商品sku价格 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 下午2:42:59
	 * 
	 * @param products
	 * @return
	 */
	JSONObject batchProductsPrice(String products);

	/**
	 * 
	 * 方法: syncSkuStore <br>
	 * 描述: 同步商品sku库存 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 下午2:42:29
	 * 
	 * @param products
	 * @return
	 */
	JSONObject batchProductsSkuStore(String products);
}
