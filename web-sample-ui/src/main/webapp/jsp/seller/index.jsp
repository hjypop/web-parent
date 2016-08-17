<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/inc/head.jsp"%>
</head>

<body class="withvernav">
	<div class="bodywrapper">
		<%@ include file="/inc/top.jsp"%>
		<%@ include file="/inc/left.jsp"%>
		<div class="centercontent tables">
			<div class="pageheader notab">
				<h1 class="pagetitle">商户管理-商户列表</h1>
			</div>
			<div id="contentwrapper" class="contentwrapper">
				<div id="dyntable2_wrapper" class="dataTables_wrapper">
					<div id="search" class="subcontent">
						<form id="search-form" class="stdform" method="post" action="">
							<div class="formwiz">
								<p>
									<label>商户编号</label> <span class="field"><input
										type="text" name="sellerCode" id="sellerCode" value="${seller.sellerCode}"
										class="smallinput" /></span>
								</p>
								<p>
									<label>商户名称</label> 
									<span class="field">
										<input type="text" name="sellerName" id="sellerName" value="${seller.sellerName }" class="smallinput" />
									</span>
								</p>
								<p>
									<span class="field">
									<a style="text-align: right;" href="javascript:void(0)" onclick="searchSeller();"
										class="btn btn2 btn_blue btn_search radius50"><span>Search</span></a>
									</span>
								</p>
							</div>
						</form>
					</div>
					<div class="btn_div" style="margin-bottom: 5px;">
						<a href="addindex.do" class="btn btn2 btn_blue btn_link">
							<span>添加</span>
						</a>
					</div>
					<div id="dyntable2_length" class="dataTables_length">
						<label> 当前显示 <select id="select-page-size" size="1"
							name="dyntable2_length" onchange="formPaging('1')">
								<option value="10"
									<c:if test="${pageList.pageSize == 10}">selected="selected"</c:if>>10</option>
								<option value="25"
									<c:if test="${pageList.pageSize == 25}">selected="selected"</c:if>>25</option>
								<option value="50"
									<c:if test="${pageList.pageSize == 50}">selected="selected"</c:if>>50</option>
								<option value="100"
									<c:if test="${pageList.pageSize == 100}">selected="selected"</c:if>>100</option>
						</select> 条记录
						</label>
					</div>
					<table id="dyntable2" cellpadding="0" cellspacing="0" border="0"
						class="stdtable">
						<thead>
							<tr>
								<th class="head1"><input type="checkbox" name="checkedAll"
									id="checkedAll"></input></th>
								<th class="head1">商户编号</th>
								<th class="head1">商户名称</th>
								<th class="head1">商家简称</th>
								<th class="head1">商家状态</th>
								<th class="head1">公司名称</th>
								<th class="head1">联系电话</th>
								<th class="head1">创建人</th>
								<th class="head1">创建时间</th>
								<th class="head1">修改人</th>
								<th class="head1">修改时间</th>
								<th class="head1">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="seller" items="${pageList.list}">
								<tr>
									<td class="head0"><input type="checkbox" name="" id=""></input>
									</td>
									<td class="head0">${seller.sellerCode}</td>
									<td class="head0">${seller.sellerName}</td>
									<td class="head0">${seller.sellerShortName}</td>
									<td class="head0">${seller.sellerStatus}</td>
									<td class="head0">${seller.sellerCompanyName}</td>
									<td class="head0">${seller.sellerTelephone}</td>
									<td class="head0">${seller.creator}</td>
									<td class="head0">${seller.createTime}</td>
									<td class="head0">${seller.updator}</td>
									<td class="head0">${seller.updateTime}</td>
									<td class="head0">
									<a onclick="deleteOne('${seller.sellerCode}')" title="删除" class="btn btn3 btn_trash" style="cursor: pointer;"></a>
									<a href="editindex.do?sellerCode=${seller.sellerCode }" title="编辑" class="btn btn3 btn_book" style="cursor: pointer;"></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<%@ include file="/inc/mysql-page.jsp"%>
				</div>
				<%@ include file="/inc/page-form.jsp"%>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function deleteOne(code) {
		if (confirm('您确定要删除这条记录吗？')) {
			var type_ = 'post';
			var url_ = 'del.do';
			var data_ = {
				sellerCode : code
			};
			var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			if (obj.status == 'success') {
				alert(obj.msg);
				var ps = $("#select-page-size").val();
				var actions = "index.do";
				$('#page-form').attr("action", actions);
				$("#page-form").submit();
			} else {
				alert(obj.msg);
			}
		}

	}
	function searchSeller() {
		var ps = $("#select-page-size").val();
		var actions = "index.do?"+$("#search-form").serialize();
		$('#page-form').attr("action", actions);
		$("#page-form").submit();
	}
</script>
</html>