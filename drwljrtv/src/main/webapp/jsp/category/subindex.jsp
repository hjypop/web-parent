<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/commons/header.jsp"%>
		<header class="wrapper">
			<a href="javascript:history.back()" class="left">返回</a>
			<a></a>
			<h2>${categoryName}</h2>
		</header>
		<div class="wrapper main">
			<c:if test="${fn:length(category)>0}">  
				<ul class="pinList clearfix">
					<c:forEach var="c" items="${category}">
						<li>
							<a href="category/subindex.do?categoryId=${c.categoryId}&categoryName=${c.categoryName}">
								<span><img src="${c.thumb }"></span>
								<strong>${c.categoryName }</strong>
							</a>
						</li>
					</c:forEach>
				</ul>
			</c:if>
			<div class="listVideo">
				<div class="title">
					<h2>视频</h2>
				</div>
				<ul class="list clearfix">
					<c:forEach var="v" items="${vidoes}">
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
		<a href="index.do"> <i><img
				src="assets/img/01_index_bottom_bar_icon_homepage.png"></i> 首页
		</a> <a class="active"  href="category/index.do"> <i><img
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