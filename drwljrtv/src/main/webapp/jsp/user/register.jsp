<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">

<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<title></title>
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" href="assets/css/swiper.min.css" />
<link rel="stylesheet" href="assets/css/animate.min.css" />
<link rel="stylesheet" href="assets/css/base.css" />
<script type="text/javascript" src="assets/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="assets/js/swiper.jquery.min.js"></script>
<script type="text/javascript" src="assets/js/fastclick.js"></script>
<script type="text/javascript" src="assets/js/base.js"></script>
<script type="text/javascript" src="assets/js/user.js"></script>
</head>

<body>
	<header class="wrapper">
		<a href="javascript:history.back()" class="left">返回</a> <a
			class="right" href="login.do">登录</a>
		<h2>首页</h2>
	</header>
	<div class="wrapper main">
		<div class="login">
			<h3 class="loginTitle">欢迎登陆</h3>
			<form action="" method="post">
				<ul class="LoginInput clearfix">
					<li><label>手机号：</label> <input type="tel" name="username"
						id="username" value="" placeholder="输入您的手机号" /> <a href="">获取验证码</a>
					</li>
					<li><label>验证码：</label> <input type="text" name="authCode"
						id="authCode" value="" placeholder="输入手机验证码" /></li>
					<li class="bor"><label>密码：</label> <input type="password"
						name="password" id="password" value="" placeholder="密码由字母、数字、符号混合" />
					</li>
				</ul>
				<a class="loginBtn">注册</a>
				<p class="loginTip">
					<input type="checkbox" name="" id="" value="" />我已阅读并接受注册协议>
				</p>
			</form>
		</div>
	</div>
</body>
</html>