<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/inc/head.jsp" %>
    <script type="text/javascript"></script>
</head>

<body class="withvernav">

    <div class="bodywrapper">
        <%@ include file="/inc/top.jsp" %>
        <%@ include file="/inc/left.jsp" %>

        <div class="centercontent tables">
            <div class="pageheader notab">
                <h1 class="pagetitle">设计者验证</h1>
                    <span class="pagedesc">
                        输入秘钥，执行危险操作
                    </span>
                <span style="display:none">jsp/sbkjt/validate.jsp</span>
            </div>

            <div id="contentwrapper" class="contentwrapper">
                <div id="fuck-two">
                    <div class="contenttitle2">
                        <h3>请输入你的秘钥</h3>
                    </div>

                    <div class="statusbox" style="width: 800px">
                        <form id="date-post" action="${basePath}kjt/index.do">
                            <div style="padding-right:20px;">
                                <div class="commentcontent" style="padding-right: 12px;margin-left:65px;">
                                    <input  id="key" name="key" type="password" class="the-password">
                                </div>
                            </div>
                            <div class="submit">
                                <input type="submit" class="stdbtn btn_orange"  value=" 验 证 秘 钥">
                            </div>
                        </form>
                    </div>
                </div>

                <c:if test="${status == true}">
                    <div id="error">
                        <div class="contenttitle2">
                            <h3>${msg}</h3>
                        </div>
                    </div>
                </c:if>


            </div>
        </div>
    </div>

</body>
</html>




























