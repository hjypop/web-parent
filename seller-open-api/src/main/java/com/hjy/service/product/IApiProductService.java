package com.hjy.service.product;

import com.hjy.request.RequestProduct;
import com.hjy.response.product.ResponseProduct;

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
	ResponseProduct addProduct(String product);

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
	ResponseProduct editProduct(String product);

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
	ResponseProduct syncProductList(String products);
}
