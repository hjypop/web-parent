package com.hjy.request.data;

/**
 * @description: 订单中的商品信息 
 * 
 * @author Yangcl
 * @date 2016年12月7日 下午1:42:46 
 * @version 1.0.0
 */
public class OrderProductInfo {
	private Integer sku_num;  // 商品数量
	private String area_code;  // 地区编号	可不填写，添加购物车不再需要区域编号
	private String product_code;  // 商品编号
	private String chooseFlag;  // 是否选择：1：是，0：否
	private String sku_code;  // sku编号
	
	
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
	public String getSku_code() {
		return sku_code;
	}
	public void setSku_code(String sku_code) {
		this.sku_code = sku_code;
	}
}
