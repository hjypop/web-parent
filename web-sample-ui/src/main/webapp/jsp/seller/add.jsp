<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<%@ include file="/inc/head.jsp"%>
<script type="text/javascript" src="${js}/plugins/jquery.validate.min.js"></script>
<script type="text/javascript" src="${js}/plugins/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript">
function add(){
	
	var obj = JSON.parse(ajaxs.sendAjax("post", "add.do", $("#addSeller").serialize()));
	if (obj.status == 'success') {
		alert(obj.msg);
	} else {
		alert(obj.msg);
	}
	window.open("index.do","_self");
}
function cancel(){
	window.open("index.do","_self");
}
$(document).ready(function(){
	//设置验证信息
	jQuery("#addSeller").validate({
		rules: {
			sellerName: "required",
			sellerCompanyName: "required",
			sellerType: "required",
			sellerArea: "required",
			sellerTelephone: "required",
			sellerReturnAddress: "required",
			sellerReturnPostcode: "required",
			sellerReturnContact: "required",
			sellerEmail:{
				required: true,
				email: true,
			},
			outDate: "required"
		},
		messages: {
			sellerName: "请填写商户名称",
			sellerCompanyName: "请填写商户公司名称",
			sellerType:"请选择商户类型",
			sellerArea:"请填写商户所在地",
			sellerTelephone:"请填写商户联系电话",
			sellerReturnAddress: "请填写退货地址",
			sellerReturnPostcode: "请填写退货邮编",
			sellerReturnContact: "请填写退货联系人地址",
			sellerEmail: "请填写商家信箱",
			outDate: "请填写合作到期时间"
		},
		debug:true,
		submitHandler:function(){
			add();
		}
	});
});
</script>
</head>

<body class="withvernav">
	<div class="bodywrapper">
		<%@ include file="/inc/top.jsp"%>
		<%@ include file="/inc/left.jsp"%>
		<div class="centercontent">
			<div class="pageheader notab">
				<h1 class="pagetitle">商户管理-添加商户</h1>
			</div>
                <form id="addSeller" class="stdform" method="post" action="add.do">
                	<p>
                    	<label>商户名称</label>
                        <span class="field">
                        	<input maxlength="20" type="text" name="sellerName" id="sellerName" class="smallinput" />
                        </span>
                    </p>
                    
                    <p>
                    	<label>商户简称</label>
                        <span class="field">
                        	<input maxlength="20" type="text" name="sellerShortName" id="sellerShortName" class="smallinput" />
                        </span>
                    </p>
                    <p>
                    	<label>公司名称</label>
                        <span class="field">
                        	<input maxlength="50" type="text" name="sellerCompanyName" id="sellerCompanyName" class="smallinput" />
                        </span>
                    </p>
                    <p>
                    	<label>店铺类型</label>
                        <span class="field">
                         <select name="sellerType" id="sellerType">
                         	<option value="">请选择</option>
                         	<c:forEach var="type" items="${sellerType }">
                         		<option value="${type.defineCode }">${type.defineName }</option>	
                         	</c:forEach>
                         </select>
                        </span>
                    </p>
                    <p>
                    	<label>商户描述</label>
                        <span class="field">
                        	<textarea cols="80" rows="5" name="sellerDescrption" class="smallinput" id="sellerDescrption"></textarea>
                        </span> 
                    </p>
                    <p>
                    	<label>所在地</label>
                        <span class="field">
                        	<input maxlength="100" type="text" name="sellerArea" id="sellerArea" class="smallinput" />
                        </span>
                    </p>
                    <p>
                    	<label>联系电话</label>
                        <span class="field">
                        	<input maxlength="20" type="text" name="sellerTelephone" id="sellerTelephone" class="smallinput" />
                        </span>
                    </p>
                    <p>
                    	<label>退货地址</label>
                        <span class="field">
                        	<input maxlength="100" type="text" name="sellerReturnAddress" id="sellerReturnAddress" class="smallinput" />
                        </span>
                    </p>
                    <p>
                    	<label>退货邮编</label>
                        <span class="field">
                        	<input maxlength="6" type="text" name="sellerReturnPostcode" id="sellerReturnPostcode" class="smallinput" />
                        </span>
                    </p>
                    <p>
                    	<label>退货联系人地址</label>
                        <span class="field">
                        	<input maxlength="100" type="text" name="sellerReturnContact" id="sellerReturnContact" class="smallinput" />
                        </span>
                    </p>
                    <p>
                    	<label>退货联系人电话</label>
                        <span class="field">
                        	<input maxlength="20" type="text" name="sellerReturnTelephone" id="sellerReturnTelephone" class="smallinput" />
                        </span>
                    </p>
                    <p>
                    	<label>商家信箱</label>
                        <span class="field">
                        	<input type="text" name="sellerEmail" id="sellerEmail" class="smallinput" />
                        </span>
                    </p>
                    <br />
                    <p class="stdformbutton">
                    	<span class="field">
                     	<button type="submit" style="width: 100px;margin-right: 10px;" class="submit radius2">添加</button>
                     	<button type="button" style="width: 100px;margin-left: 10px;" onclick="cancel();" class="stdbtn" >取消</button>
                    	</span>
                    </p>
                </form>
		</div>
	</div>
</body>