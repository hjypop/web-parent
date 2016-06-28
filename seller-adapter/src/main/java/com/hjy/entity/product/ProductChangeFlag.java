package com.hjy.entity.product;

public class ProductChangeFlag {
	
	/**
	 * 商品主数据
	 */
	private boolean isChangeProductMain = true;
	
	
	/**
	 * 商品sku数据
	 */
	private boolean isChangeProductSku = true;
	
	
	/**
	 * 是否能修改商品的sku的主属性
	 */
	private boolean isChangeSkuPropertyMain = true;
	
	
	/**
	 * 是否能修改商品的sku辅助属性
	 */
	private boolean isChangeSkuPropertySub = true;
	
	/**
	 * 是否能修改商品的图片
	 */
	private boolean isChangeProductPic = true;
	
	
	/**
	 * 是否能够修改商品的日志数据
	 */
	private boolean isChangeProductFlow = true;
	
	
	/**
	 * 是否能修改商品的价格
	 */
	private boolean isChangePrice = true;
	
	/**
	 * 是否能够修改商品的库存
	 */
	private boolean isChangeStock = true;
	
	/**
	 * 是否能够修改商品的描述信息
	 */
	private boolean isChangeDescription = true;
	
	/**
	 * 商品的老主图-请勿删
	 */
	private String oldPicUrl = "";
	
	
	/**
	 * 商品的老名字-请勿删
	 */
	private String oldProductName = "";
	
	/**
	 * 是否能修改商品的扩展属性
	 */
	private boolean isChangeProductExt = true;

	/**
	 * 是否能修改商品的虚类信息
	 */
	private boolean isChangeProductCategory = true;
	
	public String getOldProductName() {
		return oldProductName;
	}

	public void setOldProductName(String oldProductName) {
		this.oldProductName = oldProductName;
	}

	public String getOldPicUrl() {
		return oldPicUrl;
	}

	public void setOldPicUrl(String oldPicUrl) {
		this.oldPicUrl = oldPicUrl;
	}

	public boolean isChangeProductMain() {
		return isChangeProductMain;
	}

	public void setChangeProductMain(boolean isChangeProductMain) {
		this.isChangeProductMain = isChangeProductMain;
	}

	public boolean isChangeProductSku() {
		return isChangeProductSku;
	}

	public void setChangeProductSku(boolean isChangeProductSku) {
		this.isChangeProductSku = isChangeProductSku;
	}

	public boolean isChangeSkuPropertyMain() {
		return isChangeSkuPropertyMain;
	}

	public void setChangeSkuPropertyMain(boolean isChangeSkuPropertyMain) {
		this.isChangeSkuPropertyMain = isChangeSkuPropertyMain;
	}

	public boolean isChangeSkuPropertySub() {
		return isChangeSkuPropertySub;
	}

	public void setChangeSkuPropertySub(boolean isChangeSkuPropertySub) {
		this.isChangeSkuPropertySub = isChangeSkuPropertySub;
	}

	public boolean isChangeProductPic() {
		return isChangeProductPic;
	}

	public void setChangeProductPic(boolean isChangeProductPic) {
		this.isChangeProductPic = isChangeProductPic;
	}

	public boolean isChangeProductFlow() {
		return isChangeProductFlow;
	}

	public void setChangeProductFlow(boolean isChangeProductFlow) {
		this.isChangeProductFlow = isChangeProductFlow;
	}

	public boolean isChangePrice() {
		return isChangePrice;
	}

	public void setChangePrice(boolean isChangePrice) {
		this.isChangePrice = isChangePrice;
	}

	public boolean isChangeStock() {
		return isChangeStock;
	}

	public void setChangeStock(boolean isChangeStock) {
		this.isChangeStock = isChangeStock;
	}

	public boolean isChangeDescription() {
		return isChangeDescription;
	}

	public void setChangeDescription(boolean isChangeDescription) {
		this.isChangeDescription = isChangeDescription;
	}

	public boolean isChangeProductExt() {
		return isChangeProductExt;
	}

	public void setChangeProductExt(boolean isChangeProductExt) {
		this.isChangeProductExt = isChangeProductExt;
	}

	public boolean isChangeProductCategory() {
		return isChangeProductCategory;
	}

	public void setChangeProductCategory(boolean isChangeProductCategory) {
		this.isChangeProductCategory = isChangeProductCategory;
	}
	
}
