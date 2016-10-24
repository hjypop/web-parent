package com.hjy.dto.product;

import java.math.BigDecimal;

public class PcSkuInfo {

	/**
	 * 外部sku编码
	 */
	private String skuCode;

	/**
	 * 外部商品编号
	 */
	private String productCode;
	/**
	 * 销售价
	 */
	private BigDecimal sellPrice;
	/**
	 * 库存数
	 */
	private long stockNum;
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
	private String skuAdv;
	/**
	 * 最小购买数
	 */
	private Integer miniOrder;

	/**
	 * 二维码图片链接
	 */
	private String qrcodeLink;

	private String skuKey;

	private String skuKeyvalue;
	/**
	 * 安全库存
	 */
	private long securityStockNum;

	public String getSkuKey() {
		return skuKey;
	}

	public void setSkuKey(String skuKey) {
		this.skuKey = skuKey;
	}

	public String getSkuKeyvalue() {
		return skuKeyvalue;
	}

	public void setSkuKeyvalue(String skuKeyvalue) {
		this.skuKeyvalue = skuKeyvalue;
	}

	public long getSecurityStockNum() {
		return securityStockNum;
	}

	public void setSecurityStockNum(long securityStockNum) {
		this.securityStockNum = securityStockNum;
	}

	public String getQrcodeLink() {
		return qrcodeLink;
	}

	public void setQrcodeLink(String qrcodeLink) {
		this.qrcodeLink = qrcodeLink;
	}

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

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
