<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


        <script type="text/javascript">
            $(function(){
                $('.vernav2 > ul > li > ul > li').removeClass("current");
                $("#" + getCookie('liselect')).addClass("current");

                $('.vernav2 > ul > li > ul > li').click("onclick" , function(){
                    clearCookie('liselect');
                    setCookie('liselect' , this.id , '1'); 
                });

            });
            function setCookie(cname, cvalue, exdays) {
              var d = new Date();
              d.setTime(d.getTime() + (exdays*24*60*60*1000));
              var expires = "expires="+d.toUTCString();
              document.cookie = cname + "=" + cvalue + "; " + expires + "; path=/";
            }
            
            function getCookie(c_name) {
                if (document.cookie.length > 0) {
                    var c_start = document.cookie.indexOf(c_name + "=");
                    if (c_start != -1) {
                      c_start = c_start + c_name.length + 1;
                      var c_end = document.cookie.indexOf(";", c_start);
                      if (c_end == -1)
                          c_end = document.cookie.length;
                      return unescape(document.cookie.substring(c_start, c_end));
                    }
                }
              return "";
            }

            function clearCookie(name) {
              setCookie(name, "", -1);
            }
        </script>

		<%-- nav 类代表导航栏 无任何样式应用|每个导航栏对应的菜单显示与否由id来决定--%>
        <div id="nav-bar-1" class="vernav2 iconmenu nav">
            <ul class="nav-bar-ul">
                <li class="current">
                    <a href="#formsub222222" class="editor">一级菜单栏A</a>
                    <span class="arrow"></span>
                    <ul id="formsub222222">
                        <li id="li-1">
                            <a href="${basePath}kjt/query.do"  target="_blank">跨境通问题查询</a>
                        </li>
                        <li id="li-2">
                            <a href="${basePath}kjt/validate.do">跨境通临时问题维护</a>
                        </li>
                        <li id="li-3">
                            <a href="${basePath}seller/index.do">商户管理</a>
                        </li>
                    </ul>
                </li>

                <!-- 此处应放入到系统维护导航栏中-->
                 <li class="current">
                    <a href="#example" class="inbox">开发者快速入门</a>
                    <span class="arrow"></span>
                    <ul id="example">
                    	<li id="li-4">
                        	<a href="${basePath}example/addInfoPage.do">添加信息示例</a>
                        </li>
                        
                        <li id="li-5">
                        	<a href="${basePath}example/pageFormExample.do">简单分页表单示例</a>
                        </li>
                        <li id="li-6">
                        	<a href="${basePath}example/ajaxFormExample.do">Ajax 分页示例</a>
                        </li>
                        
                        <li id="li-7">
                        	<a href="${basePath}example/ajaxFormDialogExample.do">Ajax 分页+弹出窗体分页 示例</a>
                        </li>

                        <li id="li-8">
                        	<a href="${basePath}example/alertExample.do">自定义 alert confirm note 示例</a>
                        </li>
                    </ul>
                </li>

                <!-- 此处应放入到系统维护导航栏中-->
                <li class="current">
                    <a href="#platform-user" class="inbox">平台用户指南</a>
                    <span class="arrow"></span>
                    <ul id="platform-user">
                        <li id="li-9">
                            <a href="">使用规则</a>
                        </li>

                        <li id="li-10">
                            <a href="">操作流程</a>
                        </li>

                        <li id="li-11">
                            <a href="">功能分类</a>
                        </li>

                        <li id="li-12">
                            <a href="">其他事项</a>
                        </li>

                        <li id="li-13">                     <!-- 此处应包括人员创建时间、注销时间、注销原因以及开发所涉及的模块 等 -->
                            <a href="">后台开发组名单</a>
                        </li>

                        <li id="li-14">                      <!-- 此处应包括人员创建时间、注销时间和注销原因等 -->
                            <a href="">运维组名单</a>
                        </li>
                    </ul>
                </li>

                <!-- 此处应放入到系统维护导航栏中-->
                <li class="current">
                    <a href="#platform-log" class="inbox">平台更新日志</a>   <!--  -->
                    <span class="arrow"></span>
                    <ul id="platform-log">
                        <li id="li-15">
                            <a href="">系统升级记录</a>
                        </li>

                        <li id="li-16">
                            <a href="">服务器地址变更记录</a>
                        </li>

                    </ul>
                </li>
            </ul>
            <a class="togglemenu"></a>
            <br />
            <br />
        </div>


        <%-- 切换导航栏的时候 这里的菜单来应该默认打开 从而配合【Plus】按钮进行菜单的全部打开或者全部收起--%>
        <div id="nav-bar-2" class="vernav2 iconmenu nav" style="display: none">

        </div>

        <div id="nav-bar-3" class="vernav2 iconmenu nav" style="display: none">

        </div>

        <div id="nav-bar-4" class="vernav2 iconmenu nav" style="display: none">

        </div>

















