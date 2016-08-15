<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../../js/jquery-1.9.1.js"></script>
<title>api测试</title>
</head>
<body>
	<form id="productApi" action="../../openapi.do" method="post">
		<table>
			<tr>
				<td>method:</td>
				<td>
					<input type="text" id="method" name="method" value="">
				</td>
			</tr>
			<tr>
				<td>appid:</td>
				<td>
					<input type="text" id="appid" name="appid" value="">
				</td>
			</tr>
			<tr>
				<td>appSecret:</td>
				<td>
					<input type="text" id="appSecret" name="appSecret" value="">
				</td>
			</tr>
			<tr>
				<td>timestamp:</td>
				<td>
					<input type="text" id="timestamp" name="timestamp" value="">
				</td>
			</tr>
			<tr>
				<td>data:</td>
				<td>
					<input type="text" id="data" name="data" value="">
				</td>
			</tr>
			<tr>
				<td>nonce:</td>
				<td>
					<input type="text" id="nonce" name="nonce" value="">
				</td>
			</tr>
			<tr>
				<td>sign:</td>
				<td>
					<input type="text" id="sign" name="sign" value="">
				</td>
			</tr>
		</table>
		<font>
			<input type="submit" name="submit" value="提交">
		</font>
	</form>
</body>
</html>