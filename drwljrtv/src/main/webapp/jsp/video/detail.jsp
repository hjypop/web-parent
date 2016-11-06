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
</head>

<body>
	<header class="wrapper">
		<a href="javascript:history.back()" class="left">返回</a> <a></a>
		<h2>首页</h2>
	</header>
	<div class="wrapper main">
		<video width="100%;" height="" controls poster="${video.big_thumbs}">
			<source src="${video.video_href }" type="video/mp4"></source>
		</video>

		<h3 class="videoTitle">${video.title }</h3>
		<p class="videoTxt">${video.description }</p>
	</div>


	<!--导航-->
	<nav class="wrapper clearfix">
		<a class="active"> <i><img
				src="assets/img/01_index_bottom_bar_icon_homepage.png"></i> 首页
		</a> <a href="category/index.do"> <i><img
				src="assets/img/01_index_bottom_bar_icon_information.png"></i> 频道
		</a> <a href="category/subscription.do"> <i><img
				src="assets/img/01_index_bottom_bar_icon_subject.png"></i> 订阅
		</a> <a href="user/memberinfo.do"> <i><img
				src="assets/img/01_index_bottom_bar_icon_review.png"></i> vip会员
		</a> <a href="user/info.do"> <i><img
				src="assets/img/01_index_bottom_bar_icon_topic.png"></i> 我的
		</a>
	</nav>


</body>

</html>