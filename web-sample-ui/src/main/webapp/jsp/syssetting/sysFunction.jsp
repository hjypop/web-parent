<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/inc/head.jsp" %>
	
	<link rel="stylesheet" href="${css}/ztree/zTreeStyle.css" type="text/css" />
	<script type="text/javascript" src="${js}/ztree/jquery.ztree.all.js"></script>
    <script type="text/javascript" src="${js}/system/sys_function.js"></script>

    <style type="text/css">
        .tree-left{
            border: solid #FB9337 2px;
            height: 100%;
            width: 30%; 
            float: left;
        }
        .tree-right{
            border: solid #78CE07 2px;
            height: 100%;
            width: 30%;
            padding-top: 300px;
            padding-bottom: 300px;
            margin-right: 400px;
            float: right;
            position:relative;
        }
    </style>

    <script type="text/javascript">

        $(document).ready(function(){
            var jsonObj = JSON.parse('${jsonTree}');
            if(jsonObj.status == 'success'){
                var zNodes = jsonObj.list;
                $.fn.zTree.init($("#sys-tree"), setting, zNodes);
                $("#callbackTrigger").bind("change", {}, setTrigger);
            }

        });

    </script>
</head>

<body class="withvernav">

    <div class="bodywrapper">
		<%@ include file="/inc/roleTop.jsp" %> 
		<%@ include file="/inc/roleLeft.jsp" %>

        <div class="centercontent">

            <div class="pageheader">
                <h1 class="pagetitle" style="color:#ff0000;">FBI Warning</h1>
                <span class="pagedesc">
                    根据联邦法律规定，凡对未经授权，而对受版权保护的系统作品进行复制、发行或公开展出者，可导致严厉的民事或刑事处分（美国联邦法典第17篇，第501条与508条）。 
                </span>

                <ul class="hornav">
                    <li class="current">
                        <a href="#nav-menu">导航与菜单树</a>
                    </li>
                    <li>
                        <a href="#user-role">系统权限分配</a>
                    </li>
                </ul>
            </div>

            <div id="contentwrapper" class="contentwrapper">
                <div id="nav-menu" class="subcontent">
                    <div class="contenttitle2">
                        <h3>功能等级层次码 root=0 导航栏=1 一级菜单栏=2 二级菜单栏=3</h3>
                    </div>
                    <div class="stdform" >
                        <div class="tree-left">
							<div>
								<ul id="sys-tree" class="ztree"></ul>
							</div>
                        </div>

                        <div class="tree-right">

                        </div>
                    </div>
                </div>

                <div id="user-role" class="subcontent" style="display: none">
                    <form id="form1" class="stdform" method="post" action="">

                    </form>
                </div>
            </div>


        </div>


    </div>

</body>
</html>










