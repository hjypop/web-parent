package com.hjy.sample.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.controller.BaseController;
import com.hjy.entity.login.UserInfo;
import com.hjy.entity.system.Company;
import com.hjy.service.ICompanyService;
import com.hjy.service.ILoginService;
import com.hjy.service.IUserInfoService;

/**
 * @descriptions 所有【示例】相关的后台方法都在这里 
 * 
 * @author Yangcl
 * @date 2016年5月28日-下午4:40:04
 * @version 1.0.0
 */
@Controller
@RequestMapping("example")
public class ExampleController extends BaseController {
private static Logger logger=Logger.getLogger(ExampleController.class);
	
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private ICompanyService companyService;
	
	
	/**
	 * @descriptions 前往添加页面 addExample.jsp
	 * 
	 * @param session
	 * @return
	 * @author Yangcl
	 * @date 2016年6月13日-下午10:53:55
	 * @version 1.0.0.1
	 */
	@RequestMapping("addInfoPage")
	public String toAddPage(HttpSession session){ 
		// TODO 按钮权限控制等等
		return "jsp/example/addExample"; 
	}
	
	/**
	 * @descriptions 添加一条信息到数据库，成功后跳转回添加页面
	 * 
	 * @param entity
	 * @return
	 * @author Yangcl
	 * @date 2016年6月13日-下午11:04:45
	 * @version 1.0.0.1
	 */
	@RequestMapping("addInfo")
	public String addInfo(Company entity , ModelMap model , HttpSession session){ 
		if(StringUtils.isNotBlank(entity.getCompanyName()) && StringUtils.isNotBlank(entity.getRemark())){
			entity.setFlag(1);
			UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
			entity.setCreateTime(new Date());
			entity.setCreateUserId( userInfo.getId() ); 
			entity.setUpdateTime(new Date());
			entity.setUpdateUserId(userInfo.getId()); 
			
			Integer count = companyService.insertSelective(entity);
			if(count == 1){
				model.put("status", true);
				model.put("msg", "数据插入成功！");
			}else{
				model.put("status", false);
				model.put("msg", "数据插入异常！");
			}
		}else{
			model.put("status", false);
			model.put("msg", "数据不可为空！");
		}
		
		return "jsp/example/addExample"; 
	}
	
	
	/**
	 * @descriptions 简单分页示例 不涉及弹窗分页问题 对应 pageFormExample.jsp
	 * 
	 * @param info
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @refactor no
	 * @date 2016年6月3日下午7:27:51
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping("pageFormExample")   
	public String toPageFormExample(UserInfo info , ModelMap model , HttpServletRequest request, HttpSession session){ 
		logger.info(" to pageFormExample.jsp  ... "); 
		
		String pageNum = request.getParameter("pageNum"); // 当前第几页
		String pageSize = request.getParameter("pageSize");  // 当前页所显示记录条数
		
		int num = 1;
		int size = 10;
		if (StringUtils.isNotBlank(pageNum)) {
			num = Integer.parseInt(pageNum);
		}
		if (StringUtils.isNotBlank(pageSize)) {
			size = Integer.parseInt(pageSize);
		}
		
		UserInfo entity = new UserInfo();
		entity.setSex(1); 
		String sortString = "id.desc";
		Order.formString(sortString);
		PageHelper.startPage(num, size);
		List<UserInfo> userList = userInfoService.queryPage(entity);
		if(userList != null && userList.size() > 0){
			PageInfo<UserInfo> pageList = new PageInfo<UserInfo>(userList);
			model.put("pageList", pageList); 
			model.put("status", true);  // 为了开发标准化 status标记必须添加 
		}else{
			model.put("status", false); 
		}
		
		
		return "jsp/example/pageFormExample"; 
	}

	
	@RequestMapping(value = "deleteOne", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject deleteOne(UserInfo info){
		return userInfoService.deleteOne(info); 
	}
	
	@RequestMapping("editInfoPage")   
	public String editInfoPage(UserInfo info , ModelMap model , HttpServletRequest request, HttpSession session){
		if(StringUtils.isNotBlank(info.getId().toString())){
			UserInfo entity = userInfoService.find(info.getId());
			if(entity != null){
				model.addAttribute("entity", entity);
			}
		}
		
		return "jsp/example/editExample"; 
	}
	
	@RequestMapping(value = "editInfo", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject editInfo(UserInfo info){
		return userInfoService.editInfo(info);
	}

}































