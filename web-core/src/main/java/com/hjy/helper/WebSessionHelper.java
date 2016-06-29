package com.hjy.helper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.hjy.constant.WebConst;
import com.hjy.model.MDataMap;
import com.hjy.model.MUserInfo;

public class WebSessionHelper{ // implements IBaseHelper, IBaseCreate 

	public static WebSessionHelper create() {
		return new WebSessionHelper();
	}

	public WebSessionHelper() {
		super();
	}
	
	private HttpServletRequest request = null;

	/**
	 * 获取HttpRequest
	 * 
	 * @return
	 */
	public HttpServletRequest upHttpRequest() {
		if (request == null) {
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		}
		return request;
	}

	/**
	 * 插入Session
	 * 
	 * @param sKey
	 * @param oValue
	 */
	public void inSession(String sKey, Object oValue) {
		upHttpRequest().getSession().setAttribute(WebConst.CONST_WEB_SESSION_KEY + sKey, oValue);
	}
	
	/**
	 * 获取session
	 * 
	 * @param sKey
	 * @return
	 */
	public Object upSession(String sKey) {
		Object oReturnObject = upHttpRequest().getSession().getAttribute(WebConst.CONST_WEB_SESSION_KEY + sKey);
		return oReturnObject;
	}
	
	/**
	 * 获取Cookie值
	 * 
	 * @param sKey
	 * @return
	 */
	public String upCookie(String sKey) {
		String sReturn = null;
		Cookie[] allCookies = upHttpRequest().getCookies();
		if (allCookies != null) {
			for (Cookie cookie : allCookies) {
				if (cookie.getName().equals(WebConst.CONST_WEB_COOKIE_KEY + sKey)) {
					sReturn = cookie.getValue();
				}
			}
		}
		return sReturn;
	}

	
	
	
	
	public MUserInfo upSessionUser() {
		
		String sCookieUser = WebSessionHelper.create().upCookie(WebConst.CONST_WEB_SESSION_USER);
		String userStr=KvUp.upDefault().get(WebConst.CONST_WEB_SESSION_KEY+WebConst.CONST_WEB_SESSION_USER+"-"+sCookieUser);
		
		MUserInfo mUserInfo = null;
		if (StringUtils.isNotBlank(userStr)) {
			
			mUserInfo = JSON.parseObject(userStr, MUserInfo.class);
			
		}

		return mUserInfo;
	}

	

	/**
	 * 获取request的值
	 * 
	 * @param sKey
	 * @return
	 */
	public String upRequest(String sKey) {
		return StringUtils.defaultIfBlank(upHttpRequest().getParameter(sKey),
				"").trim();
	}

	/*
	 * 获取上一个请求的url
	 */
	public String upReferer() {
		return StringUtils.defaultIfBlank(upHttpRequest().getHeader("Referer"),
				"");
	}

	/**
	 * 获取客户端IP地址
	 * 
	 * @return
	 */
	public String upIpaddress() {

		String ip = upHttpRequest().getHeader("X-Real-IP");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = upHttpRequest().getHeader("x-forwarded-for");
			/*
			 * if (ip == null || ip.length() == 0 ||
			 * "unknown".equalsIgnoreCase(ip)) { ip =
			 * request.getHeader("Proxy-Client-IP"); } if (ip == null ||
			 * ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { ip =
			 * request.getHeader("WL-Proxy-Client-IP"); }
			 */
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		}
		return ip;

	}

	public String upReplaceUrl(String sUrl, String... sReplace) {

		if (StringUtils.isEmpty(sUrl)) {
			sUrl = upHttpRequest().getRequestURL().toString();
		}

		String sLeft = StringUtils.substringBefore(sUrl, "?");

		String sRight = StringUtils.substringAfter(sUrl, "?");

		MDataMap mQueryMap = new MDataMap();
		mQueryMap.inUrlParams(sRight);

		for (int i = 0, j = sReplace.length; i < j; i = i + 2) {
			mQueryMap.put(sReplace[i], sReplace[i + 1]);
		}

		List<String> lParams = new ArrayList<String>();
		for (String sKey : mQueryMap.keySet()) {
			if (StringUtils.isNotBlank(sKey))
				lParams.add(sKey + "=" + mQueryMap.get(sKey));
		}

		sUrl = sLeft + "?" + StringUtils.join(lParams, "&");

		return sUrl;
	}

}
