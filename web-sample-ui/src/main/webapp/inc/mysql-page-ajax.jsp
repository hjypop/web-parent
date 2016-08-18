<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*" %>
<% 
	// http://localhost:8080/springMatrix/
    String bpath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	
	//  /user/getListForPage.do?  取出Controller的请求路径 注意user前有斜杠 
	String pageUrl = request.getAttribute( "javax.servlet.forward.servlet_path").toString() + "?"; 
    
    String method = request.getMethod().toUpperCase();   //  GET or POST 获取form的提交方法
    if ("GET".equals(method) && null != request.getQueryString()) {
        String[] values = request.getQueryString().split("&");
        for (int i = 0; i < values.length; i++) {
            if (null != values[i] && values[i].length() > 0
                    && !("pageNum").equals(values[i].substring(0,
                    values[i].indexOf("=")))) {
                pageUrl += values[i] + "&";
            }
        }
    }else if("POST".equals(method) && null != request.getParameterNames()) {
        Enumeration pageEnum = request.getParameterNames(); // request.getParameterNames() 封装了form表单中所要提交的参数
        while (pageEnum.hasMoreElements())   // 
        {
            String paramName = (String) pageEnum.nextElement();
            String[] values = request.getParameterValues(paramName);
            for (int i = 0; i < values.length; i++) {
                if (null != values[i] && values[i].length() > 0 && !("pageNum").equals(paramName) && !("pageSize").equals(paramName)) {
                    pageUrl += paramName + "=" + values[i] + "&";
                }
            }
        }
    } 
    pageUrl = pageUrl.substring(1); // 去掉第一个斜杠 '/'        如 "user/getListForPage.do?address=亮晶晶之家&"

    pageContext.setAttribute("bpath", bpath + pageUrl);
    // pageContext.setAttribute("pageUrl", pageUrl);
     
%>

  


























