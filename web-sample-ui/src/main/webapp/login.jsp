<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <title>登录页面</title>

    <link rel="stylesheet" href="css/style.default.css" type="text/css" />
    <!--[if IE 9]>
        <link rel="stylesheet" media="screen" href="css/style.ie9.css"/>
    <![endif]-->
    <!--[if IE 8]>
        <link rel="stylesheet" media="screen" href="css/style.ie8.css"/>
    <![endif]-->
    <!--[if lt IE 9]>
	    <script src="js/plugins/css3-mediaqueries.js"></script>
    <![endif]-->
    <script type="text/javascript" src="js/jquery-1.9.1.js"></script>

    <!--页面JavaScript脚本-->
    <script type="text/javascript" src="js/system/login.js"></script>
    <script type="text/javascript" src="js/utils/ajaxs.js"></script>

</head>

<body class="loginpage">
	<div class="loginbox">
    	<div class="loginboxinner">
        	
            <div class="logo">
            	<h1 class="logo">$<span>惠家有</span>$</h1>
				<span class="slogan">后台管理系统</span>
            </div>
            
            <br clear="all" /><br />
            
            <div class="nousername">
				<div class="loginmsg">密码不正确.</div>
            </div>
            
            <div class="nopassword">
				<div class="loginmsg">密码不正确.</div>
                <div class="loginf">
                    <div class="thumb"><img alt="" src="images/thumbs/avatar1.png" /></div>
                    <div class="userlogged">
                        <h4></h4>
                        <a href="index.html">Not <span></span>?</a> 
                    </div>
                </div>
            </div>
            
            <form id="form-login">
                <div class="username">
                	<div class="usernameinner">
                    	<input type="text" name="userName" id="username" maxlength="20" value="a"/>
                    </div>
                </div>
                <div class="password">
                	<div class="passwordinner">
                    	<input type="password" name="password" id="password" maxlength="20" value="a"/>
                    </div>
                </div>
                <input type="button" class="button-login" value="登录" onclick="login.login('form-login')"/>
                
                <div class="keep"><input type="checkbox" /> 记住密码</div>
            </form>
            
        </div>
    </div>

</body>
</html>






















































