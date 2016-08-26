<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/inc/head.jsp" %>
	<style type="text/css">

	</style>
	<script type="text/javascript">

		$(function(){
			$('#alertboxbutton').click(function(){
				jAlert('自定义 alert 展示', 'alert-title');
				return false;
			});

			$('#confirmbutton').click(function(){
				// 注意：flag = ture or false - Yangcl
				jConfirm('Can you confirm this?', 'confirm-title', function(flag) {
					jAlert('您选择了: ' + flag, 'alert-title');
				});
				return false;
			});

			$('#promptbutton').click(function(){
				// 注意：content = 你输入的内容 - Yangcl
				jPrompt('请输入：', '', 'prompt-title', function(content) {
					if(content) {
						alert('您输入了：' + content);
					}
				});
				return false;
			});

			$('#alerthtmlbutton').click(function(){
				var html_ = '<a href="${basePath}example/ajaxFormDialogExample.do" target="_blank" class="anchorbutton">Ajax 分页+弹出窗体分页 示例</a>';
				var title = 'alert 支持html标签';
				jAlert(html_ , title);
				return false;
			});

		})


	</script>
</head>

<body class="withvernav">

    <div class="bodywrapper">
		<%@ include file="/inc/top.jsp" %>
		<%@ include file="/inc/left.jsp" %>
		<div class="centercontent tables">
			<!--这个跳转页面的功能及跳转路径等等-->
			<div class="pageheader notab">
				<h1 class="pagetitle">自定义封装 alert confirm prompt 示例</h1>
                    <span class="pagedesc">
                        这个页面描述了系统中最常用的功能：自定义提示框。
                    </span>
				<span style="display:none">jsp/example/pageFormExample</span>
			</div>

			<div id="contentwrapper" class="contentwrapper">

				<div id="dyntable2_wrapper" class="dataTables_wrapper" >
					<div class="contenttitle2">
						<h3>请选择你的用例 本页面包含JavaScript调用方式</h3>
					</div>

					<div class="subcontent" style="display: block; margin-top: 100px; margin-left: 20px">
						<a id="alertboxbutton" href="" class="anchorbutton">基本 Alert</a> &nbsp;
						<a id="confirmbutton" href="" class="anchorbutton">确认对话框 confirm</a> &nbsp;
						<a id="promptbutton" href="" class="anchorbutton">输入对话框 prompt</a> &nbsp;
						<a id="alerthtmlbutton" href="" class="anchorbutton">alert 支持html标签</a>
					</div>

				</div>
			</div>

		</div>



    </div>

</body>
</html>










