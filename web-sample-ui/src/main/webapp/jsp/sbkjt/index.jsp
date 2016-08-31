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
	
	if(session.getAttribute("kjt-key") == null){
		String url = basePath + "jsp/sbkjt/validate.jsp";
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
            var url_ = '${basePath}kjt/funcOne.do';
            var data_ = {json:$("#json-str").val()};
            var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
            if(obj.status == 'success'){
                alert(obj.desc);
            }
        }

        function funcTwo(){
            var s = $("#s-time").val();
            var e = $("#e-time").val();

            var type_ = 'post';
            var url_ = '${basePath}kjt/funcTwo.do';
            var data_ = {
                s:s,
                e:e
            };
            var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
            if(obj.status == 'success'){
                alert(obj.desc);
            }
        }

        function funcThree(){
            var remark_ = $("#remark").val();
            var execTime_ = $("#exec-time").val();
            var type_ = 'post';
            var url_ = '${basePath}kjt/funcThree.do';
            var data_ = {
                remark:remark_,
                execTime:execTime_
            };
            var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
            if(obj.status == 'success'){
                alert(obj.desc);
            }
        }

        function funcFour(){
            var type_ = 'post';
            var url_ = '${basePath}kjt/funcFour.do';
            var data_ = {json:$("#json-str-p").val()};
            var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
            if(obj.status == 'success'){
                alert(obj.desc);
            }
        }

        function funcFive(){
            var type_ = 'post';
            var url_ = '${basePath}kjt/funcFive.do';
            var data_ = {json:$("#json-str-e").val()};
            var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
            if(obj.status == 'success'){
                alert(obj.desc);
            }
        }

    </script>
</head>

<body class="withvernav">

    <div class="bodywrapper">
        <%@ include file="/inc/top.jsp" %>
        <%@ include file="/inc/left.jsp" %>

        <div class="centercontent tables">
            <!--这个跳转页面的功能及跳转路径等等-->
            <div class="pageheader notab">
                <h1 class="pagetitle">跨境通线上临时问题解决页面</h1>
                    <span class="pagedesc">
                        这个页面 执行运营人员的线上临时需求，如：一批商品下架、一批商品上架等等。 | <a href="${basePath}kjt/leave.do" style="color: #ff0000">【离开此页面】</a>
                    </span>
                <span style="display:none">jsp/sbkjt/index.jsp</span>
            </div>

            <div id="contentwrapper" class="contentwrapper">
                <div id="fuck-one">
                    <div class="contenttitle2">
                        <h3>A  批量拉取商品价格变动信息|接口标识：Product.ProudctInfoBatchGet</h3>
                    </div>

                    <div class="statusbox" style="width: 800px">
                        <form id="json-post" action="#">
                            <div class="status_thumb">商品编号Json串：</div>
                            <div style="padding-right:20px;">
                                <textarea id="json-str" name="" cols="" rows="" style="height: 200px;width: 790px"></textarea>
                            </div>
                            <div class="submit">
                                <button class="stdbtn btn_orange" onclick="funcOne()">执 行 任 务</button>
                            </div>
                        </form>
                    </div>

                </div>

                <div id="fuck-two">
                    <div class="contenttitle2">
                        <h3>B  拉取指定时间段内商品价格变动信息|接口标识：Product.ProudctInfoBatchGet</h3>
                    </div>

                    <div class="statusbox" style="width: 800px">
                        <form id="date-post" action="#">
                            <div style="padding-right:20px;">
                                <div class="comment_authorimg"  >开始时间</div>
                                <div class="commentcontent" style="padding-right: 12px;margin-left:65px; margin-bottom: 10px">
                                    <input  id="s-time" type="text" value="这是一个示例：2016-08-08 00:00:00">
                                </div>

                                <div class="comment_authorimg">结束时间</div>
                                <div class="commentcontent" style="padding-right: 12px;margin-left:65px;">
                                    <input  id="e-time" type="text" placeholder="2016-08-08 00:00:00">
                                </div>
                            </div>
                            <div class="submit">
                                <button class="stdbtn btn_orange" onclick="funcTwo()">执 行 任 务</button>
                            </div>
                        </form>
                    </div>


                </div>

                <div id="fuck-three">
                    <div class="contenttitle2">
                        <h3>C  重新同步跨境通问题订单|接口标识：Order.SOCreate</h3>
                    </div>

                    <div class="statusbox" style="width: 800px">
                        <form id="poststatus" action="#">
                            <div class="status_thumb">备注信息：</div>
                            <div style="padding-right:20px;margin-bottom: 20px">
                                <textarea id="remark" name="" cols="" rows="" style="height: 100px;width: 790px"></textarea>
                            </div>

                            <div class="comment_authorimg"  >执行时间</div>
                            <div class="commentcontent" style="padding-right: 12px;margin-left:65px; margin-bottom: 10px">
                                <input  id="exec-time" type="text" placeholder="这是一个示例：2016-08-08 00:00:00  默认则为当前时间">
                            </div>

                            <div class="submit">
                                <button class="stdbtn btn_orange" onclick="funcThree()">执 行 任 务</button>
                            </div>
                        </form>
                    </div>


                </div>

                <div id="fuck-four">
                    <div class="contenttitle2">
                        <h3>D  根据产品编号Json批量置空跨境通商品编号(pc_productinfo 表 product_code_old 字段将被置空)</h3>
                    </div>
                    <div class="statusbox" style="width: 800px">
                        <form action="#">
                            <div class="status_thumb">产品编号Json串：</div>
                            <div style="padding-right:20px;">
                                <textarea id="json-str-p" name="" cols="" rows="" style="height: 200px;width: 790px"></textarea>
                            </div>
                            <div class="submit">
                                <button class="stdbtn btn_orange" onclick="funcFour()">执 行 任 务</button>
                            </div>
                        </form>
                    </div>

                </div>


                <div id="fuck-five">
                    <div class="contenttitle2">
                        <h3>E  批量拉取商品库存信息|跨境通商品库存同步|接口标识：Inventory.ChannelQ4SBatchGet</h3>
                    </div>

                    <div class="statusbox" style="width: 800px">
                        <form id="json-post-e" action="#">
                            <div class="status_thumb">商品编号Json串：</div>
                            <div style="padding-right:20px;">
                                <textarea id="json-str-e" name="" cols="" rows="" style="height: 200px;width: 790px"></textarea>
                            </div>
                            <div class="submit">
                                <button class="stdbtn btn_orange" onclick="funcFive()">执 行 任 务</button>
                            </div>
                        </form>
                    </div>

                </div>



            </div>
        </div>
    </div>

</body>
</html>




























