package com.sample.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.controller.BaseController;
import com.hjy.entity.login.UserInfo;
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

}































