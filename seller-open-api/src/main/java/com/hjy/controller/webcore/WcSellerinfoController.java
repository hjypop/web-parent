package com.hjy.controller.webcore;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hjy.common.DateUtil;
import com.hjy.entity.system.ScDefine;
import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.helper.WebHelper;
import com.hjy.pojo.entity.login.UserInfo;
import com.hjy.service.system.IScDefineService;
import com.hjy.service.webcore.IWcSellerinfoService;
import com.hjy.system.ApiCacheVisitor;

/**
 * 
 * 类: WcSellerinfoController <br>
 * 描述: 商户信息controller <br>
 * 作者: zhy<br>
 * 时间: 2016年8月17日 下午1:57:50
 */
@Controller
@RequestMapping("seller")
public class WcSellerinfoController {

	@Autowired
	private HttpSession session;

	@Autowired
	private IWcSellerinfoService service;

	@Autowired
	private IScDefineService scDefineService;

	/**
	 * 
	 * 方法: index <br>
	 * 描述: 商户信息-列表 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月17日 下午1:58:42
	 * 
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index() {
		return "jsp/seller/index";
	}

	@RequestMapping(value = "ajaxPageData", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxPageData(WcSellerinfo entity, HttpServletRequest request) {
		return service.ajaxPageData(entity, request);
	}

	/**
	 * 
	 * 方法: add <br>
	 * 描述: 商户信息-添加页面 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月17日 下午1:59:35
	 * 
	 * @return
	 */
	@RequestMapping("addindex")
	public String addIndex(ModelMap model) { 
		// 商户类型 
		List<ScDefine> ucSellerType = scDefineService.findDefineByParentCode("44974747");
//		model.put("sellerType", sellerType);
		model.put("ucSellerType", ucSellerType);
		return "jsp/seller/add";
	}

	/**
	 * 方法: add <br>
	 * 描述: 商户信息-添加 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月17日 下午1:59:35
	 */
	@RequestMapping("add")
	@ResponseBody
	public JSONObject add(WcSellerinfo entity) {
		return service.insertWcSellerInfo(entity, session);
	}

	/**
	 * 描述: 商户信息-编辑页面 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月17日 下午1:59:35
	 * 
	 * @return
	 */
	@RequestMapping("editindex")
	public String editIndex(String sellerCode, ModelMap model) { 
		// 商户类型
		List<ScDefine> ucSellerType = scDefineService.findDefineByParentCode("44974747");
		model.put("ucSellerType", ucSellerType);
		WcSellerinfo info = service.selectBySellerCode(sellerCode);
		model.put("e", info);
		model.put("c", JSONObject.parseArray(info.getCommission()));
		return "jsp/seller/edit";
	}

	/**
	 * 方法: edit <br>
	 * 描述: 商户信息-编辑 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月17日 下午1:59:59
	 * 
	 * @return
	 */
	@RequestMapping("edit")
	@ResponseBody
	public JSONObject edit(WcSellerinfo entity) {
		return service.updateWcSellerInfo(entity, session);
	}
	
	/**
	 * @description: 取出open-api列表并关联该商户有的api
	 * 
	 * @param sellerCode
	 * @author Yangcl 
	 * @date 2016年12月26日 下午4:37:36 
	 * @version 1.0.0.1
	 */
	@RequestMapping("soalist") 
	@ResponseBody
	public JSONObject sellerOpenapiList(String sellerCode) {
		return service.sellerOpenapiList(sellerCode);
	}

	/**
	 * 
	 * 方法: del <br>
	 * 描述: 商户信息-删除 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月17日 下午2:00:23
	 * 
	 * @return
	 */
	@RequestMapping("del")
	@ResponseBody
	public JSONObject del(String sellerCode) {
		JSONObject obj = new JSONObject();
		int result = service.deleteBySellerCode(sellerCode);
		if (result >= 0) {
			obj.put("status", "success");
			obj.put("msg", "删除成功");
		} else {
			obj.put("status", "error");
			obj.put("msg", "删除失败");
		}
		return obj;
	}
}
