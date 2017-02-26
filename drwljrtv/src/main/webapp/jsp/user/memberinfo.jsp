<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/commons/header.jsp"%>
	<header class="wrapper">
		<a class="left"><img src="assets/img/lBtn1.png"></a> <a class="right"><img
			src="assets/img/close.png"></a> <span>你的地盘你做主，做主怎能不登录</span>
	</header>
	<div class="wrapper main">
		<div class="myTitle clearfix">
			<a class="MyHead"><img
				src="assets/img/01_index_list_topic_icon_icon.png"></a>
			<div class="left denglu">
				<a href="user/loginindex.do">登录/注册</a> <a>
					<!--<i><img src="img/01_index_bottom_bar_icon_subject.png"></i>-->开通会员
				</a>
			</div>
			<div class="zipindao">
				<i><img src="assets/img/zipindao.png"></i><a>自频道></a>
			</div>
		</div>
		<ul class="MyList clearfix">
			<li class="active"><a> <i><img src="assets/img/xiaoxi.jpg"></i>
					<span>消息中心</span> <strong>></strong>
			</a></li>
		</ul>
		<ul class="MyList clearfix">
			<li><a> <i><img src="assets/img/xiazai.jpg"></i> <span>我的缓存</span>
					<strong>></strong>
			</a></li>
			<li><a> <i><img src="assets/img/shijian.jpg"></i> <span>观看记录</span>
					<strong>></strong>
			</a></li>
			<li><a> <i><img src="assets/img/shoucang.jpg"></i> <span>我的收藏</span>
					<strong>></strong>
			</a></li>
			<li><a> <i><img src="assets/img/dingyue.jpg"></i> <span>我的订阅</span>
					<strong>></strong>
			</a></li>
			<li><a> <i><img src="assets/img/dingdan.jpg"></i> <span>我的订单</span>
					<strong>></strong>
			</a></li>
		</ul>

	</div>

	<!--导航-->
	<nav class="wrapper clearfix">
		<a href="index.do"> <i><img
				src="assets/img/01_index_bottom_bar_icon_homepage.png"></i> 首页
		</a> <a href="category/index.do"> <i><img
				src="assets/img/01_index_bottom_bar_icon_information.png"></i> 频道
		</a> <a  href="category/subscription.do"> <i><img
				src="assets/img/01_index_bottom_bar_icon_subject.png"></i> 订阅
		</a> <a class="active" href="user/memberinfo.do"> <i><img
				src="assets/img/01_index_bottom_bar_icon_review.png"></i> vip会员
		</a> <a  href="user/info.do"> <i><img
				src="assets/img/01_index_bottom_bar_icon_topic.png"></i> 我的
		</a>
	</nav>
</body>

</html>