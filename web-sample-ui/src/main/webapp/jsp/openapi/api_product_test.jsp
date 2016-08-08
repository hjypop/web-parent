<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../../js/jquery-1.9.1.js"></script>
<title>api测试</title>
<script type="text/javascript">
function apiClick(action){
	var url = action+".do";
	$("#productApi").attr("action",url);
	$("#productApi").submit();
}
</script>
</head>
<body>
	<form id="productApi" action="" method="post">
		<textarea id="request" name="request" rows="" cols=""
			style="font-size: 11px !important; resize: both; width: 1078px; max-width: 1070px; height: 600px; min-height: 600px;"></textarea>
		<br> <br>
		<div>
			<a href="javascript:void(0)" onclick="apiClick('batchProducts');">批量同步商品</a>
			<a href="javascript:void(0)" onclick="apiClick('addProduct');">添加商品</a>
			<a href="javascript:void(0)" onclick="apiClick('editProduct');">编辑商品</a>
			<a href="javascript:void(0)" onclick="apiClick('batchProductsPrice');">同步商品价格</a>
			<a href="javascript:void(0)" onclick="apiClick('batchProductsSkuStore');">同步商品库存</a>
		</div>
	</form>
</body>
</html>