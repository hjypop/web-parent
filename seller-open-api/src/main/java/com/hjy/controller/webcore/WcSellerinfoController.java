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
		/**
		 * 店铺类型
		 */
		List<ScDefine> sellerType = scDefineService.findDefineByParentCode("44974639");
		/**
		 * 商户类型
		 */
		List<ScDefine> ucSellerType = scDefineService.findDefineByParentCode("449747810005");
		/**
		 * 
		 */
		model.put("sellerType", sellerType);
		model.put("ucSellerType", ucSellerType);
		return "jsp/seller/add";
	}

	/**
	 * 
	 * 方法: add <br>
	 * 描述: 商户信息-添加 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月17日 下午1:59:35
	 * 
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public JSONObject add(WcSellerinfo entity) {
		UserInfo user = (UserInfo) session.getAttribute("userInfo");
		JSONObject obj = new JSONObject();
		entity.setUid(WebHelper.getInstance().genUuid());
		entity.setSellerCode(WebHelper.getInstance().genUniqueCode("SI"));
		entity.setCreator(user.getUserName());
		entity.setCreateTime(DateUtil.getSysDateTimeString());
		entity.setUpdator(user.getUserName());
		entity.setUpdateTime(DateUtil.getSysDateTimeString());
		int result = service.insertSelective(entity);
		if (result >= 0) {
			obj.put("status", "success");
			obj.put("msg", "添加成功");
		} else {
			obj.put("status", "error");
			obj.put("msg", "添加失败");
		}
		return obj;
	}

	/**
	 * 
	 * 方法: add <br>
	 * 描述: 商户信息-编辑页面 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月17日 下午1:59:35
	 * 
	 * @return
	 */
	@RequestMapping("editindex")
	public String editIndex(String sellerCode, ModelMap model) {
		/**
		 * 商户类型
		 */
		List<ScDefine> sellerType = scDefineService.findDefineByParentCode("44974639");
		model.put("sellerType", sellerType);
		/**
		 * 商户类型
		 */
		List<ScDefine> ucSellerType = scDefineService.findDefineByParentCode("449747810005");
		model.put("ucSellerType", ucSellerType);
		WcSellerinfo info = service.selectBySellerCode(sellerCode);
		model.put("seller", info);
		return "jsp/seller/edit";
	}

	/**
	 * 
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
		UserInfo user = (UserInfo) session.getAttribute("userInfo");
		JSONObject obj = new JSONObject();
		entity.setUpdator(user.getUserName());
		entity.setUpdateTime(DateUtil.getSysDateTimeString());
		int result = service.updateSelective(entity);
		if (result >= 0) {
			obj.put("status", "success");
			obj.put("msg", "编辑成功");
		} else {
			obj.put("status", "error");
			obj.put("msg", "编辑失败");
		}
		return obj;
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
