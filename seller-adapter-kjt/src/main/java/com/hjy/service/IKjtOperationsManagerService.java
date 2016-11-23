package com.hjy.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.hjy.dto.QueryKjtLog;

public interface IKjtOperationsManagerService {
	
	
	public JSONObject funcOne(String json , HttpSession session);

	/**
	 * @descriptions 
	 * 
	 * @param s 开始时间
	 * @param e 结束时间
	 * @date 2016年8月25日下午12:46:15
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject funcTwo(String s, String e, HttpSession session); 
	
	public JSONObject funcThree(String execTime , String remark, HttpSession session);

	public JSONObject funcFour(String json, HttpSession session);

	public JSONObject funcFive(String json, HttpSession session);

	public JSONObject funcSix(String json, HttpSession session);

	public JSONObject queryKjtlog(QueryKjtLog dto);     
	
	
	public JSONObject funcSeven(String json, String productStatus , String reason , HttpSession session);

	public JSONObject funcThreePlus(String uuid, HttpSession session); 
	
}













