package com.hjy.dto.product;

import java.math.BigDecimal;

public class PcSkuInfo {
	/**
	 * 销售价
	 */
	private BigDecimal sellPrice;
	/**
	 * 库存数
	 */
	private long stockNum = 0;
	/**
	 * 商品的Sku的图片信息
	 */
	private String skuPicUrl;
	/**
	 * 商品的sku名称信息
	 */
	private String skuName;
	/**
	 * 广告语
	 */
	private String skuAdv = "";
	/**
	 * 最小购买数
	 */
	private Integer miniOrder = 1;

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getSkuPicUrl() {
		return skuPicUrl;
	}

	public void setSkuPicUrl(String skuPicUrl) {
		this.skuPicUrl = skuPicUrl;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuAdv() {
		return skuAdv;
	}

	public void setSkuAdv(String skuAdv) {
		this.skuAdv = skuAdv;
	}

	public Integer getMiniOrder() {
		return miniOrder;
	}

	public void setMiniOrder(Integer miniOrder) {
		this.miniOrder = miniOrder;
	}

	public long getStockNum() {
		return stockNum;
	}

	public void setStockNum(long stockNum) {
		this.stockNum = stockNum;
	}

}
