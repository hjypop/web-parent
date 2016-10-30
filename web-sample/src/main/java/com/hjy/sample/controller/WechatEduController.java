package com.hjy.sample.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @descriptions wechat edu controller
 * 
 * @author Yangcl
 * @date 2016年5月28日-下午4:40:04
 * @version 1.0.0
 */
@Controller
@RequestMapping("wechat")
public class WechatEduController {
	private static Logger logger=Logger.getLogger(WechatEduController.class);
	
	
	@RequestMapping("gesture_sign_in")
	public String toGestureSignIn(HttpSession session){ 
		
		return "jsp/wechat/gestureSignIn";  
	}
	
}



























