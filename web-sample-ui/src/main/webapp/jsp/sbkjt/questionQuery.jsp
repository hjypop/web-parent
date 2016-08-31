<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/inc/head.jsp" %>
    <script type="text/javascript" src="${js}/system/ajax-form.js"></script>
    <script type="text/javascript">

        $(function(){

            //datepicker
            $('#datepicker').datepicker();

            //show tabbed widget
            $('#tabs').tabs();

            //accordion
            $('#accordion').accordion();

            //content slider
            $('#slidercontent').bxSlider({
                prevText: '',
                nextText: ''
            });

            //slim scroll
            $('#scroll1').slimscroll({
                color: '#666',
                size: '10px',
                width: 'auto',
                height: '208px'
            });


        });

    </script>
</head>

<body class="withvernav">

<div class="bodywrapper">
    <%@ include file="/inc/top.jsp" %>
    <%@ include file="/inc/left.jsp" %>

    <div class="centercontent tables">
        <div class="pageheader notab">
            <h1 class="pagetitle">日志查询与数据对比</h1>
                    <span class="pagedesc">
                        定位跨境通问题订单
                    </span>
            <span style="display:none">jsp/sbkjt/validate.jsp</span>
        </div>

        <div id="contentwrapper" class="contentwrapper">
            <div id="table-form" class="dataTables_wrapper" >
                <div class="contenttitle2">
                    <p style="margin: 0px">
                        <label>First Name</label>
                        <span class="field"><input id="name" type="text" name="name"  class="form-search"></span>



                    </p>

                    <p style="margin:5px 0px 0px 0px;">
                        <a href="" class="btn btn_orange btn_search radius50" style="float:right">
                            <span>执行查询</span>
                        </a>
                    </p>
                </div>

                <div id="dyntable2_length" class="dataTables_length">
                    <label>
                        当前显示
                        <%-- TODO 注意：select-page-size 这个ID是写定的，如果没有这个显示条数，则默认显示10条 - Yangcl --%>
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
                    <colgroup>
                        <col class="con0" style="width: 4%"/>
                        <col class="con1"/>
                        <col class="con0"/>
                        <col class="con1"/>
                        <col class="con0"/>
                    </colgroup>
                    <thead>
                    <tr>
                        <th class="head0 nosort">
                            <input type="checkbox"/>
                        </th>                                                                           <%-- sorting 代表可排序--%>
                        <th class="head0 sorting_asc">ID(升序排序)</th>  <%-- sorting_asc 代表升序排列--%>
                        <th class="head1 sorting_desc"> 姓名(降序排序)</th>   <%-- sorting_desc 代表降序排列--%>
                        <th class="head0 sorting">手机(s)</th>
                        <th class="head1 sorting">身份证号</th>
                        <th class="head0 sorting">E-mail</th>
                        <th class="head1 " width="100px">操作</th>
                    </tr>
                    </thead>

                    <tfoot>
                    <tr>
                        <th class="head0">
                                        <span class="center">
                                            <input type="checkbox"/>
                                         </span>
                        </th>
                        <th class="head0">Rendering engine</th>
                        <th class="head1">Browser</th>
                        <th class="head0">Platform(s)</th>
                        <th class="head1">Engine version</th>
                        <th class="head0">CSS grade</th>
                        <th class="head1">To do it</th>
                    </tr>
                    </tfoot>

                    <tbody id="ajax-tbody-1">
                    <%-- 等待填充 --%>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>

</body>
</html>




























