package com.hjy.service.product;

import com.alibaba.fastjson.JSONObject;
import com.hjy.entity.webcore.WcSellerinfo;

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
	 * @param sellerCode商户编码
	 * @return
	 */
	JSONObject addProduct(String product, String sellerCode);

	/**
	 * 
	 * 方法: editProduct <br>
	 * 描述: 编辑商品 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 上午9:37:00
	 * 
	 * @param product
	 * @param sellerCode商户编码
	 * @return
	 */
	JSONObject editProduct(String product, String sellerCode);

	/**
	 * 
	 * 方法: syncProductList <br>
	 * 描述: 批量商品处理 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 上午9:37:07
	 * 
	 * @param products
	 * @param sellerCode商户编码
	 * @return
	 */
	JSONObject batchProducts(String products, String sellerCode);

	/**
	 * 
	 * 方法: syncProductPrice <br>
	 * 描述: 同步商品sku价格 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 下午2:42:59
	 * 
	 * @param products
	 * @param sellerCode商户编码
	 * @return
	 */
	JSONObject batchProductsPrice(String products, String sellerCode);

	/**
	 * 
	 * 方法: syncSkuStore <br>
	 * 描述: 同步商品sku库存 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 下午2:42:29
	 * 
	 * @param products
	 * @param sellerCode商户编码
	 * @return
	 */
	JSONObject batchProductsSkuStore(String products, String sellerCode);

	/**
	 * 
	 * 方法: pushProduct <br>
	 * 描述: 推送商品到第三方 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月25日 下午4:40:30
	 * 
	 * @param sellerCode
	 *            合作商编号
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	JSONObject pushProduct(WcSellerinfo seller, String startDate, String endDate);

	/**
	 * 
	 * 方法: pushSkuStock <br>
	 * 描述: 推送sku库存到第三方 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月26日 下午3:59:20
	 * 
	 * @param seller
	 * @param productCodes
	 * @return
	 */
	JSONObject pushSkuStock(WcSellerinfo seller, String productCodes);

	/**
	 * 
	 * 方法: pushProductPrice <br>
	 * 描述: 根据时间区间查询商品价格推送到第三方 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月26日 下午3:52:11
	 * 
	 * @param seller
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	JSONObject pushProductPrice(WcSellerinfo seller, String productCodes);
	
	/**
	 * @description: 批量返回时间段内商品上下架状态有变化的商品信息
	 *  			update_tim between '2016-10-01 15:00:00' and '2016-10-01 16:00:00'
	 *  			
	 * @param seller
	 * @param productCodes
	 * @return list
	 * @author Yangcl 
	 * @date 2016年9月30日 上午11:29:46 
	 * @version 1.0.0.1
	 */
	public JSONObject rsyncProductStatus(WcSellerinfo seller);
}














