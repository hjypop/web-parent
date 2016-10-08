

民生品萃后台订阅系统 | 这里有1000元预付款，请谨慎处理
http://www.minspc.com/minagent/
用户名：huijy
初始密码：123123


调用接口：www.minspc.com/Api/api
惠家有访问标识：
Appid=“huijy”
Secretkey=“adbaf6f4a0f6484fa783564f70a9ba21” 


定时任务业务执行顺序
	1.	 获取所有订阅产品 | JobForGetProductList
	2.  将所有跨境商户的订单 从oc_orderinfo表中导入到 job_exectimer 表中 | JobForKjSellerOrder
	3.  向民生品粹同步我平台的订单 | JobForCreateSubscribeOrder
	4.  订单状态变化 
		情况一：订单作废(即：取消订单) | JobForVoidOrder
		情况二：同步订单出关状态 | JobForGetOrderStatusList





TODO 等待升级内容，如下：

		加入同步日志表 ：lc_rsync_minspc_log 
		 
		job_exectimer表需要添加注释：
			exec_type字段 注释追加 【4民生品粹】-> 449746990014
			这里会写一个定时任务，从oc_orderinfo表中定时同步SF03MINSPC的订单到此表中
			【执行类型 1LD支付 2LD同步订单  3跨境通同步订单 4民生品粹】
		
		
		加入跨境商户拆单表 ：oc_kj_seller_separate_order	|所有外接跨境商户订单都会放入到这个表，涉及到拆单。
	
	
		因为是跨境商户，所以在 uc_sellerinfo 表和 uc_seller_info_extend 表需要插入数据；这些数据时通过后台来维护进入表中的
				维护后的数据为：SF03MINSPC，此表标识民生品粹的商品为跨境商品
				
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
