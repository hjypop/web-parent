package com.hjy.request;

import com.hjy.base.IApiRequest;

/**
 * @descriptions 请求基类。
 * 
 * @param <T> 这是一个包含响应信息的泛型，它们都存放于com.hjy.response.data包中
 * @test open.api.request.RequestSample.requestMsgTest()
 *  
 * @date 2016年8月2日上午11:37:00
 * @author Yangcl
 * @version 1.0.1
 */
public class ApiRequest  <T extends IApiRequest>{
	private String code ;
	private String desc ;
//	private T data;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
