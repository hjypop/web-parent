package com.hjy.entity.system;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: ScDefine <br>
 * 描述: 系统定义表 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月18日 上午9:06:24
 */
public class ScDefine extends BaseModel {

	/**
	 * 定义编号
	 */
	private String defineCode;

	/**
	 * 定义名称
	 */
	private String defineName;

	/**
	 * 父编号
	 */
	private String parentCode;

	public String getDefineCode() {
		return defineCode;
	}

	public void setDefineCode(String defineCode) {
		this.defineCode = defineCode;
	}

	public String getDefineName() {
		return defineName;
	}

	public void setDefineName(String defineName) {
		this.defineName = defineName;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

}