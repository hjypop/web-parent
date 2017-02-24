/**
 * 用户相关js操作
 */
var User = {
	login : function() {
		// 用户登录
		var param = $("#login").serialize();
		$.ajax({
			url : "user/login.do",
			type : "POST",
			data : param,
			success : function(data) {
				data = JSON.parse(data);
				if (data.state == 'ok') {
					window.open("index.do", "_self");
				} else {
					alert(data.msg);
				}
			},
			error : function(data) {
				alert("请联系管理员");
			}
		});
	},
	register : function() {
		// 用户注册
		var param = $("#register").serialize();
		$.ajax({
			url : "user/register.do",
			type : "POST",
			data : param,
			success : function(data) {
				data = JSON.parse(data);
				if (data.state == 'ok') {
					window.open("index.do", "_self");
					//window.open("loginindex.do", "_self");
				} else {
					alert(data.msg);
				}
			},
			error : function(data) {
				alert("请联系管理员");
			}
		});
	}
};