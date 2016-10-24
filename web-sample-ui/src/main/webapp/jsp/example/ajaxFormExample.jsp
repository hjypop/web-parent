<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/inc/head.jsp" %>
    <script type="text/javascript" src="${js}/system/ajax-form.js"></script>
    <script type="text/javascript">

        /**
         * Ajax 页面分页示例
         *
         * var data_ = null; 这里暂设置为null，这两处为空的地方可以根据实际情况处理。注意loadTable()也有。
         */
         $(function(){
             var type_ = 'post';
             var url_ = '${basePath}example/ajaxPageData.do';
             var data_ = null;  // 可以为null，后台会进行默认处理
             var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
             aForm.launch(url_ , 'table-form' , obj).init().drawForm(loadTable);
         });

         // 回调函数
         function loadTable(url_){
             if(url_ == undefined){ // 首次加载表单
                 draw(aForm.jsonObj);
                 return;
             }
             // 这种情况是响应上一页或下一页的触发事件
             var type_ = 'post';
             var data_ = {
                 userName: $("#user-name").val(),
                 mobile: $("#mobile").val(),
                 sex: $("#sex").val()
             }
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
 	                html_ += '<tr id="tr-' + list[i].id + '" class="gradeX">'
 	                +'<td align="center"><span class="center"> <input type="checkbox"/> </span></td>'
 	                +'<td width="100px">' + list[i].id + '</td>'
 	                +'<td>' + list[i].userName + '</td>'
 	                +'<td>' + list[i].mobile + '</td>'
 	                +'<td class="center">' + list[i].idNumber + '</td>'
 	                +'<td class="center">' + list[i].email + '</td>'
 	                +'<td width="150px" align="center">'
 	                +'<a onclick="deleteOne(\'' + list[i].id + '\')" title="删除"  style="cursor: pointer;">删除</a> | '
 	                +'<a href="${basePath}example/editInfoPage.do?id=' + list[i].id + '" title="修改"  style="cursor: pointer;">修改</a> ' 
 	                +'</td></tr>'
 	            }
             }else{
             	html_='<tr><td colspan="11" style="text-align: center;">'+obj.msg+'</td></tr>';
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
//                    $("#tr-" + id_).remove();
                    var currentPageNumber = $(".paginate_active").html();   // 定位到当前分页的页码，然后重新加载数据
                    aForm.formPaging(currentPageNumber);
                }else{
                    alert(obj.msg);
                }
            }
        }

        //搜索
        function searchUser(){
            aForm.formPaging(0);
        }

        // 重置查询条件
        function searchReset(){
            $("#user-name").val("");
            $("#mobile").val("");
            $("#sex").val("");
            aForm.formPaging(0);
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
                        这个页面描述了系统中最常用的功能：Ajax表单分页。该页面所在的路径已经隐藏在下面
                        【鼠标右键】 -> 【审查元素】可以看到如下路径【jsp/example/ajaxFormExample】
                    </span>
                <span style="display:none">jsp/example/ajaxFormExample</span>
            </div>

            <div id="contentwrapper" class="contentwrapper">

                <%-- dyntable2_wrapper --%>
                <div id="table-form" class="dataTables_wrapper" >
                    <div class="contenttitle2">
                        <p style="margin: 0px">
                            <label>姓名：</label>
							<span class="field">
								<input id="user-name" type="text" name="userName"  class="form-search"/>
							</span>

                            <label>手机号：</label>
							<span class="field">
								<input id="mobile" type="text" name="mobile"  class="form-search"/>
							</span>

                            <label>性别：</label>
							<span class="field">
								<select id="sex" name="sex" class="form-search">
                                    <option value="">请选择---</option>
                                    <option value="1">男</option>
                                    <option value="2">女</option>
                                </select>
							</span>

                            <a onclick="searchReset()" class="btn btn_orange btn_search radius50" style="float:right; cursor: pointer; margin-left: 10px">
                                <span> 重 置 </span>
                            </a>
                            <a onclick="searchUser()" class="btn btn_orange btn_search radius50" style="float:right; cursor: pointer;margin-left: 20px">
                                <span> 查 询 </span>
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
                                <th class="head0">tfoot</th>
                                <th class="head1">tfoot</th>
                                <th class="head0">tfoot</th>
                                <th class="head1">tfoot</th>
                                <th class="head0">tfoot</th>
                                <th class="head1">tfoot</th>
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




























