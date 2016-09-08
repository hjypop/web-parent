package com.hjy.dto.response.product;

import java.util.List;



public class Product {
	
	private String ProductID;			// 产品编号
	private String Stock;					// 当前订阅产品数量
	private String Price;						// 价格
	private String ProductName;
	private String TradeType;			// 贸易类型（0为保税贸易，1为海外直邮，2为一般贸易）
	
	private String ProductWeight;
	private String ProductBrand; 		// 品牌
	private String ProductBigtype;		// 分类大类
	private String ProductBarcode;		// 条形码
	private String ProductTaxes;			// 税率
	private String ProductSKU;
	private String ProductKey;			 // 关键字
	
	private String ColdStorage; // 存储方式(0:常温，1:冷藏，2:冷冻)
	
	private List<String> ProductPictures ;  // 轮播图
	private List<String> ProductDescribe ;  // 描述图
	
	
	public String getProductID() {
		return ProductID;
	}
	public void setProductID(String productID) {
		ProductID = productID;
	}
	public String getStock() {
		return Stock;
	}
	public void setStock(String stock) {
		Stock = stock;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getTradeType() {
		return TradeType;
	}
	public void setTradeType(String tradeType) {
		TradeType = tradeType;
	}
	
	
	
	public String getColdStorage() {
		return ColdStorage;
	}
	public void setColdStorage(String coldStorage) {
		ColdStorage = coldStorage;
	}
	public String getProductWeight() {
		return ProductWeight;
	}
	public void setProductWeight(String productWeight) {
		ProductWeight = productWeight;
	}
	public String getProductBrand() {
		return ProductBrand;
	}
	public void setProductBrand(String productBrand) {
		ProductBrand = productBrand;
	}
	public String getProductBigtype() {
		return ProductBigtype;
	}
	public void setProductBigtype(String productBigtype) {
		ProductBigtype = productBigtype;
	}
	public String getProductBarcode() {
		return ProductBarcode;
	}
	public void setProductBarcode(String productBarcode) {
		ProductBarcode = productBarcode;
	}
	public String getProductTaxes() {
		return ProductTaxes;
	}
	public void setProductTaxes(String productTaxes) {
		ProductTaxes = productTaxes;
	}
	public String getProductSKU() {
		return ProductSKU;
	}
	public void setProductSKU(String productSKU) {
		ProductSKU = productSKU;
	}
	public String getProductKey() {
		return ProductKey;
	}
	public void setProductKey(String productKey) {
		ProductKey = productKey;
	}
	public List<String> getProductPictures() {
		return ProductPictures;
	}
	public void setProductPictures(List<String> productPictures) {
		ProductPictures = productPictures;
	}
	public List<String> getProductDescribe() {
		return ProductDescribe;
	}
	public void setProductDescribe(List<String> productDescribe) {
		ProductDescribe = productDescribe;
	}
	
}














































