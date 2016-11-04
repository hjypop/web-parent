package com.core.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.core.pojo.entity.login.UserInfo;
import com.core.service.ILoginService;

/**
 * @descriptions 登录与退出相关控制类
 * 
 * @date 2016年5月20日上午10:34:27
 * @author Yangcl
 * @version 1.0.1
 */
@Controller
@RequestMapping("login")
public class LoginController extends BaseController {
//	private static Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private ILoginService loginService;

	/**
	 * @descriptions 验证用户登录信息
	 * 
	 * @param info
	 * @return
	 * @refactor no
	 * @date 2016年5月20日上午11:34:34
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "login", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject login(UserInfo info, HttpSession session) {
		getLogger().logInfo(info.getUserName() + " login ... ");

		return loginService.login(info, session);
	}

	/**
	 * @descriptions 登录验证完成后跳转到指定页面
	 * 
	 * @return
	 * @refactor no
	 * @date 2016年5月20日上午11:40:43
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
	@RequestMapping("index")
	public String loginPageIndex() {

		getLogger().logInfo(" to index.jsp  ... ");

		return "redirect:/index.jsp";
	}
}
