package com.hjy.entity.product;

import java.math.BigDecimal;

/**
 * @description: open-api 用于向pc_product表插入数据的entity类 | 纯净的实体类
 * @msg 向pc_productinfo表插入数据的时候，推荐使用这个实体类做为传输模型
 * 
 * @author Yangcl
 * @date 2016年12月30日 下午5:38:55 
 * @version 1.0.0
 */
public class ApiPcProductInfo {
	private Integer zid;
    private String uid;
    private String productCodeOld;
    private String productCode;
    private String productName;
    private String productShortname;
    private String videoUrl;
    private String sellerCode;
    private String smallSellerCode;
    private String brandCode;
    private BigDecimal productWeight;
    private Integer flagSale;
    private String createTime;
    private String updateTime;
    private BigDecimal minSellPrice;
    private BigDecimal maxSellPrice;
    private BigDecimal marketPrice;
    private BigDecimal costPrice;
    private BigDecimal taxRate;
    private String productStatus;
    private BigDecimal productVolume;
    private String transportTemplate;
    private String areaTemplate;
    private String sellProductcode;
    private String supplierName;
    private String mainpicUrl;
    private String labels;
    private Integer flagPayway;
    private String productVolumeItem;
    private String saleScopeDid;
    private String validateFlag;
    private String productCodeCopy;
    private String adpicUrl;
    private Integer expiryDate;
    private String expiryUnit;
    private String lowGood;
    private String qualificationCategoryCode;
    private String productAdv;
    
	public Integer getZid() {
		return zid;
	}
	public void setZid(Integer zid) {
		this.zid = zid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getProductCodeOld() {
		return productCodeOld;
	}
	public void setProductCodeOld(String productCodeOld) {
		this.productCodeOld = productCodeOld;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
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
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public String getSmallSellerCode() {
		return smallSellerCode;
	}
	public void setSmallSellerCode(String smallSellerCode) {
		this.smallSellerCode = smallSellerCode;
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
	public Integer getFlagSale() {
		return flagSale;
	}
	public void setFlagSale(Integer flagSale) {
		this.flagSale = flagSale;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public BigDecimal getMinSellPrice() {
		return minSellPrice;
	}
	public void setMinSellPrice(BigDecimal minSellPrice) {
		this.minSellPrice = minSellPrice;
	}
	public BigDecimal getMaxSellPrice() {
		return maxSellPrice;
	}
	public void setMaxSellPrice(BigDecimal maxSellPrice) {
		this.maxSellPrice = maxSellPrice;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public BigDecimal getProductVolume() {
		return productVolume;
	}
	public void setProductVolume(BigDecimal productVolume) {
		this.productVolume = productVolume;
	}
	public String getTransportTemplate() {
		return transportTemplate;
	}
	public void setTransportTemplate(String transportTemplate) {
		this.transportTemplate = transportTemplate;
	}
	public String getAreaTemplate() {
		return areaTemplate;
	}
	public void setAreaTemplate(String areaTemplate) {
		this.areaTemplate = areaTemplate;
	}
	public String getSellProductcode() {
		return sellProductcode;
	}
	public void setSellProductcode(String sellProductcode) {
		this.sellProductcode = sellProductcode;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getMainpicUrl() {
		return mainpicUrl;
	}
	public void setMainpicUrl(String mainpicUrl) {
		this.mainpicUrl = mainpicUrl;
	}
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	public Integer getFlagPayway() {
		return flagPayway;
	}
	public void setFlagPayway(Integer flagPayway) {
		this.flagPayway = flagPayway;
	}
	public String getProductVolumeItem() {
		return productVolumeItem;
	}
	public void setProductVolumeItem(String productVolumeItem) {
		this.productVolumeItem = productVolumeItem;
	}
	public String getSaleScopeDid() {
		return saleScopeDid;
	}
	public void setSaleScopeDid(String saleScopeDid) {
		this.saleScopeDid = saleScopeDid;
	}
	public String getValidateFlag() {
		return validateFlag;
	}
	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}
	public String getProductCodeCopy() {
		return productCodeCopy;
	}
	public void setProductCodeCopy(String productCodeCopy) {
		this.productCodeCopy = productCodeCopy;
	}
	public String getAdpicUrl() {
		return adpicUrl;
	}
	public void setAdpicUrl(String adpicUrl) {
		this.adpicUrl = adpicUrl;
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
	public String getLowGood() {
		return lowGood;
	}
	public void setLowGood(String lowGood) {
		this.lowGood = lowGood;
	}
	public String getQualificationCategoryCode() {
		return qualificationCategoryCode;
	}
	public void setQualificationCategoryCode(String qualificationCategoryCode) {
		this.qualificationCategoryCode = qualificationCategoryCode;
	}
	public String getProductAdv() {
		return productAdv;
	}
	public void setProductAdv(String productAdv) {
		this.productAdv = productAdv;
	} 
}
