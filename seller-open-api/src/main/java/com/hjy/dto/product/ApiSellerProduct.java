package com.hjy.dto.product;

import java.math.BigDecimal;
import java.util.List;

import com.hjy.annotation.TargetField;

/**
 * @description: open-api 惠家有商户商品的数据封装体
 * 
 * TODO 待删除：RequestProducts  &  com.hjy.dto.product.ProductInfo
 * TODO private BigDecimal taxes;			// 税率  这里也是个问题
 * 
 * @author Yangcl
 * @date 2016年12月29日 下午6:03:24 
 * @version 1.0.0
 */
public class ApiSellerProduct {
	@TargetField(value="productOutCode")
	private String sellerProductCode;    // 商户商品编号 对应惠家有的 productOutCode(外部商品编号)
	private String productName;    // 商品名称
	private String productShortname;    // 商品简称
	private BigDecimal costPrice;    // 成本价
	private BigDecimal marketPrice;    // 市场价
	private BigDecimal productWeight;    // 商品重量
	private String mainpicUrl;    // 主图的Url
	private String productVolumeItem;    // 长 宽 高 ，用逗号隔开
	private BigDecimal productVolume;    // 商品体积
	private Integer expiryDate;    // 保质期
	private String expiryUnit;    // 保质期单位 4497471600290001:天，4497471600290002:月,4497471600290003:年
	private BigDecimal taxes;			// 税率
	private String labels;			 // 关键字
	private List<String> productPictures ;  // 轮播图
	private List<String> productDescribe ;  // 描述图
	private List<ApiSellerSkuInfo> skuList;    // 商品的Sku列表的属性信息
	
	
//	private String settlementType; // 服务费结算方式 常规结算4497471600110001| 特殊结算4497471600110002|服务费结算 4497471600110003
//	public String getSettlementType() {
//		return settlementType;
//	}
//	public void setSettlementType(String settlementType) {
//		this.settlementType = settlementType;
//	}
	public BigDecimal getTaxes() {
		return taxes;
	}
	public void setTaxes(BigDecimal taxes) {
		this.taxes = taxes;
	}
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	public List<String> getProductPictures() {
		return productPictures;
	}
	public void setProductPictures(List<String> productPictures) {
		this.productPictures = productPictures;
	}
	public List<String> getProductDescribe() {
		return productDescribe;
	}
	public void setProductDescribe(List<String> productDescribe) {
		this.productDescribe = productDescribe;
	}
	public String getSellerProductCode() {
		return sellerProductCode;
	}
	public void setSellerProductCode(String sellerProductCode) {
		this.sellerProductCode = sellerProductCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductShortname() {
		return productShortname;
	}
	public void setProductShortname(String productShortname) {
		this.productShortname = productShortname;
	}
	public BigDecimal getProductWeight() {
		return productWeight;
	}
	public void setProductWeight(BigDecimal productWeight) {
		this.productWeight = productWeight;
	}
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getMainpicUrl() {
		return mainpicUrl;
	}
	public void setMainpicUrl(String mainpicUrl) {
		this.mainpicUrl = mainpicUrl;
	}
	public String getProductVolumeItem() {
		return productVolumeItem;
	}
	public void setProductVolumeItem(String productVolumeItem) {
		this.productVolumeItem = productVolumeItem;
	}
	public BigDecimal getProductVolume() {
		return productVolume;
	}
	public void setProductVolume(BigDecimal productVolume) {
		this.productVolume = productVolume;
	}
	public Integer getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Integer expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getExpiryUnit() {
		return expiryUnit;
	}
	public void setExpiryUnit(String expiryUnit) {
		this.expiryUnit = expiryUnit;
	}
	public List<ApiSellerSkuInfo> getSkuList() {
		return skuList;
	}
	public void setSkuList(List<ApiSellerSkuInfo> skuList) {
		this.skuList = skuList;
	}
	
}























