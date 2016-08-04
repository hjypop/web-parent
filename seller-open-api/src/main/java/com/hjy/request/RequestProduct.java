package com.hjy.request;

import com.hjy.dto.product.ProductInfo;

/**
 * 
 * 类: RequestProduct <br>
 * 描述: 商品请求报文 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月4日 上午9:33:45
 */
public class RequestProduct extends ApiRequest {

	private ProductInfo product;

	public ProductInfo getProduct() {
		return product;
	}

	public void setProduct(ProductInfo product) {
		this.product = product;
	}

}
