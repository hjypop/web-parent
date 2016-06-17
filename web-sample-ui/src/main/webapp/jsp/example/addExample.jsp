<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/inc/head.jsp" %>
</head>

<body class="withvernav">

    <div class="bodywrapper">
		<%@ include file="/inc/top.jsp" %>
		<%@ include file="/inc/left.jsp" %>

        <div class="centercontent">

			<div id="validation" class="subcontent" style="display: block">
				<form id="form-example" class="stdform" method="post" action="${basePath}example/addInfo.do">
					<p>
						<label>商户名称</label>
						<span class="field">
							<input type="text" name="companyName" id="companyName" class="longinput" />
						</span>
					</p>
					<p>
						<label>备注</label>
						<span class="field">
							<input type="text" name="remark" id="remark" class="longinput" />
						</span>
					</p>
					<br />
					<p class="stdformbutton">
						<button type="submit" class="submit radius2">添加</button>
					</p>
				</form>
			</div>

			<c:if test="${status == true}">
				<h1 style="color:#ff0000">${msg}</h1>
			</c:if>
			<c:if test="${status == false}">
				<h1 style="color:#ff0000">${msg}</h1>
			</c:if>
        </div>


    </div>

</body>
</html>










