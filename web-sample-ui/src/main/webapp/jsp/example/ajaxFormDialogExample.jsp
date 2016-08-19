<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/inc/head.jsp" %>
    <script type="text/javascript" src="${js}/system/ajax-form.js"></script>
    <script src="${js}/blockUI/jquery.blockUI.js" type="text/javascript"></script>
    <script type="text/javascript">

        /**
         * Ajax 页面分页示例
         *
         * var data_ = null; 这里暂设置为null，这两处为空的地方可以根据实际情况处理。注意loadTable()也有。
         */
        $(function(){
            var type_ = 'post';
            var url_ = '${basePath}example/ajaxPageData.do';
            var data_ = null;
            var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
            if(obj.status == 'success'){
                aForm.launch(url_ , 'table-form' , obj).init().drawForm(loadTable);
            }
        });

        // 回调函数
        function loadTable(url_){
            if(url_ == undefined){ // 首次加载表单
                draw(aForm.jsonObj);
                console.log("A1")
                return;
            }
            // 这种情况是响应上一页或下一页的触发事件
            var type_ = 'post';
            var data_ = null;
            var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
            if(obj.status == 'success'){
                aForm.launch(url_ , 'table-form' , obj).init();
                console.log("B" + obj.data.pageNum);
                draw(obj);
            }
        }

        // 画表格
        function draw(obj){
            $('#ajax-tbody-1 tr').remove();
            var html_ = '';
            var list = obj.data.list;
            for(var i = 0 ; i < list.length ; i ++){
                html_ += '<tr id="tr-' + list[i].id + '" class="gradeX">'
                +'<td align="center"><span class="center"> <input type="checkbox"/> </span></td>'
                +'<td width="100px">' + list[i].id + '</td>'
                +'<td>' + list[i].userName + '</td>'
                +'<td>' + list[i].mobile + '</td>'
                +'<td class="center">' + list[i].idNumber + '</td>'
                +'<td class="center">' + list[i].email + '</td>'
                +'<td width="150px" align="center">'
                +'<a onclick="deleteOne(\'' + list[i].id + '\')" title="删除"  style="cursor: pointer;">删除</a> | '
                +'<a href="${basePath}example/editInfoPage.do?id=' + list[i].id + '" title="修改"  style="cursor: pointer;">修改</a> | '
                +'<a onclick="openDialogPage(\'' + list[i].id + '\')" style="cursor: pointer;">弹窗分页</a>'
                +'</td></tr>'
            }
            $('#ajax-tbody-1').append(html_);
        }




        function deleteOne(id_){
            if(confirm('您确定要删除这条记录吗？')){
                var type_ = 'post';
                var url_ = '${basePath}example/deleteOne.do';
                var data_ = {id:id_};
                var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
                if(obj.status == 'success'){
                    alert(obj.msg);
                    $("#tr-" + id_).remove();
                }else{
                    alert(obj.msg);
                }
            }
        }

        /**
         * @描述: 打开dialog insert BlockUI弹框
         * @作者: Yangcl
         * @时间: 2016-08-19 : 15-20-56
         */
        function openDialogPage(dialogFormId){
//            dialogFormNull(dialogFormId)
            $.blockUI({
                showOverlay:true ,
                css:  {
                    cursor:'auto',
                    left:($(window).width() - $("#dialog-page-div").width())/2 + 'px',
                    width:$("#dialog-page-div").width()+'px',
                    top:($(window).height()-$("#dialog-page-div").height())/2 + 'px',
                    position:'fixed', //居中
                    border: '3px solid #FB9337'                  // 无边界
                },
                message: $('#dialog-page-div'),
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




    </script>
</head>

<body class="withvernav">

    <div class="bodywrapper">
        <%@ include file="/inc/top.jsp" %>
        <%@ include file="/inc/left.jsp" %>

        <div class="centercontent tables">
            <!--这个跳转页面的功能及跳转路径等等-->
            <div class="pageheader notab">
                <h1 class="pagetitle">常用分页表单示例</h1>
                    <span class="pagedesc">
                        这个页面描述了系统中较高级的功能：Ajax分页 且弹窗同时分页。该页面所在的路径已经隐藏在下面
                        【鼠标右键】 -> 【审查元素】可以看到如下路径【jsp/example/ajaxFormDialogExample】
                    </span>
                <span style="display:none">jsp/example/ajaxFormDialogExample</span>
            </div>

            <div id="contentwrapper" class="contentwrapper">

                <%-- dyntable2_wrapper --%>
                <div id="table-form" class="dataTables_wrapper" >
                    <div class="contenttitle2">
                        <p style="margin: 0px">
                            <label>First Name</label>
                            <span class="field"><input id="name" type="text" name="name"  class="form-search"></span>

                            <label>First Name2</label>
                            <span class="field"><input id="name2" type="text" name="name"  class="form-search"></span>

                            <label>First Name3</label>
                            <span class="field"><input id="name3" type="text" name="name"  class="form-search"></span>

                            <label>First Name4</label>
                            <span class="field"><input id="name4" type="text" name="name"  class="form-search"></span>

                            <label>First Name4</label>
                            <span class="field"><input id="name5" type="text" name="name"  class="form-search"></span>

                            <label>First Name6</label>
                            <span class="field"><input id="name6" type="text" name="name"  class="form-search"></span>
                        </p>
                        <p style="margin:5px 0px 0px 0px;">
                            <label>First Name7</label>
                            <span class="field"><input id="name7" type="text" name="name"  class="form-search"></span>

                            <label>First Name8</label>
                            <span class="field"><input id="name8" type="text" name="name"  class="form-search"></span>
                        </p>
                        <p style="margin:5px 0px 0px 0px;">
                            <a href="" class="btn btn_orange btn_search radius50" style="float:right">
                                <span>Search</span>
                            </a>
                        </p>
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


<%-- Ajax分页 且弹窗同时分页 --%>
<div id="dialog-page-div" class="dialog-page-div" style="display: none;width: 1200px;height: 600px">
    <p class="dialog-title">
        <a href="#" onclick="closeDialog()" class="dialog-close"></a>
        Ajax分页 且弹窗同时分页
    </p>

    <div id="dialog-content-wrapper" class="contentwrapper">
        <div id="dialog-table-form" class="dataTables_wrapper" >
            <div id="dialog-dyntable" class=" dialog-show-count" >
                <label>
                    当前显示
                    <select id="dialog-select-page-size" size="1" name="dyntable2_length" onchange="aForm.formPaging('1')">
                        <option value="10">10</option>
                        <option value="25" >25</option>
                        <option value="50">50</option>
                        <option value="100">100</option>
                    </select>
                    条记录
                </label>
            </div>

            <%-- 以下内容根据你自己的业务需要进行修改--%>
            <table id="dialog-table" cellpadding="0" cellspacing="0" border="0" class="stdtable">
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

                <tbody id="dialog-ajax-tbody">
                <%-- 等待填充 --%>
                </tbody>
            </table>

        </div>
    </div>

</div>
























