<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/inc/head.jsp"%>
	<script type="text/javascript" src="${js}/system/ajax-form.js"></script>
	<script type="text/javascript">


		$(function(){
			var type_ = 'post';
			var url_ = '${basePath}seller/ajaxPageData.do';
			var data_ = null;
			var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
			if(obj.status == 'success'){
				aForm.launch(url_ , 'table-form' , obj).init().drawForm(loadTable);
			}
		});

		// 回调函数
		function loadTable(url_){
			if(url_ == undefined){ // 首次加载表单
				draw(aForm.jsonObj);
				console.log("A1")
				return;
			}
			// 这种情况是响应上一页或下一页的触发事件
			var type_ = 'post';
			var data_ = $("#search-form").serializeArray();
			var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
			if(obj.status == 'success'){
				aForm.launch(url_ , 'table-form' , obj).init();
				console.log("B" + obj.data.pageNum);
				draw(obj);
			}
		}

		// 画表格
		function draw(obj){
			$('#ajax-tbody-1 tr').remove();
			var html_ = '';
			var list = obj.data.list;
			for(var i = 0 ; i < list.length ; i ++){
				html_ += '<tr class="gradeX">'
				+'<td align="center"><span class="center"><input type="checkbox" id="' + list[i].zid + '"></span></td>'
				+'<td class="head0" style="text-align: center;">' + list[i].sellerCode + ' </td>'
				+'<td class="head0">' + list[i].sellerName + ' </td>'
				+'<td class="head0" style="text-align: center;">' + list[i].sellerTelephone + ' </td>'
				+'<td class="head0" style="text-align: center;">'
				if(list[i].status == 1){
					html_ += '已开通';
				}else if(list[i].status == 2){
					html_ += '已禁用';
				}else{
					html_ += '未开通';
				}
				html_ += '</td>'
				+'<td class="head0" style="text-align: center;">' + list[i].creator + ' </td>'
				+'<td class="head0" style="text-align: center;">' + list[i].createTime + ' </td>'
				+'<td class="head0" style="text-align: center;">' + list[i].updator + ' </td>'
				+'<td class="head0" style="text-align: center;">' + list[i].updateTime + ' </td>'
				+'<td class="head0" style="text-align:center;">'
				+'<a class="btn btn3 btn_book" href="editindex.do?sellerCode=' + list[i].sellerCode + ' " title="修改"  style="cursor: pointer;"></a> '
				+'<a class="btn btn3 btn_trash" onclick="deleteOne(\'' + list[i].sellerCode + '\')" title="删除"  style="cursor: pointer;"></a>'
				+'</td>'
				+'</tr>'

			}
			$('#ajax-tbody-1').append(html_);
		}





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
			var type_ = 'post';
			var url_ = '${basePath}seller/ajaxPageData.do?pageSize=' + parseInt($("#select-page-size").val());
			var data_ = $("#search-form").serializeArray();
			var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
			if(obj.status == 'success'){
				aForm.launch(url_ , 'table-form' , obj).init();
				console.log("B" + obj.data.pageNum);
				draw(obj);
			}
		}



	</script>
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
				<div id="table-form" class="dataTables_wrapper">

					<div id="search" class="subcontent">
						<form id="search-form" class="stdform" method="post" action="">
							<div class="formwiz">
								<p>
									<label>商户编号</label> <span class="field">
									<input type="text" name="sellerCode" id="sellerCode" value="" class="smallinput" /></span>
								</p>
								<p>
									<label>商户名称</label> 
									<span class="field">
										<input type="text" name="sellerName" id="sellerName" value="" class="smallinput" />
									</span>
								</p>
								<p>
									<span class="field">
										<a style="text-align: right;" href="javascript:void(0)" onclick="searchSeller();" class="btn btn2 btn_blue btn_search radius50"><span>搜索</span></a>
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
						<label>
							当前显示
							<select id="select-page-size" size="1" name="dyntable2_length" onchange="aForm.formPaging('1')">
								<option value="10">10</option>
								<option value="25" >25</option>
								<option value="50">50</option>
								<option value="100">100</option>
							</select>
							条记录
						</label>
					</div>
					<table id="dyntable2" cellpadding="0" cellspacing="0" border="0" class="stdtable">
						<thead>
							<tr>
								<th class="head1">
									<input type="checkbox" name="checkedAll" id="checkedAll">
								</th>
								<th class="head1">商户编号</th>
								<th class="head1">商户名称</th>
								<th class="head1">联系电话</th>
								<th class="head1">合作状态</th>
								<th class="head1">创建人</th>
								<th class="head1">创建时间</th>
								<th class="head1">修改人</th>
								<th class="head1">修改时间</th>
								<th class="head1">操作</th>
							</tr>
						</thead>
						<tbody id="ajax-tbody-1">
							<%-- 等待填充--%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>

</html>


















