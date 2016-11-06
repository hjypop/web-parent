package com.drwljrtv.controller.user;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.drwljrtv.request.user.UserRequest;
import com.drwljrtv.service.user.IUserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private IUserService service;

	@RequestMapping("loginindex")
	public String loginIndex() {
		return "jsp/user/login";
	}

	@RequestMapping("login")
	@ResponseBody
	public JSONObject login(UserRequest request, HttpSession session) {
		JSONObject obj = service.login(request);
		if (StringUtils.equals(obj.getString("state"), "ok")) {
			JSONObject data = obj.getJSONObject("data");
			session.setAttribute(data.getString("token"), data);
		}
		return obj;
	}

	@RequestMapping("registerindex")
	public String registerIndex() {
		return "jsp/user/register";
	}

	@RequestMapping("register")
	@ResponseBody
	public JSONObject register(UserRequest request, HttpSession session) {
		JSONObject obj = service.register(request);
		return obj;
	}
	@RequestMapping("info")
	public String info(){
		return "jsp/user/info";
	}
	
	@RequestMapping("memberinfo")
	public String memberinfo(){
		return "jsp/user/memberinfo";
	}	

}
