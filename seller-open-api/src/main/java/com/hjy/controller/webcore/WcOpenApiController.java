package com.hjy.controller.webcore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hjy.common.DateUtil;
import com.hjy.entity.webcore.WcOpenApi;
import com.hjy.helper.WebHelper;
import com.hjy.pojo.entity.login.UserInfo;
import com.hjy.service.webcore.IWcOpenApiService;

/**
 * 
 * 类: WcOpenapi <br>
 * 描述: openApi接口<br>
 * 作者: zhy<br>
 * 时间: 2016年8月25日 上午8:52:45
 */
@Controller
@RequestMapping("openapi")
public class WcOpenApiController {

	@Autowired
	private HttpSession session;
	@Autowired
	private IWcOpenApiService service;

	/**
	 * 
	 * 方法: index <br>
	 * 描述: 接口列表页 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月25日 上午9:48:03
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String index() {
		return "jsp/api/index";
	}

	/**
	 * 
	 * 方法: ajaxPageData <br>
	 * 描述: 分页 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月25日 上午10:06:07
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "ajaxPageData", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxPageData(WcOpenApi entity, HttpServletRequest request) {
		return service.ajaxPageData(entity, request);
	}

	/**
	 * 方法: add <br>
	 * 描述: 添加接口 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月25日 上午10:06:42
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public JSONObject add(WcOpenApi entity) {
		return service.insertWcOpenApi(entity, session); 
	}

	/**
	 * 方法: edit <br>
	 * 描述: 编辑接口 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月25日 上午10:07:15
	 * @return
	 */
	@RequestMapping("edit")
	@ResponseBody
	public JSONObject edit(WcOpenApi entity) {
		return service.updateWcOpenApi(entity, session);
	}

	@RequestMapping("del")
	@ResponseBody
	public JSONObject del(String apiCode) {
		return service.deleteApiCode(apiCode);
	}
}















