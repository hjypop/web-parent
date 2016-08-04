package com.hjy.response;


/**
 * @descriptions 响应基类。
 * 
 * @date 2016年8月2日上午11:37:00
 * @author Yangcl
 * @version 1.0.1
 */
public class ApiResponse {

	private int code;
	private String desc;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
