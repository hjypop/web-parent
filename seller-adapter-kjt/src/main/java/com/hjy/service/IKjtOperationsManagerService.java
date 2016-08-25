package com.hjy.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public interface IKjtOperationsManagerService {
	public JSONObject funcOne(List<String> list);

	/**
	 * @descriptions 
	 * 
	 * @param s 开始时间
	 * @param e 结束时间
	 * @date 2016年8月25日下午12:46:15
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject funcTwo(String s, String e); 
	
	public JSONObject funcThree(String execTime , String remark); 
}
