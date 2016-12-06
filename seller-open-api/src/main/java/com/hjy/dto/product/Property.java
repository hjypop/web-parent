package com.hjy.dto.product;

/**'
 * @description: 商品规格属性描述 
 * 
 * 	【cfamily后台】->【商品相关】->【商户商品】->【一般信息修改】中的商品规格
 * 
 * @author Yangcl
 * @date 2016年12月6日 下午5:24:03 
 * @version 1.0.0
 */
public class Property {
	private String key;
	private String value;
	private String type;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
