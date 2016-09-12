

民生品萃后台订阅系统 | 这里有1000元预付款，请谨慎处理
http://www.minspc.com/minagent/
用户名：huijy
初始密码：123123


调用接口：www.minspc.com/Api/api
惠家有访问标识：
Appid=“huijy”
Secretkey=“adbaf6f4a0f6484fa783564f70a9ba21” 



TODO 等待升级内容，如下：

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


job_exectimer表需要添加注释：
	exec_type字段 注释追加 【4民生品粹】-> 449746990004
	这里会写一个定时任务，从oc_orderinfo表中定时同步SF03MINSPC的订单到此表中



  SELECT 
	p.product_code_old AS productID, # minspcCode 
	de.sku_num AS quantity,#购买数量
	sku.sell_price AS salePrice,
	de.tax_rate AS taxRate 
  FROM 
	ordercenter.`oc_orderdetail` de 
	LEFT JOIN productcenter.`pc_productinfo` p ON de.product_code = p.product_code
	LEFT JOIN productcenter.`pc_skuinfo` sku ON de.sku_code = sku.sku_code
  WHERE 
	de.order_code='DD4382872102' AND p.small_seller_code = 'SF03KJT'
	
	
	
	
	
	
	
	
	
	
	
