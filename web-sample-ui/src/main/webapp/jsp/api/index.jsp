<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/inc/head.jsp"%>
<script type="text/javascript" src="${js}/system/ajax-form.js"></script>
<script type="text/javascript" src="${js}/blockUI/jquery.blockUI.js"></script>
<title>开放接口管理</title>
<script type="text/javascript">
	//------ 分页 start ---------
	$(function() {
		var type_ = 'post';
		var url_ = 'ajaxPageData.do';
		var data_ = null;
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		aForm.launch(url_, 'table-form', obj).init().drawForm(loadTable);
	});
	// 回调函数
	function loadTable(url_) {
		if (url_ == undefined) { // 首次加载表单
			draw(aForm.jsonObj);
			return;
		}
		// 这种情况是响应上一页或下一页的触发事件
		var type_ = 'post';
		var data_ = $("#search-form").serializeArray();
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		if (obj.status == 'success') {
			aForm.launch(url_, 'table-form', obj).init();
			draw(obj);
		}
	}
	// 画表格
	function draw(obj) {
		$('#ajax-tbody-1 tr').remove();
		var html_ = '';
		var list = obj.data.list;
		if(list.length>0){
			for (var i = 0; i < list.length; i++) {
				var obj = list[i];
				html_ += '<tr id="tr_'+obj.apiCode+'" class="gradeX" code=\''+JSON.stringify(obj)+'\'>'
						+ '<td align="center"><span class="center"><input type="checkbox" id="' + obj.zid + '"></span></td>'
						+ '<td class="head0" style="text-align: center;">'
						+ obj.apiCode + ' </td>' + '<td class="head0">'
						+ obj.apiName + ' </td>'
						+ '<td class="head0" style="text-align: center;">'
						+ obj.method + ' </td>'
						+ '<td class="head0" style="text-align: center;">'
				if (obj.status == 1) {
					html_ += '已开通';
				} else if (obj.status == 2) {
					html_ += '已禁用';
				} else {
					html_ += '未开通';
				}
				html_ += '</td>'
						+ '<td class="head0" style="text-align: center;">'
						+ obj.creator
						+ ' </td>'
						+ '<td class="head0" style="text-align: center;">'
						+ obj.createTime
						+ ' </td>'
						+ '<td class="head0" style="text-align: center;">'
						+ obj.updator
						+ ' </td>'
						+ '<td class="head0" style="text-align: center;">'
						+ obj.updateTime
						+ ' </td>'
						+ '<td class="head0" style="text-align:center;">'
						+ '<a class="btn btn3 btn_book" onclick="openDialog(\''+obj.apiCode+'\')" title="修改"  style="cursor: pointer;"></a> '
						+ '<a class="btn btn3 btn_trash" onclick="deleteOne(\''
						+ obj.apiCode
						+ '\')" title="删除"  style="cursor: pointer;"></a>'
						+ '</td>' + '</tr>'

			}			
		}else{
			html_='<tr><td colspan="10" style="text-align: center;">'+obj.msg+'</td></tr>';
		}
		$('#ajax-tbody-1').append(html_);
	}
	//------ 分页 end ---------
	//删除api
	function deleteOne(code) {
		if (confirm('您确定要删除这条记录吗？')) {
			var type_ = 'post';
			var url_ = 'del.do';
			var data_ = {
				apiCode : code
			};
			var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			if (obj.status == 'success') {
				alert(obj.msg);
				//刷新列表
				var type_ = 'post';
				var url_ = 'ajaxPageData.do?pageSize='
						+ parseInt($("#select-page-size").val());
				var data_ = $("#search-form").serializeArray();
				var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
				if (obj.status == 'success') {
					aForm.launch(url_, 'table-form', obj).init().drawForm(loadTable);
				}
			} else {
				alert(obj.msg);
			}
		}

	}
	//查询api
	function searchApi() {
		var type_ = 'post';
		var url_ = 'ajaxPageData.do?pageSize='
				+ parseInt($("#select-page-size").val());
		var data_ = $("#search-form").serialize();
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		aForm.launch(url_, 'table-form', obj).init();
		draw(obj);
	}
	//弹窗层
	function openDialog(apiCode){
		if(apiCode){
			$(".dialog-title").children("span").html("编辑api接口");
			//填充弹出层文本框内容 start
			var obj = JSON.parse($("#tr_"+apiCode).attr("code"));
			$("#dialog_apiCode").val(obj.apiCode);
			$("#dialog_apiName").val(obj.apiName);
			$("#dialog_method").val(obj.method);
			$("#dialog_description").val(obj.description);
			$("#dialog_status").val(obj.status);
			// end
		}else{
			$(".dialog-title").children("span").html("添加api接口");
		}
		$.blockUI({
			showOverlay:true ,
			css:{
				cursor:'auto',
				left:($(window).width() - $("#api-dialog-div").width())/2 + 'px',
				width:$("#api-dialog-div").width()+'px',
				top:($(window).height()-$("#api-dialog-div").height())/2 + 'px',
				position:'fixed', //居中
				border: '3px solid #FB9337'  // 边界
			},
			message: $('#api-dialog-div'),
			fadeIn: 500,//淡入时间
			fadeOut: 1000  //淡出时间
       });
	}
    /**
     * @描述: 关闭BlockUI弹框
     * @作者: Yangcl
     * @时间: 2016-08-19 : 15-20-56
     */
    function closeDialog(){
        $.unblockUI();
    }
    
    //添加编辑api接口
    function apiSubmit(){
    	var apiCodeVal = $("#dialog_apiCode").val();
		var apiNameVal = $("#dialog_apiName").val();
		var methodVal = $("#dialog_method").val();
		var descriptionVal = $("#dialog_description").val();
		var statusVal = $("#dialog_status").val();
		var action = "";
		if(apiCodeVal){
			action = "edit.do";
		}else{
			action = "add.do";
		}
		var data = {
				apiCode : apiCodeVal,
				apiName : apiNameVal,
				method : methodVal,
				description : descriptionVal,
				status : statusVal
		}
		var obj = JSON.parse(ajaxs.sendAjax("post", action, data));
		if (obj.status == 'success') {
			alert(obj.msg);
		} else {
			alert(obj.msg);
		}
		closeDialog();
		//刷新列表
		var type_ = 'post';
		var url_ = 'ajaxPageData.do?pageSize='
				+ parseInt($("#select-page-size").val());
		var data_ = $("#search-form").serializeArray();
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		if (obj.status == 'success') {
			aForm.launch(url_, 'table-form', obj).init().drawForm(loadTable);
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
				<h1 class="pagetitle">接口管理-接口列表</h1>
			</div>
			<div id="contentwrapper" class="contentwrapper">
				<div id="table-form" class="dataTables_wrapper">
					<!-- 查询条件 -->
					<div id="search" class="subcontent">
						<form id="search-form" class="stdform" method="post" action="">
							<div class="formwiz">
								<p>
									<label>接口名称</label> 
									<span class="field">
										<input type="text" name="apiName" id="apiName" value="" class="smallinput" />
									</span>
								</p>
								<p>
									<label>接口方法</label> <span class="field"> <input
										type="text" name="method" id="method" value=""
										class="smallinput" />
									</span>
								</p>
								<p>
									<span class="field"> <a style="text-align: right;"
										href="javascript:void(0)" onclick="searchApi();"
										class="btn btn2 btn_blue btn_search radius50"><span>搜索</span></a>
									</span>
								</p>
							</div>
						</form>
					</div>
					<div class="btn_div" style="margin-bottom: 5px;">
						<a href="javascript:void(0)" onclick="openDialog();" class="btn btn2 btn_blue btn_link"> <span>添加</span>
						</a>
					</div>
					<div id="dyntable2_length" class="dataTables_length">
						<label> 当前显示 <select id="select-page-size" size="1"
							name="dyntable2_length" onchange="aForm.formPaging('1')">
								<option value="10">10</option>
								<option value="25">25</option>
								<option value="50">50</option>
								<option value="100">100</option>
						</select> 条记录
						</label>
					</div>
					<table id="dyntable2" cellpadding="0" cellspacing="0" border="0"
						class="stdtable">
						<thead>
							<tr>
								<th class="head1"><input type="checkbox" name="checkedAll"
									id="checkedAll"></th>
								<th class="head1">接口编号</th>
								<th class="head1">接口名称</th>
								<th class="head1">接口方法名称</th>
								<th class="head1">接口状态</th>
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
	<!-- 添加api接口弹出层 -->
	<div id="api-dialog-div" class="dialog-page-div" style="display: none;width: 800px;height: 500px">
	    <p class="dialog-title">
	        <a href="#" onclick="closeDialog()" class="dialog-close"></a>
	        <span>添加api接口</span>
	    </p>
	    <div id="dialog-content-wrapper" class="contentwrapper">
	    	<form id="apiForm" class="stdform" method="post" action="">
	              	<input type="hidden"  id="dialog_apiCode" name="_dialogapiCode" value=""></input>
	              	<p>
	                  	<label>接口名称</label>
						<span style="position: relative;">
							<input maxlength="20" type="text" name="dialog_apiName" id="dialog_apiName" class="smallinput" />
						</span>
					</p>
	              	<p>
	                  	<label>接口方法</label>
						<span style="position: relative;">
							<input maxlength="20" type="text" name="dialog_method" id="dialog_method" class="smallinput" />
						</span>
					</p>
	              	<p>
	                  	<label>接口描述</label>
						<span style="position: relative;">
							<textarea name="dialog_description" id="dialog_description" cols="80" rows="5" class="smallinput"></textarea>
						</span>
					</p>
                    <p>
                    	<label>接口状态</label>
                        <span style="position: relative;">
                        	<select id="dialog_status" name="dialog_status">
                        		<option value="">请选择</option>
                        		<option value="0">未开通</option>
                        		<option value="1">已开通</option>
                        		<option value="2">已禁用</option>
                        	</select>
                        </span>
                    </p>
                    <p>
                    	<span style="position: relative;">
                     		<button onclick="apiSubmit();" type="button" style="width: 100px;margin-right: 10px;" class="submit radius2">保存</button>
                     		<button type="button" style="width: 100px;margin-left: 10px;" onclick="closeDialog();" class="stdbtn" >取消</button>
                    	</span>
                    </p>
	    	</form>
	    </div>
   	</div>
</html>