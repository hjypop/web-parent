<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<a class="left"></a> <a></a>
		<h2>
			首页
			</h1>
	</header>
	<div class="wrapper main">
		<div class="banner">
			<div class="swiper-wrapper">
				<c:forEach var="s" items="${shuffling}">
					<div class="swiper-slide">
						<a href="video/detail.do?videoId=${s.videoid }">
							<img src="${s.image}">
						</a>
					</div> 
				</c:forEach>
			</div>
			<!-- Add Pagination -->
			<div class="swiper-pagination"></div>
		</div>
		<ul id="category" class="pinList clearfix">
			<c:forEach var="c" items="${category}">
				<li>
					<a href="category/subindex.do?categoryId=${c.category_id}">
						<span><img src="${c.thumb }"></span>
						<strong>${c.category_name }</strong>
					</a>
				</li>
			</c:forEach>
		</ul>
		<div class="listVideo">
			<div class="title">
				<h2>视频</h2>
			</div>
			<ul class="list clearfix">
				<c:forEach var="v" items="${video}">
					<li>
						<a href="video/detail.do?videoId=${v.videoid }">
							<span><img src="${v.thumb }"></span>
							<p>${v.title }</p>
							<strong>${v.views }人想看</strong>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>

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