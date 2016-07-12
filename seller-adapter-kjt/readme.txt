
访问地址：http://test-kjt.ycp8.cn/ckjt/

Xshell：
	地址：172.18.19.133
	用户名：root
	密码：admin@100
	日志目录：cd /opt/tomcat/logs
	所有运行记录：tail -f matrix_application.log
	所有Error级别错误记录：tail -f matrix_error_log


/etc/init.d/tomcat stop
/etc/init.d/tomcat start
/etc/init.d/tomcat restart
查看java进程：ps -ef |grep java
