<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
	<%@ include file="/inc/head.jsp"%>

	<style type="text/css">
		.table-show,td{
			border-bottom: solid #808080 1px;
			border-spacing: 10px;
			border-collapse: collapse;
			padding-top: 5px;
			padding-bottom: 5px;
		}
		.table-show td{
			width: 150px;
		}
	</style>

	<script type="text/javascript">
		function saveSellerInfo(){
			var url_ = '${basePath}seller/edit.do';
			var data_ = $("#edit-seller").serializeArray();
			var arr = new Array();
			for(var i = 0 ; i < 5 ; i ++){
				var s = '449747810005000' + i ;
				for(var o in data_){
					if(s === data_[o].name){
						var v = new Object();
						v.type= data_[o].name;
						v.commission = data_[o].value;
						arr.push(v);
					}
				}
			}
			var c = new Object();
			c.name = 'commission';
			c.value = JSON.stringify(arr);
			data_.push(c);
			var obj = JSON.parse(ajaxs.sendAjax('post' , url_ , data_));
			if(obj.status === 'success'){
				jConfirm('您下一步是否返回列表页?', '记录修改成功!', function(flag) {
					if(flag){
						window.location = '${basePath}seller/index.do';
					}
				});
			}else{
				jAlert('记录修改失败!', '警告');
			}
		}


		$(function(){
			$("input[name='status'][value='${e.status}']").attr("checked","checked"); // 商户状态 0 未开通 1 已开通 2 已禁用
			$("#type").prop("disabled", true).find("option[value='${e.type}']").attr("selected",true);   // 商家类型
			$("input[name='flag'][value='${e.flag}']").attr("checked","checked"); // 是否需要惠家有为其报关 1是 2否
			$("input[name='sellerCustomLocation'][value='${e.sellerCustomLocation}']").attr("checked","checked");  // 商户报关地点
			$("#priceType").find("option[value='${e.priceType}']").attr("selected",true);  // 价格类型 0 成本价 1 销售价

			var commission = ${c};    // 佣金比例 json存储
			for(var i in commission){
				var o = commission[i];
				$("input[name='" + o.type + "']").val(o.commission);
			}
		});
	</script>
</head>

<body class="withvernav">
<div class="bodywrapper">
	<%@ include file="/inc/top.jsp"%>
	<%@ include file="/inc/left.jsp"%>
	<div class="centercontent">
		<div class="pageheader notab">
			<h1 class="pagetitle">商户管理-修改商户</h1>
		</div>

		<form id="edit-seller" class="stdform" method="post" action="#">
			<input type="hidden" name="zid" value="${e.zid}"/>
			<p>
				<label>商家名称</label>
					<span class="field">
						<input maxlength="20" type="text" name="sellerName" id="sellerName" class="smallinput" value="${e.sellerName}"/>
					</span>
			</p>
			<p>
				<label>商家编号</label>
					<span class="field">
						<input maxlength="20" type="text" name="sellerCode" id="sellerCode" class="smallinput"  value="${e.sellerCode}" readonly="readonly"/>
					</span>
			</p>
			<p>
				<label>商家描述</label>
					<span class="field">
						<textarea cols="80" rows="5" name="sellerDescrption" class="smallinput" id="sellerDescrption">${e.sellerDescrption}</textarea>
					</span>
			</p>
			<p>
				<label>联系电话</label>
					<span class="field">
						<input maxlength="20" type="text" name="sellerTelephone" id="sellerTelephone" class="smallinput" value="${e.sellerTelephone}"/>
					</span>
			</p>
			<p>
				<label>商家信箱</label>
					<span class="field">
						<input type="text" name="sellerEmail" id="sellerEmail" class="smallinput"  value="${e.sellerEmail}"/>
					</span>
			</p>
			<p>
				<label>商户状态</label>
					<span class="field">
						<input type="radio" id="status-y" name="status" value="0"/>未开通 |
						<input type="radio" id="status-n" name="status" value="1"/>已开通|
						<input type="radio" id="status-f" name="status" value="2" />已禁用
					</span>
			</p>
			<p>
				<label>商家类型</label>
					<span class="field">
						<select id="type" name="type">
							<option value="">请选择</option>
							<option value="1">惠家有的商户</option>
							<option value="2">分销平台</option>
						</select>
					</span>
			</p>

			<p>
				<label>跨境商户报关</label>
					<span class="field">
						<input type="radio" id="flag-y" name="flag" value="1"/>需要报关 | <input type="radio" id="flag-n" name="flag" value="2" checked="checked"/>无需报关
					</span>
			</p>
			<p class="seller-custom">
				<label>商户海关备案编号</label>
					<span class="field">
						<input type="text" id="sellerCustomNumber" name="sellerCustomNumber" class="smallinput" value="${e.sellerCustomNumber}"/>
					</span>
			</p>
			<p class="seller-custom">
				<label>商户报关地点</label>
					<span class="field">
						<input type="radio" id="HANGZHOU" name="sellerCustomLocation" value="HANGZHOU"/>杭州海关 |
						<input type="radio" id="ZHENGZHOU" name="sellerCustomLocation" value="ZHENGZHOU"/>郑州海关 |
						<input type="radio" id="GUANGZHOU" name="sellerCustomLocation" value="GUANGZHOU"/>广州海关 |
						<input type="radio" id="CHONGQING" name="sellerCustomLocation" value="CHONGQING"/>重庆海关
						<br />
						<input type="radio" id="NINGBO" name="sellerCustomLocation" value="NINGBO"/>宁波海关 |
						<input type="radio" id="SHENZHEN" name="sellerCustomLocation" value="SHENZHEN"/>深圳海关 |
						<input type="radio" id="HENAN" name="sellerCustomLocation" value="HENAN"/>河南海关 |
						<input type="radio" id="SHANGHAI" name="sellerCustomLocation" value="SHANGHAI"/>上海海关
						<br />
						<input type="radio" id="XIAN" name="sellerCustomLocation" value="XIAN"/>西安海关 |
						<input type="radio" id="SUZHOU" name="sellerCustomLocation" value="SUZHOU"/>苏州海关 |
						<input type="radio" id="TIANJIN" name="sellerCustomLocation" value="TIANJIN"/>天津海关 |
						<input type="radio" id="NANSHAGJ" name="sellerCustomLocation" value="NANSHAGJ"/>南沙国检 |
						<input type="radio" id="ZONGSHU" name="sellerCustomLocation" value="ZONGSHU"/>海关总署
					</span>
			</p>
			<p>
				<label>商户同步价格类型</label>
					<span class="field">
						<select id="priceType" name="priceType">
							<option value="">请选择</option>
							<option value="0">成本价</option>
							<option value="1">销售价</option>
						</select>
					</span>
			</p>

			<p>
				<label>佣金设置</label>
					<span class="field">
						<table  class="table-show ">
							<tbody>
							<c:forEach var="type" items="${ucSellerType}">
								<tr>
									<td>
										${type.defineName}
									</td>
									<td>
										<input id="${type.defineCode }" name="${type.defineCode }" style="" class="define-code smallinput" type="text" value=""/>  %
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</span>
			</p>
			<br />

			<p class="stdformbutton">
					<span class="field">
					<button type="button" style="width: 100px;margin-right: 10px;" class="submit radius2" onclick="saveSellerInfo()">保存</button>
					<button type="button" style="width: 100px;margin-left: 10px;" onclick="" class="stdbtn" >取消</button>
					</span>
			</p>
		</form>
	</div>
</div>
</body>