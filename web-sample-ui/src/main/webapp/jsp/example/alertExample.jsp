<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/inc/head.jsp" %>

	<link rel="stylesheet" href="${css}/bui/bui.css" type="text/css" />
	<script src="${js}/bui/bui.js" type="text/javascript"></script>

	<style type="text/css">

	</style>

	<script type="text/javascript">
		$(function(){
			BUI.use('bui/overlay',function(Overlay){
				var alertSetting = {
					title:'系统提示',
					width:310,
					height:130,
					mask:true,  //设置是否模态
					closeable : false,
					elCls : 'sys-notice-style',
					bodyContent:''
				};

				var formatVal = function(val){
					// val = typeof val=='string' ? val : '{{!}} 注意了  O o 。.';
					val = val.replace('{{v}}','<i class="ico ico-right"></i>');
					val = val.replace('{{!}}','<i class="ico ico-notice"></i>');
					return val;
				}

				$.alert = function(val,call){
					if(typeof val=="string"){
						var alertDialog = new Overlay.Dialog( alertSetting );
						alertDialog.set("bodyContent", formatVal(val) );
						alertDialog.set("buttons", [{
							text:'确定',
							elCls : 'but-blue',
							handler : function(){
								(typeof call=="function") && call();
								this.close();
								$(".bui-ext-mask").css("z-index","1040")
							}
						}]);
						alertDialog.show();
						$(".bui-ext-mask").css("z-index","888")
					}else{

					}
				}

				$.confirm = function(val,call,call2){
					if(typeof val=="string"){
						var alertDialog = new Overlay.Dialog( alertSetting );
						alertDialog.set("bodyContent", formatVal(val) );
						alertDialog.set("buttons", [{
							text:'确定',
							elCls : 'but-blue',
							handler : function(){
								(typeof call=="function") && call();
								this.close();
							}
						},{
							text:'取消',
							elCls : 'but-wire-blue',
							handler : function(){
								(typeof call2=="function") && call2();
								this.close();
							}
						}]);
						alertDialog.show();
					}else{

					}
				}

				$.note = function(val,call){
					if(typeof val=="string"){
						var alertDialog = new Overlay.Dialog( alertSetting );
						alertDialog.set("bodyContent", formatVal(val) );
						alertDialog.set("buttons", []);
						alertDialog.show();
						setTimeout(function(){
							if(typeof call=="function" ){
								alertDialog.close();
								call();
							}else if(typeof call=="string" ){
								window.location.href = call;
							}
						}, 1*1000);
					}else{

					}
				}
			});





		})

		function tryaaa(){
			$.alert("开始编号已被使用请重新输入");
		}

	</script>



</head>

<body class="withvernav">

    <div class="bodywrapper">
		<%@ include file="/inc/top.jsp" %>
		<%@ include file="/inc/left.jsp" %>

        <div class="centercontent">

			<div id="validation" class="subcontent" style="display: block">
				<form id="form-example" class="stdform" method="post" action="#" style="margin-top: 100px; margin-left: 100px">
					<button type="button" class="submit radius2" onclick="tryaaa()">$alert test</button>
					<button type="button" class="submit radius2" onclick="tryaaa()">confirm test</button>
					<button type="button" class="submit radius2" onclick="tryaaa()">$note test</button>
				</form>
			</div>

        </div>


    </div>

</body>
</html>










