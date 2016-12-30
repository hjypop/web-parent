package com.hjy.dto.product;

import java.math.BigDecimal;

/**
 * @description: open-api 惠家有商户商品Sku信息的数据封装体
 * 
 * TODO 待删除：com.hjy.dto.product.PcSkuInfo 
 * 
 * @author Yangcl
 * @date 2016年12月29日 下午6:03:24 
 * @version 1.0.0
 */
public class ApiSellerSkuInfo {
	
	private String skuCode;    // sku编码
	private BigDecimal sellPrice;    // 销售价
	private BigDecimal costPrice; 	// 成本价
	private Long stockNum;    // 库存数
	private String skuPicUrl;    // 商品的Sku的图片信息
//	private String skuKey;    // color_id=6&style_id=0
//	private String skuKeyvalue;    // 颜色=白色&款式=共同
	private String skuName;    // 商品的sku名称信息
	private String skuAdv;    // 广告语
	private Long securityStockNum;    // 安全库存
	private String qrcodeLink;    // 二维码图片链接
	private Integer miniOrder;    // 起订数量
	
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public BigDecimal getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	public Long getStockNum() {
		return stockNum;
	}
	public void setStockNum(Long stockNum) {
		this.stockNum = stockNum;
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
	public Long getSecurityStockNum() {
		return securityStockNum;
	}
	public void setSecurityStockNum(Long securityStockNum) {
		this.securityStockNum = securityStockNum;
	}
	public String getQrcodeLink() {
		return qrcodeLink;
	}
	public void setQrcodeLink(String qrcodeLink) {
		this.qrcodeLink = qrcodeLink;
	}
	public Integer getMiniOrder() {
		return miniOrder;
	}
	public void setMiniOrder(Integer miniOrder) {
		this.miniOrder = miniOrder;
	}
}




















