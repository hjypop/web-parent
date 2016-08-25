package com.hjy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.request.data.OrderInfoStatus;
import com.hjy.service.IKjtOperationsManagerService;
import com.hjy.service.operations.IOperationsManagerService;

/**
 * @descriptions 执行运营人员的线上临时需求，如：一批商品下架、一批商品上架等等。
 * 这个类中的所有接口全部用于处理运营人员的线上临时需求，不对外开放。
 * 
 * @date 2016年8月15日下午5:08:01
 * @author Yangcl
 * @version 1.0.1
 */
@Controller
@RequestMapping("kjt")
public class KjtOperationsManagerController {
	
	private static Logger logger=Logger.getLogger(KjtOperationsManagerController.class);
	
	
	@Autowired
	private IOperationsManagerService service;
	
	@Autowired
	private IKjtOperationsManagerService kjtService;
	
	
	/**
	 * @descriptions 跨境通商品批量上架
	 *  
	 * @date 2016年8月15日下午5:10:43
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "up_storage", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject upStorage(){
		return  service.upStorage("");
	}
	
	
	
	
 
	/**
	 * @descriptions 跨境通线上临时问题解决页面
	 *  
	 * @date 2016年8月25日上午10:16:07
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "index")
	public String index() { 
		return "jsp/sbkjt/index";
	}
	
	@RequestMapping(value = "funcOne", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject funcOne(String json){
		List<String> list = JSON.parseArray(json, String.class);
		return kjtService.funcOne(list); 
	}
	
	@RequestMapping(value = "funcTwo", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject funcTwo(String s , String e){
		return kjtService.funcTwo(s , e); 
	}
	
	@RequestMapping(value = "funcThree", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject funcThree(String execTime , String remark){
		return kjtService.funcThree(execTime , remark);  
	}
	
	
	
	
	
	
}









