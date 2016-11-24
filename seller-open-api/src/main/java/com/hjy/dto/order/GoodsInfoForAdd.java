package com.hjy.dto.order;

public class GoodsInfoForAdd {
	// 商品数量
	private Integer sku_num;
	// 地区编号 可不填写，添加购物车不再需要区域编号
	private String area_code;
	// 商品编号
	private String product_code;
	// 是否选择 1：是，0：否
	private String chooseFlag = "1";
	// sku编号
	private String skuCode;
	
	public Integer getSku_num() {
		return sku_num;
	}
	public void setSku_num(Integer sku_num) {
		this.sku_num = sku_num;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getChooseFlag() {
		return chooseFlag;
	}
	public void setChooseFlag(String chooseFlag) {
		this.chooseFlag = chooseFlag;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
}
