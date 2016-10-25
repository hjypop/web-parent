package com.hjy.dto.product;

import java.math.BigDecimal;
import java.util.List;

import com.hjy.base.IApiRequest;

public class ProductInfo implements IApiRequest {
	/**
	 * 外部商品编号
	 */
	private String productOutCode;

	/**
	 * 惠家有商品编号
	 */
	private String productCode;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品简称
	 */
	private String productShortname;
	/**
	 * 卖家编号
	 */
	private String sellerCode;
	/**
	 * 品牌编号
	 */
	private String brandCode;
	/**
	 * 商品重量
	 */
	private BigDecimal productWeight;
	/**
	 * 成本价
	 */
	private BigDecimal costPrice;
	/**
	 * 市场价
	 */
	private BigDecimal marketPrice;
	/**
	 * 主图的Url
	 */
	private String mainPicUrl;
	/**
	 * 标签值 ，用逗号分开
	 */
	private String labels;
	/**
	 * 长 宽 高 ，用逗号隔开
	 */
	private String productVolumeItem;

	/**
	 * 商品描述信息
	 */
	private Productdescription description;
	/**
	 * 商品图片信息
	 */
	private List<String> pcPicList;
	/**
	 * 商品体积
	 */
	private BigDecimal productVolume;
	/**
	 * 商品的Sku列表的属性信息
	 */
	private List<PcSkuInfo> skuInfoList;

	/**
	 * 广告图的Url
	 */
	private String adpicUrl;
	/**
	 * 商品广告
	 */
	private String productAdv;
	/**
	 * 保质期
	 */
	private int expiryDate;

	/**
	 * 保质期单位 4497471600290001:天，4497471600290002:月,4497471600290003:年
	 */
	private String expiryUnit;

	/**
	 * 操作类型 0为添加，1为编辑
	 */
	private Integer operate;

	/**
	 * 商品编码数组
	 */
	private List<String> codes;

	private String LD;
	
	private String sellerType;
	
	private String validateFlag;

	public String getValidateFlag() {
		return validateFlag;
	}

	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}

	public String getLD() {
		return LD;
	}

	public void setLD(String lD) {
		LD = lD;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public Integer getOperate() {
		return operate;
	}

	public void setOperate(Integer operate) {
		this.operate = operate;
	}

	public int getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(int expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getExpiryUnit() {
		return expiryUnit;
	}

	public void setExpiryUnit(String expiryUnit) {
		this.expiryUnit = expiryUnit;
	}

	public String getAdpicUrl() {
		return adpicUrl;
	}

	public void setAdpicUrl(String adpicUrl) {
		this.adpicUrl = adpicUrl;
	}

	public String getProductAdv() {
		return productAdv;
	}

	public void setProductAdv(String productAdv) {
		this.productAdv = productAdv;
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

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
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

	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getProductVolumeItem() {
		return productVolumeItem;
	}

	public void setProductVolumeItem(String productVolumeItem) {
		this.productVolumeItem = productVolumeItem;
	}

	public Productdescription getDescription() {
		return description;
	}

	public void setDescription(Productdescription description) {
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

	public List<PcSkuInfo> getSkuInfoList() {
		return skuInfoList;
	}

	public void setSkuInfoList(List<PcSkuInfo> skuInfoList) {
		this.skuInfoList = skuInfoList;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getProductOutCode() {
		return productOutCode;
	}

	public void setProductOutCode(String productOutCode) {
		this.productOutCode = productOutCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
