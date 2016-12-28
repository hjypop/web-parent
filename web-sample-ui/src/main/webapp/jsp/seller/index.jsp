<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/inc/head.jsp"%>
	<script type="text/javascript" src="${js}/system/ajax-form.js"></script>
	<script type="text/javascript" src="${js}/blockUI/jquery.blockUI.js"></script>
	<script type="text/javascript" src="${js}/plugins/jquery.slimscroll.js"></script>
	<script type="text/javascript">


		$(function(){
			var type_ = 'post';
			var url_ = 'ajaxPageData.do';
			var data_ = null;
			var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
			aForm.launch(url_ , 'table-form' , obj).init().drawForm(loadTable);

//			// 自定义滚动条 | 执行此代码自定义滚动条则生效
//			$('#interface-list').slimscroll({
//				color: '#666',
//				size: '10px',
//				width: 'auto',
//				height: '400px' // '208px'
//			});
		});


		// 回调函数
		function loadTable(url_){
			if(url_ == undefined){ // 首次加载表单
				draw(aForm.jsonObj);
				return;
			}
			// 这种情况是响应上一页或下一页的触发事件
			var type_ = 'post';
			var data_ = $("#search-form").serializeArray();
			var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
			aForm.launch(url_ , 'table-form' , obj).init();
			draw(obj);
		}

		// 画表格
		function draw(obj){
			$('#ajax-tbody-1 tr').remove();
			var html_ = '';
			var list = obj.data.list;
			if(list.length>0){
				for(var i = 0 ; i < list.length ; i ++){
					html_ += '<tr class="gradeX">'
					+'<td align="center"><span class="center"><input type="checkbox" id="' + list[i].zid + '"></span></td>'
					+'<td class="head0" style="text-align: center;">' + list[i].sellerCode + ' </td>'
					+'<td class="head0" style="text-align: center;">' + list[i].uid + ' </td>'
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
					+'<td class="head0" style="text-align: center;">' + list[i].createTime + ' </td>'
					+'<td class="head0" style="text-align:center;">'
					+'<a href="#" >查看</a>|<a href="#" onclick="openDialog(\''+list[i].sellerCode+'\' , true)" >管理</a>'
					+'</td>'
					+'<td class="head0" style="text-align:center;">'
					+'<a href="editindex.do?sellerCode=' + list[i].sellerCode + ' " style="cursor: pointer;color:#FB9337">修改</a> '
					// +'<a class="btn btn3 btn_trash" onclick="deleteOne(\'' + list[i].sellerCode + '\')" title="删除"  style="cursor: pointer;"></a>'
					+'</td>'
					+'</tr>'

				}
			}else{
				html_='<tr><td colspan="11" style="text-align: center;">'+obj.msg+'</td></tr>';
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
			aForm.launch(url_ , 'table-form' , obj).init();
			draw(obj);
		}


		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/**
		 * 接口管理 弹窗层
		 * @param seller_code 商户编号
		 * @param flag true:编辑，false:查看
		 */
		function openDialog(seller_code , flag){
			$("#add-api-btn").remove();
			if(flag){
				$(".dialog-title").children("span").html("管理商户接口权限");
				var html = '<button id="add-api-btn" onclick="submitAccredit(\'' + seller_code + '\');" type="button" style="width: 100px;margin-left: 600px;" class="submit radius2">提交修改</button>';
				$("#add-api").append(html)
			}else{
				$(".dialog-title").children("span").html("查看商户已开通接口");
			}
			drawDialog(seller_code);
			$.blockUI({
				showOverlay:true ,
				css:{
					cursor:'auto',
					left:($(window).width() - $("#api-dialog-div").width())/2 + 'px',
					width:$("#api-dialog-div").width()+'px',
					height:580,
					top:($(window).height()-$("#api-dialog-div").height())/2 + 'px',
					position:'fixed', //居中
					textAlign:'left',
					border: '3px solid #FB9337'   // 边界,
				},
				message: $('#api-dialog-div'),
				fadeIn: 500,//淡入时间
				fadeOut: 1000  //淡出时间
			});
		}

		/**
		 * 初始化弹窗页面
		 * @param seller_code
		 */
		function drawDialog(seller_code){
			var type_ = 'post';
			var url_ = 'soalist.do';
			var data_ = {
				sellerCode : seller_code
			};
			var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
			$("#platform-title").html(obj.platform);
			$("#api-list").remove();
			var html = '<ul id="api-list" class="entrylist">';
			if(obj.status == 'success'){
				var arr = obj.data;
				for(var i = 0 ; i < arr.length ; i ++){
					var html_ = '';
					html += '<li><div class="entry_wrap"><div class=""><h4><span style="color: #F0882C">' + arr[i].method;
					html += '</span></h4><span><span>接口名称：' + arr[i].apiName + '</span> | <a>接口状态：';
					if(arr[i].status == 0){
						html += '<span style="color: red">未开通</span>';
					}else if(arr[i].status == 1){
						html += '<span style="color: green">已开通</span>';
						if(arr[i].uid == 'true'){
							html_ += '<span style="margin-top: 10px"><span style="color:green">已授权使用</span> |';
							html_ += '<input type="checkbox" class="accredit" value="' + arr[i].apiCode + '@delete" style="margin-top: 2px"/>取消授权</span></div></div></li>';
						}else{
							html_ += '<span style="margin-top: 10px">未授权使用 |';
							html_ += '<input type="checkbox" class="accredit" value="' + arr[i].apiCode + '@add" style="margin-top: 2px"/>进行授权</span></div></div></li>';
						}

					}else{
						html += '已禁用';
					}
					html += '</a></span><br><span>接口描述：' + arr[i].description + '</span><br>' + html_;
				}
			}else{
				html += obj.msg;
			}
			html += '</ul>';
			$("#interface-list").append(html);

			// 自定义滚动条 | 执行此代码自定义滚动条则生效
			$('#interface-list').slimscroll({
				color: '#666',
				size: '10px',
				width: 'auto',
				height: '400px' // '208px'
			});
		}

		/**
		 * 提交接口授权
		 * @param seller_code
		 */
		function submitAccredit(sellerCode){
			var val_ = '';
			$(".accredit").each(function() {
				if ($(this)[0].checked == true) {
					val_ += $(this).val() + ',';
				}
			});
			if(val_.length != 0){
				val_ = val_.substring(0 , val_.length -1);
			}

			var data_ = {
				sellerCode : sellerCode,
				apis : val_
			};
			$("#platform-title").html("");
			var obj = JSON.parse(ajaxs.sendAjax('post' , 'accredit.do' , data_));
			if(obj.status == 'success'){
				drawDialog(sellerCode);
				$("#platform-title").html(obj.platform);
			}else{
				$("#platform-title").html(obj.platform);
			}
		}

		function closeDialog(){
			$.unblockUI();
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
								<th class="head1">商户标识码</th>
								<th class="head1">商户名称</th>
								<th class="head1">联系电话</th>
								<th class="head1">合作状态</th>
								<th class="head1">创建时间</th>
								<th class="head1">接口管理</th>
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



<div id="api-dialog-div" class="dialog-page-div" style="display: none;width: 800px;height: 500px">
	<p class="dialog-title">
		<a href="#" onclick="closeDialog()" class="dialog-close"></a>
		<span>
			<%-- 等待填充 弹层标题 --%>
		</span>
	</p>
	<div id="dialog-content-wrapper" class="contentwrapper">
		<div class="last">
			<div class="widgetbox" style="height: inherit">
				<div  class="title">
					<h3 id="platform-title">
						<%-- 等待填充--%>
					</h3>
				</div>
				<div class="widgetcontent">
					<div id="interface-list" class="mousescroll">
						<%--<ul id="api-list" class="entrylist">
							<li>
								<div class="entry_wrap">
									<div class="">
										<h4>
											<span>Product.pushProducts</span>
										</h4>
										<span><span>接口名称：根据日期推送商品到第三方</span> | <a>接口状态：已开通</a></span><br>
										<span>根据日期推送商品到第三方……</span><br>
										<span style="margin-top: 10px">
											&lt;%&ndash;<button class="stdbtn btn_orange">授权使用</button>
											<button class="stdbtn btn_lime">取消使用</button>&ndash;%&gt;
											<input type="radio"  name="" value=""/>授权使用 |
											<input type="radio"  name="" value=""/>取消使用
										</span>
									</div>
								</div>
							</li>

							<li>
								<div class="entry_wrap">
									<div class="">
										<h4>
											<span>Product.Insert</span>
										</h4>
										<span><span>接口名称：添加商品信息</span> | <a>接口状态：已开通</a></span><br>
										<span>添加一条商品信息到数据库中……</span><br>
										<span style="margin-top: 10px">
											<button class="stdbtn btn_lime">授权使用</button>
											<button class="stdbtn ">取消使用</button>
										</span>
									</div>
								</div>
							</li>

						</ul>--%>
					</div>
				</div>
			</div>
		</div>
		<form class="stdform">
			<p>
				<span id="add-api" style="position: relative;">
					<%-- 等待填充 --%>
				</span>
			</p>
		</form>
	</div>
</div>














