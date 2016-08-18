package com.hjy.controller.webcore;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.dto.webcore.WcSellerinfoDto;
import com.hjy.entity.system.ScDefine;
import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.helper.WebHelper;
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
@RequestMapping("/seller/")
public class WcSellerinfoController {

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
	@RequestMapping(value = "index", produces = { "application/json;charset=utf-8" })
	public String index(WcSellerinfoDto dto, ModelMap model) {
		/*
		 * 如果分页参数当前页为空，默认为0，页面最大显示数为空，默认为10
		 */
		String sortString = "create_time.desc";
		Order.formString(sortString);
		PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
		WcSellerinfo entity = new WcSellerinfo();
		entity.setSellerCode(dto.getSellerCode());
		String name = "";
		if (dto.getSellerName() != null && !"".equals(dto.getSellerName())) {
			try {
				name = new String(dto.getSellerName().getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		entity.setSellerName(name);
		List<WcSellerinfo> list = service.queryPage(entity);
		if (list != null && list.size() > 0) {
			PageInfo<WcSellerinfo> pageList = new PageInfo<WcSellerinfo>(list);
			model.put("pageList", pageList);
		}
		model.put("seller", entity);
		return "jsp/seller/index";
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
		 * 商户状态
		 */
		List<ScDefine> sellerStatus = scDefineService.findDefineByParentCode("449717230004");
		model.put("sellerStatus", sellerStatus);
		/**
		 * 商户类型
		 */
		List<ScDefine> sellerType = scDefineService.findDefineByParentCode("44974639");
		model.put("sellerType", sellerType);
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
		JSONObject obj = new JSONObject();
		entity.setUid(WebHelper.getInstance().genUuid());
		entity.setSellerCode(WebHelper.getInstance().genUniqueCode("SI"));
		entity.setCreator("system");
		entity.setCreateTime(new Date());
		entity.setUpdator("system");
		entity.setUpdateTime(new Date());
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
		 * 商户状态
		 */
		List<ScDefine> sellerStatus = scDefineService.findDefineByParentCode("449717230004");
		model.put("sellerStatus", sellerStatus);
		/**
		 * 商户类型
		 */
		List<ScDefine> sellerType = scDefineService.findDefineByParentCode("44974639");
		model.put("sellerType", sellerType);
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
		JSONObject obj = new JSONObject();
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
