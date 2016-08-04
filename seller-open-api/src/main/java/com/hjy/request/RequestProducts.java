package com.hjy.request;

import java.util.List;

import com.hjy.dto.product.ProductInfo;

public class RequestProducts extends ApiRequest {

	/**
	 * 商品总数
	 */
	private int total;
	/**
	 * 商品集合
	 */
	private List<ProductInfo> productInfos;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<ProductInfo> getProductInfos() {
		return productInfos;
	}

	public void setProductInfos(List<ProductInfo> productInfos) {
		this.productInfos = productInfos;
	}

}
