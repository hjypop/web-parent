package com.hjy.dto.product;

import java.math.BigDecimal;
import java.util.List;

import com.hjy.annotation.TargetField;

/**
 * @description: open-api 惠家有商户商品的数据封装体
 * 
 * TODO 待删除：RequestProducts  &  com.hjy.dto.product.ProductInfo
 * 
 * @author Yangcl
 * @date 2016年12月29日 下午6:03:24 
 * @version 1.0.0
 */
public class ApiSellerProduct {
//	private Integer operate;    // 操作类型 0为添加，1为编辑
//	private String sellerCode;    // 卖家编号
//	private String productOutCode;    // 外部商品编号|商户自己的商品编码
	@TargetField(value="productOutCode")
	private String sellerProductCode;    // 商户商品编号 对应惠家有的 productOutCode(外部商品编号)
	private String productName;    // 商品名称
	private String productShortname;    // 商品简称
	private BigDecimal productWeight;    // 商品重量
	private BigDecimal costPrice;    // 成本价
	private BigDecimal marketPrice;    // 市场价
	private String mainPicUrl;    // 主图的Url
	private String productVolumeItem;    // 长 宽 高 ，用逗号隔开
	private BigDecimal productVolume;    // 商品体积
	private Integer expiryDate;    // 保质期
	private String expiryUnit;    // 保质期单位 4497471600290001:天，4497471600290002:月,4497471600290003:年
	
	private ApiProductDesc description;    // 商品描述信息
	private List<String> pcPicList;    // 商品图片信息
	private List<ApiSellerSkuInfo> skuList;    // 商品的Sku列表的属性信息
	
	
/*	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}*/
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
	public String getMainPicUrl() {
		return mainPicUrl;
	}
	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}
	public String getProductVolumeItem() {
		return productVolumeItem;
	}
	public void setProductVolumeItem(String productVolumeItem) {
		this.productVolumeItem = productVolumeItem;
	}
	public ApiProductDesc getDescription() {
		return description;
	}
	public void setDescription(ApiProductDesc description) {
		this.description = description;
	}
	public List<String> getPcPicList() {
		return pcPicList;
	}
	public void setPcPicList(List<String> pcPicList) {
		this.pcPicList = pcPicList;
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























