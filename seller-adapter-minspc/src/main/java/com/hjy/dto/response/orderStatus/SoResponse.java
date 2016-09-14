package com.hjy.dto.response.orderStatus;

import java.util.List;

public class SoResponse {
	private String Code;
	private String Desc;
	private List<DataResponse> Data;
	
	
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	public List<DataResponse> getData() {
		return Data;
	}
	public void setData(List<DataResponse> data) {
		Data = data;
	}
	
	
}
