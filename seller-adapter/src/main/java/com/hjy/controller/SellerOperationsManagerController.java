package com.hjy.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hjy.service.operations.ISellerOperationService;

/**
 * @description: 商户问题维护
 * 	执行运营人员的线上临时需求，不对外开放。
 * 
 * @author Yangcl
 * @date 2017年1月5日 上午10:00:45 
 * @version 1.0.0
 */
@Controller
@RequestMapping("adapter")
public class SellerOperationsManagerController {
	private static Logger logger=Logger.getLogger(SellerOperationsManagerController.class);
	
	@Autowired
	private ISellerOperationService service;
	
	@RequestMapping(value = "validate")
	public String validate() { 
		return "jsp/sblhr/validate";
	}
	@RequestMapping(value = "index")
	public String index(ModelMap model , String key, HttpSession session) { 
		if(key.equals("hello world")){ 
			session.setAttribute("seller-operation-key", "seller-operation-key"); // 写入session
			return "redirect:/jsp/sblhr/index.jsp";  
		}else{ 
			return "redirect:/jsp/sblhr/validate.jsp";
		} 
	}
	// 离开此页面
	@RequestMapping(value = "leave")
	public String leave(HttpSession session ) { 
		session.setAttribute("seller-operation-key", null); // 删除session
		return "redirect:/jsp/sblhr/validate.jsp";    
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value = "funcOne", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject funcOne(String json, HttpSession session){
		return service.funcOne(json , session);
	}
	
	@RequestMapping(value = "funcTwo", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject funcTwo(String json, HttpSession session){
		return service.funcTwo(json , session);
	}
	
}





































