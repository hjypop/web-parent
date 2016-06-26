package com.hjy.selleradapter.kjt.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class RsyncModelGetKjtProduct implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 商品ID
	 */
	private String ProductID = "";
	/**
	 * 商品类别ID
	 */
	private String CategoryID = "";
	/**
	 * 商品类别名称
	 */
	private String CategoryName ="";
	/**
	 * 商品名称
	 */
	private String ProductName ="";
	/**
	 * 商品简称
	 */
	private String BriefName ="";
	/**
	 * 
	 */
	private String ProductMode ="";
	/**
	 * 
	 */
	private String ProductDesc ="";
	/**
	 * 
	 */
	private double Weight;
	/**
	 * 详细描述
	 */
	private String ProductDescLong="";
	/**
	 * 以图片方式展示的详细描述
	 */
	private String ProductPhotoDesc="";
	/**
	 * 详细规格
	 */
	private String Performance="";
	/**
	 * 售后服务
	 */
	private String Warranty="";
	/**
	 * 购买须知
	 */
	private String Attention="";
	/**
	 * 默认图片
	 */
	private String DefaultImage="";
	/**
	 * 促销标题
	 */
	private String PromotionTitle="";
	/**
	 * 关键字
	 */
	private String KeyWords="";
	/**
	 * 供应商编号
	 */
	private int VendorID;
	/**
	 * 供应商名称
	 */
	private String VendorName="";
	/**
	 * 品牌编号
	 */
	private int BrandID;
	/**
	 * 品牌名称
	 */
	private String BrandName="";
	/**
	 * 贸易类型 ,0 = 直邮 ,1 = 自贸
	 */
	private int ProductTradeType;
	/**
	 * 可销售库存
	 */
	private int OnlineQty;
	/**
	 * 销售价格
	 */
	private BigDecimal Price;
	/**
	 * 商品库存列表
	 */
	private ProductEntryInfo ProductEntryInfo=new ProductEntryInfo();
	/**
	 * 跨境通店铺编号
	 */
	private String StoreSysNo="";
	
	public String getProductID() {
		return ProductID;
	}
	public void setProductID(String productID) {
		ProductID = productID;
	}
	public String getCategoryID() {
		return CategoryID;
	}
	public void setCategoryID(String categoryID) {
		CategoryID = categoryID;
	}
	public String getCategoryName() {
		return CategoryName;
	}
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getBriefName() {
		return BriefName;
	}
	public void setBriefName(String briefName) {
		BriefName = briefName;
	}
	public String getProductMode() {
		return ProductMode;
	}
	public void setProductMode(String productMode) {
		ProductMode = productMode;
	}
	public String getProductDesc() {
		return ProductDesc;
	}
	public void setProductDesc(String productDesc) {
		ProductDesc = productDesc;
	}
	public double getWeight() {
		return Weight;
	}
	public void setWeight(double weight) {
		Weight = weight;
	}
	public String getProductDescLong() {
		return ProductDescLong;
	}
	public void setProductDescLong(String productDescLong) {
		ProductDescLong = productDescLong;
	}
	public String getProductPhotoDesc() {
		return ProductPhotoDesc;
	}
	public void setProductPhotoDesc(String productPhotoDesc) {
		ProductPhotoDesc = productPhotoDesc;
	}
	public String getPerformance() {
		return Performance;
	}
	public void setPerformance(String performance) {
		Performance = performance;
	}
	public String getWarranty() {
		return Warranty;
	}
	public void setWarranty(String warranty) {
		Warranty = warranty;
	}
	public String getAttention() {
		return Attention;
	}
	public void setAttention(String attention) {
		Attention = attention;
	}
	public String getDefaultImage() {
		return DefaultImage;
	}
	public void setDefaultImage(String defaultImage) {
		DefaultImage = defaultImage;
	}
	public String getPromotionTitle() {
		return PromotionTitle;
	}
	public void setPromotionTitle(String promotionTitle) {
		PromotionTitle = promotionTitle;
	}
	public String getKeyWords() {
		return KeyWords;
	}
	public void setKeyWords(String keyWords) {
		KeyWords = keyWords;
	}
	public int getVendorID() {
		return VendorID;
	}
	public void setVendorID(int vendorID) {
		VendorID = vendorID;
	}
	public String getVendorName() {
		return VendorName;
	}
	public void setVendorName(String vendorName) {
		VendorName = vendorName;
	}
	public int getBrandID() {
		return BrandID;
	}
	public void setBrandID(int brandID) {
		BrandID = brandID;
	}
	public String getBrandName() {
		return BrandName;
	}
	public void setBrandName(String brandName) {
		BrandName = brandName;
	}
	public int getProductTradeType() {
		return ProductTradeType;
	}
	public void setProductTradeType(int productTradeType) {
		ProductTradeType = productTradeType;
	}
	public int getOnlineQty() {
		return OnlineQty;
	}
	public void setOnlineQty(int onlineQty) {
		OnlineQty = onlineQty;
	}
	public BigDecimal getPrice() {
		return Price;
	}
	public void setPrice(BigDecimal price) {
		Price = price;
	}
	public ProductEntryInfo getProductEntryInfo() {
		return ProductEntryInfo;
	}
	public void setProductEntryInfo(ProductEntryInfo productEntryInfo) {
		ProductEntryInfo = productEntryInfo;
	}
	public String getStoreSysNo() {
		return StoreSysNo;
	}
	public void setStoreSysNo(String storeSysNo) {
		StoreSysNo = storeSysNo;
	}
}
