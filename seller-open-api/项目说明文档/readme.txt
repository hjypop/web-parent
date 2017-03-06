

Yangcl标记如下：
ApiOcOrderInfoServiceImpl -> updateOrderStatus(String json) 
需要加入新的日志表 lc_open_api_order_status 


ApiOcOrderShipmentsServiceImpl -> apiInsertShipments 
需要加入新的日志表 lc_open_api_shipment_status | 

加入新的日志表 lc_open_api_operation  记录所有接口调用情况 

加入新的表，用于记录 open api 商户的 appid 和 惠家有的small_seller_code的关联信息
open_api_appid


加入新的日志表 lc_open_api_order_insert 记录订单批量插入情况
 


`lc_open_api_operation`
`lc_open_api_order_insert`
`lc_open_api_product_error`
`lc_open_api_order_status`
`lc_open_api_query_log`
`lc_open_api_shipment_status`



线上访问地址：
	http://api-open.huijiayou.cn/openapi/openapi.do

xshell：
	10.20.2.136
	dev
	juqCPv6xRW74N4i2Y-C_aV



Yangcl
		等待处理一下功能未实现：
				pc_product_authority_logo 表，需要根据商户类型进行判断，关联一个商品的商户自定义标识，跨境商品，统一设置为不支持7天退货。

















