package com.hjy.entity.system;

import com.hjy.base.BaseModel;

public class ScTmp extends BaseModel {

	private String code;

	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
}