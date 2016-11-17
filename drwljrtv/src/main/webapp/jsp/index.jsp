<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/commons/header.jsp"%>
	<header class="wrapper">
		<a class="left"></a> <a></a>
		<h2>首页</h2>
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
				<c:if test="${c.categoryId!=154 }">
					<li>
						<a href="category/subindex.do?categoryId=${c.categoryId}&categoryName=${c.categoryName}">
							<span><img src="${c.thumb }"></span>
							<strong>${c.categoryName }</strong>
						</a>
					</li>
				</c:if>
			</c:forEach>
		</ul>
		<div class="listVideo">
			<c:forEach var="c" items="${categoryVideos}">
				<div class="title">
					<h2>
						<a href="category/subindex.do?categoryId=${c.categoryId}&categoryName=${c.categoryName}">
							${c.categoryName}
						</a>
					</h2>
				</div>
				<ul class="list clearfix">
					<c:forEach var="v" items="${c.videos}">
						<li>
							<a href="video/detail.do?videoId=${v.videoid }">
								<span><img src="${v.thumb }"></span>
								<p>${v.title }</p>
								<strong>${v.views }人想看</strong>
							</a>
						</li>
					</c:forEach>
				</ul>
			</c:forEach>
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