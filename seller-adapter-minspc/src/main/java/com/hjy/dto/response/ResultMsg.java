package com.hjy.dto.response;

import com.hjy.entity.product.PcProductinfo;

public class ResultMsg {
	
	private String code;
	private String msg;
	private String type;
	private PcProductinfo entity;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public PcProductinfo getEntity() {
		return entity;
	}
	public void setEntity(PcProductinfo entity) {
		this.entity = entity;
	}
	
	
	
}
