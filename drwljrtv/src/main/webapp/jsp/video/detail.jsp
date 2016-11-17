<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/commons/header.jsp"%>
	<header class="wrapper">
		<a href="javascript:history.back()" class="left">返回</a> <a></a>
		<h2>首页</h2>
	</header>
	<div class="wrapper main">
		<video width="100%;" height="" controls poster="${video.bigThumb}">
			<source src="${video.videoHref }" type="video/mp4"></source>
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