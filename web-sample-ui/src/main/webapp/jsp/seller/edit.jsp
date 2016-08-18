<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<%@ include file="/inc/head.jsp"%>
<script type="text/javascript">
function edit(){
	var obj = JSON.parse(ajaxs.sendAjax("post", "edit.do", $("#editSeller").serialize()));
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
                    <form id="editSeller" class="stdform" method="post" action="add.do">
                    	<p>
                        	<label>商户名称</label>
                            <span class="field">
                            	<input type="text" value="${seller.sellerName }" name="sellerName" id="sellerName" class="smallinput" />
                            </span>
                        </p>
                        
                        <p>
                        	<label>商户简称</label>
                            <span class="field">
                            	<input type="text" value="${seller.sellerShortName }" name="sellerShortName" id="sellerShortName" class="smallinput" />
                            </span>
                        </p>
                        <p>
                        	<label>公司名称</label>
                            <span class="field">
                            	<input type="text" value="${seller.sellerCompanyName }" name="sellerCompanyName" id="sellerCompanyName" class="smallinput" />
                            </span>
                        </p>
                        <p>
                        	<label>店铺类型</label>
                            <span class="field">
	                            <select name="sellerType" id="sellerType">
	                            	<option value="">请选择</option>
	                            	<c:forEach var="type" items="${sellerType }">
	                            		<option <c:if test="${seller.sellerType == type.defineCode }">selected="selected"</c:if> value="${type.defineCode }">${type.defineName }</option>	
	                            	</c:forEach>
	                            </select>                            </span>
                        </p>
                        <p>
                        	<label>商户描述</label>
                            <span class="field">
                            	<textarea cols="80" rows="5" value="${seller.sellerDescrption }" name="sellerDescrption" class="smallinput" id="sellerDescrption"></textarea>
                            </span> 
                        </p>
                        <p>
                        	<label>商家状态</label>
                            <span class="field">
	                            <select name="sellerStatus" id="sellerStatus">
	                            	<option value="">请选择</option>
	                            	<c:forEach var="status" items="${sellerStatus }">
	                            		<option <c:if test="${seller.sellerStatus == status.defineCode }">selected="selected"</c:if> value="${status.defineCode }">${status.defineName }</option>	
	                            	</c:forEach>
	                            </select>
                            </span>
                        </p>
                        <p>
                        	<label>所在地</label>
                            <span class="field">
                            	<input type="text" value="${seller.sellerArea }" name="sellerArea" id="sellerArea" class="smallinput" />
                            </span>
                        </p>
                        <p>
                        	<label>联系电话</label>
                            <span class="field">
                            	<input type="text" value="${seller.sellerTelephone }" name="sellerTelephone" id="sellerTelephone" class="smallinput" />
                            </span>
                        </p>
                        <p>
                        	<label>退货地址</label>
                            <span class="field">
                            	<input type="text" value="${seller.sellerReturnAddress }" name="sellerReturnAddress" id="sellerReturnAddress" class="smallinput" />
                            </span>
                        </p>
                        <p>
                        	<label>退货邮箱</label>
                            <span class="field">
                            	<input type="text" value="${seller.sellerReturnPostcode }" name="sellerReturnPostcode" id="sellerReturnPostcode" class="smallinput" />
                            </span>
                        </p>
                        <p>
                        	<label>退货联系人地址</label>
                            <span class="field">
                            	<input type="text" value="${seller.sellerReturnContact }" name="sellerReturnContact" id="sellerReturnContact" class="smallinput" />
                            </span>
                        </p>
                        <p>
                        	<label>退货联系人电话</label>
                            <span class="field">
                            	<input type="text" value="${seller.sellerReturnTelephone }" name="sellerReturnTelephone" id="sellerReturnTelephone" class="smallinput" />
                            </span>
                        </p>
                        <p>
                        	<label>商家信箱</label>
                            <span class="field">
                            	<input type="text" value="${seller.sellerEmail }" name="sellerEmail" id="sellerEmail" class="smallinput" />
                            </span>
                        </p>
                        <p>
                        	<label>合作到期时间</label>
                            <span class="field">
                            	<input type="text" value="${seller.outDate }" name="outDate" id="outDate" class="smallinput" />
                            </span>
                        </p>
                        <br />
                        <p class="stdformbutton">
                        	<span class="field">
		                       	<button type="button" style="width: 100px;margin-right: 10px;" onclick="edit();" class="submit radius2">编辑</button>
		                       	<button type="button" style="width: 100px;margin-left: 10px;" onclick="cancel();" class="stdbtn" >取消</button>
                        	</span>
                        </p>
                    </form>
		</div>
	</div>
</body>