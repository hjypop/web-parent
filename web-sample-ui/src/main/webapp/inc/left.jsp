<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<%-- nav 类代表导航栏 无任何样式应用|每个导航栏对应的菜单显示与否由id来决定--%>
        <div id="nav-bar-1" class="vernav2 iconmenu nav">
            <ul class="nav-bar-ul">
                <li class="current">
                    <a href="#formsub222222" class="editor">一级菜单栏A</a>
                    <span class="arrow"></span>
                    <ul id="formsub222222">
                        <li class="current">
                        	<!-- 必须添加 $ { basePath } -->
                        	<a href="">二级菜单栏A</a>
                        </li>
                        <li><a href="">二级菜单栏B</a></li>
                        <li><a href="">二级菜单栏C</a></li>
                        <li><a href="${basePath}seller/index.do">商户管理</a></li>
                    </ul>
                </li>

                <li>
                    <a href="" class="gallery">File Manager</a>
                </li>

                <!-- <li class="current">
                    <a href="#error" class="error">一级菜单栏B</a>
                    <span class="arrow"></span>
                    <ul id="error">
                        <li><a href="">二级菜单栏D</a></li>
                        <li><a href="">二级菜单栏E</a></li>
                        <li><a href="">二级菜单栏F</a></li>
                        <li><a href="">二级菜单栏G</a></li>
                    </ul>
                </li> -->

                <!-- <li class="current">
                    <a href="#addons" class="addons">一级菜单栏C</a>
                    <span class="arrow"></span>
                    <ul id="addons">
                        <li><a href="">二级菜单栏H</a></li>
                        <li><a href="">二级菜单栏I</a></li>
                        <li><a href="">二级菜单栏J</a></li>
                        <li><a href="">二级菜单栏K</a></li>
                        <li><a href="">二级菜单栏L</a></li>
                        <li><a href="">二级菜单栏N</a></li>
                    </ul>
                </li> -->


                <!-- 此处应放入到系统维护导航栏中-->
                 <li class="current">
                    <a href="#example" class="inbox">开发者快速入门</a>
                    <span class="arrow"></span>
                    <ul id="example">
                    	<li class="gallery">
                        	<a href="${basePath}example/addInfoPage.do">添加信息示例</a>
                        </li>
                        
                        <li class="gallery">
                        	<!-- 必须添加 $ { basePath } -->
                        	<a href="${basePath}example/pageFormExample.do">简单分页表单示例</a>
                        </li>
                        <li class="gallery">
                        	<a href="${basePath}example/ajaxFormExample.do">Ajax 分页示例</a>
                        </li>
                        <!-- <li class="gallery">
                        	<a href="${basePath}example/pageFormExample.do">分页+弹窗分页示例</a>
                        </li> -->
                        
                        <li class="gallery">
                        	<a href="${basePath}example/queryLeft.do">左连接查询示例</a>
                        </li>
                    </ul>
                </li>

                <!-- 此处应放入到系统维护导航栏中-->
                <li class="current">
                    <a href="#platform-user" class="inbox">平台用户指南</a>
                    <span class="arrow"></span>
                    <ul id="platform-user">
                        <li class="current">
                            <a href="">使用规则</a>
                        </li>

                        <li class="gallery">
                            <a href="">操作流程</a>
                        </li>

                        <li class="gallery">
                            <a href="">功能分类</a>
                        </li>

                        <li class="gallery">
                            <a href="">其他事项</a>
                        </li>

                        <li class="gallery">                     <!-- 此处应包括人员创建时间、注销时间、注销原因以及开发所涉及的模块 等 -->
                            <a href="">后台开发组名单</a>
                        </li>

                        <li class="gallery">                      <!-- 此处应包括人员创建时间、注销时间和注销原因等 -->
                            <a href="">运维组名单</a>
                        </li>
                    </ul>
                </li>

                <!-- 此处应放入到系统维护导航栏中-->
                <li class="current">
                    <a href="#platform-log" class="inbox">平台更新日志</a>   <!--  -->
                    <span class="arrow"></span>
                    <ul id="platform-log">
                        <li class="current">
                            <a href="">系统升级记录</a>
                        </li>

                        <li class="gallery">
                            <a href="">服务器地址变更记录</a>
                        </li>

                    </ul>
                </li>
            </ul>
            <a class="togglemenu"></a>

            <br /><br />
        </div>


        <%-- 切换导航栏的时候 这里的菜单来应该默认打开 从而配合【Plus】按钮进行菜单的全部打开或者全部收起--%>
        <div id="nav-bar-2" class="vernav2 iconmenu nav" style="display: none">

        </div>

        <div id="nav-bar-3" class="vernav2 iconmenu nav" style="display: none">

        </div>

        <div id="nav-bar-4" class="vernav2 iconmenu nav" style="display: none">

        </div>

















