

民生品萃后台订阅系统 | 这里有1000元预付款，请谨慎处理
http://www.minspc.com/minagent/
用户名：huijy
初始密码：123123


调用接口：www.minspc.com/Api/api
惠家有访问标识：
Appid=“huijy”
Secretkey=“adbaf6f4a0f6484fa783564f70a9ba21” 





加入同步日志表 ：lc_rsync_minspc_log 
加入日志记录表：lc_rsync_minspc_product
CREATE TABLE `lc_rsync_minspc_product` (
  `zid` int(11) NOT NULL AUTO_INCREMENT,
  `response_json` longtext COMMENT '响应数据报文',
  `success_list` longtext COMMENT '同步成功的商品记录',
  `error_list` longtext COMMENT '同步失败的商品记录以及每条记录对应的错误信息',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  `remark` varchar(20) DEFAULT 'success' COMMENT '响应数据报文描述',
  PRIMARY KEY (`zid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
