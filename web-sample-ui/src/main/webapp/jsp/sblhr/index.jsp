<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String js = path + "/js";
	String css = path + "/css";
	String img = path + "/images";
	String resources = path + "/resources";
	
	pageContext.setAttribute("js", js);
	pageContext.setAttribute("css", css);
	pageContext.setAttribute("img", img);
	pageContext.setAttribute("basePath", basePath);
	pageContext.setAttribute("resources", resources);
	
	if(session.getAttribute("seller-operation-key") == null){
		String url = basePath + "jsp/sblhr/validate.jsp";
		response.sendRedirect(url);
		return;
	}
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/inc/head.jsp" %>
    <script type="text/javascript">

        function funcOne(){
            var type_ = 'post';
            var url_ = '${basePath}adapter/funcOne.do';
            var data_ = {json:$("#json-str").val()};
            var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
            if(obj.status == 'success'){
                jAlert(obj.desc, '网页提示');
            }
        }

        function funcTwo(){
            var type_ = 'post';
            var url_ = '${basePath}adapter/funcTwo.do';
            var data_ = {json:$("#fuck-two-str").val()};
            var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
            if(obj.status == 'success'){
                jAlert(obj.desc, '网页提示');
            }
        }

        function funcTwossssssssssss(){
            var s = $("#s-time").val();
            var e = $("#e-time").val();

            var type_ = 'post';
            var url_ = '${basePath}adapter/funcTwo.do';
            var data_ = {
                s:s,
                e:e
            };
            var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
            if(obj.status == 'success'){
                jAlert(obj.desc, '网页提示');
            }
        }

        function trim(str) {
            return str.replace(/(^\s+$)/g, "");
        }

    </script>
</head>

<body class="withvernav">

    <div class="bodywrapper">
        <%@ include file="/inc/top.jsp" %>
        <%@ include file="/inc/left.jsp" %>

        <div class="centercontent tables">
            <div class="pageheader notab">
                <h1 class="pagetitle">商户、财务和马丽老师的线上临时问题解决页面</h1>
                    <span class="pagedesc">
                        这个页面 执行她们的线上临时需求，如：一批商品下架、一批商品上架等等。 | <a href="${basePath}adapter/leave.do" style="color: #ff0000">【离开此页面】</a>
                    </span>
                <span style="display:none">jsp/sblhr/index.jsp</span>
            </div>

            <div id="contentwrapper" class="contentwrapper">

                <div id="fuck-one">
                    <div class="contenttitle2">
                        <h3>A 批量修改商户商品的税率</h3>
                    </div>
                    <div class="statusbox" style="width: 800px">
                        <form id="json-post" action="#">
                            <div class="status_thumb">标准数据结构Json串：</div>
                            <div style="padding-right:20px;">
                                <textarea id="json-str" name="" cols="" rows="" style="height: 200px;width: 790px" placeholder='{"SF03100804":["8016474370@0.17","8016474378@0.17"],"SF03100803":["8016475996@0.17","8016475998@0.17"]}'></textarea>
                            </div>
                            <div class="submit">
                                <button class="stdbtn btn_orange" onclick="funcOne()">执 行 任 务</button>
                            </div>
                        </form>
                    </div>
                </div>

                <div id="fuck-two">
                    <div class="contenttitle2">
                        <h3>B 批量修改商户商品的权威标识 </h3>
                    </div>
                    <div class="statusbox" style="width: 800px">
                        <form id="fuck-two-post" action="#">
                            <div class="status_thumb">标准数据结构Json串：</div>
                            <div style="padding-right:20px;">
                                <textarea id="fuck-two-str" name="" cols="" rows="" style="height: 200px;width: 790px" placeholder='["8016474370","8016474378"]'></textarea>
                            </div>
                            <div class="submit">
                                <button class="stdbtn btn_orange" onclick="funcTwo()">执 行 任 务</button>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>

</body>
</html>




























