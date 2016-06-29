package com.hjy.model;

import java.util.ArrayList;
import java.util.List;
/**
 * @descriptions 
 * 
 * propertyTypeList	 
 * 	449736200001 颜色属性  
 * 	449736200002 关键属性
 * 	449736200003 销售属性
 * 	449736200004	自定义属性  				目前只有这个属性在用
 * 
 * @date 2016年6月29日下午4:31:05
 * @author Yangcl
 * @version 1.0.1
 */
public class PcProductpropertyExample {
	private String productCode = "";
	private List<String> propertyTypeList = new ArrayList<String>();
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public List<String> getPropertyTypeList() {
		return propertyTypeList;
	}
	public void setPropertyTypeList(List<String> propertyTypeList) {
		this.propertyTypeList = propertyTypeList;
	}
	
	
}
