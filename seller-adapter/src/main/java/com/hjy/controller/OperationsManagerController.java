package com.hjy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
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
@RequestMapping("operations")
public class OperationsManagerController {

	
	@Autowired
	private IOperationsManagerService service;
	
	
	
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
