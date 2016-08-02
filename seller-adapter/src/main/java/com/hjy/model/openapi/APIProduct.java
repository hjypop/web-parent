package com.hjy.model.openapi;

import java.util.List;

import com.hjy.entity.product.PcProductdescription;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.PcProductinfoExt;
import com.hjy.entity.product.PcProductpic;
import com.hjy.entity.product.PcProductproperty;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.entity.system.ScStoreSkunum;

public class APIProduct {

	/**
	 * 商品集合
	 */
	private List<PcProductinfo> productList;

	/**
	 * 商品描述信息
	 */
	private List<PcProductdescription> productDescriptionList;

	/**
	 * 商品图片
	 */
	private List<PcProductpic> productPic;

	/**
	 * 商品属性表
	 */
	private List<PcProductproperty> productPropertyList;

	/**
	 * 商品扩展信息
	 */
	private List<PcProductinfoExt> productInfoExtList;

	/**
	 * sku集合
	 */
	private List<PcSkuinfo> skuInfoList;

	/**
	 * sku库存
	 */
	private List<ScStoreSkunum> skunumList;

	public List<PcProductinfo> getProductList() {
		return productList;
	}

	public void setProductList(List<PcProductinfo> productList) {
		this.productList = productList;
	}

	public List<PcProductdescription> getProductDescriptionList() {
		return productDescriptionList;
	}

	public void setProductDescriptionList(List<PcProductdescription> productDescriptionList) {
		this.productDescriptionList = productDescriptionList;
	}

	public List<PcProductpic> getProductPic() {
		return productPic;
	}

	public void setProductPic(List<PcProductpic> productPic) {
		this.productPic = productPic;
	}

	public List<PcProductproperty> getProductPropertyList() {
		return productPropertyList;
	}

	public void setProductPropertyList(List<PcProductproperty> productPropertyList) {
		this.productPropertyList = productPropertyList;
	}

	public List<PcProductinfoExt> getProductInfoExtList() {
		return productInfoExtList;
	}

	public void setProductInfoExtList(List<PcProductinfoExt> productInfoExtList) {
		this.productInfoExtList = productInfoExtList;
	}

	public List<PcSkuinfo> getSkuInfoList() {
		return skuInfoList;
	}

	public void setSkuInfoList(List<PcSkuinfo> skuInfoList) {
		this.skuInfoList = skuInfoList;
	}

	public List<ScStoreSkunum> getSkunumList() {
		return skunumList;
	}

	public void setSkunumList(List<ScStoreSkunum> skunumList) {
		this.skunumList = skunumList;
	}
}
