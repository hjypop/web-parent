package com.hjy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.dto.QueryKjtLog;
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
	 * @descriptions 跨境通问题查询
	 *  
	 * @date 2016年8月31日下午4:27:17
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "query")
	public String query() { 
		return "jsp/sbkjt/questionQuery";
	}
	
	
	@RequestMapping(value = "queryKjtlog", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject queryKjtlog(QueryKjtLog dto){
		return kjtService.queryKjtlog(dto); 
	}
	
	
	
	
	
	
	
	
	
	
	// ##########################################################################################################
 
	/**
	 * @descriptions 跨境通线上临时问题解决页面
	 *  
	 * @date 2016年8月25日上午10:16:07
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "validate")
	public String validate() { 
		return "jsp/sbkjt/validate";
	}
	  
	@RequestMapping(value = "index")
	public String index(ModelMap model , String key, HttpSession session ) { 
		if(key.equals("whosyourdaddy")){ 
			session.setAttribute("kjt-key", "kjt-key"); // 写入session
			return "redirect:/jsp/sbkjt/index.jsp";    
		}else{ 
			return "redirect:/jsp/sbkjt/validate.jsp";
		} 
	}
	
	// 离开此页面
	@RequestMapping(value = "leave")
	public String leave(HttpSession session ) { 
		session.setAttribute("kjt-key", null); // 删除session
		return "redirect:/jsp/sbkjt/validate.jsp";    
	}
	
	
	
	
	@RequestMapping(value = "funcOne", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject funcOne(String json, HttpSession session){
		return kjtService.funcOne(json , session); 
	}
	
	@RequestMapping(value = "funcTwo", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject funcTwo(String s , String e, HttpSession session){
		return kjtService.funcTwo(s , e , session); 
	}
	
	@RequestMapping(value = "funcThree", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject funcThree(String execTime , String remark, HttpSession session){
		return kjtService.funcThree(execTime , remark , session);  
	}
	
	@RequestMapping(value = "funcFour", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject funcFour(String json, HttpSession session){
		return kjtService.funcFour(json , session); 
	}
	
	@RequestMapping(value = "funcFive", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject funcFive(String json, HttpSession session){
		return kjtService.funcFive(json , session); 
	}
	
	/**
	 * @descriptions 批量刷新商品缓存
	 *  
	 * @date 2016年8月31日下午5:56:37
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "funcSix", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject funcSix(String json, HttpSession session){
		return kjtService.funcSix(json , session); 
	}
	
	
	/**
	 * @description: 跨境通产品批量下架 
	 * 
	 * @param json
	 * @param session
	 * @return
	 * @author Yangcl 
	 * @date 2016年10月9日 下午2:20:25 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "funcSeven", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject funcSeven(String json , String status , String reason , HttpSession session){
		
		return kjtService.funcSeven(json, status, reason, session); 
	}
}









