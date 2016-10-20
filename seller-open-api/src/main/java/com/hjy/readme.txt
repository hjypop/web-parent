

Yangcl标记如下：
ApiOcOrderInfoServiceImpl -> updateOrderStatus(String json) 需要加入新的日志表 lc_open_api_order_status |zid   sellerCode  orderCode   orderStatus createTime
新添加了一个字段 flag  1 成功 2失败，失败则remark字段保存的异常信息


ApiOcOrderShipmentsServiceImpl -> apiInsertShipments 需要加入新的日志表 lc_open_api_shipment_status |shipmentUid sellerCode orderCode logisticseName wayBill  flag 1 成功 2失败 createTime remark

加入新的日志表 lc_open_api_operation  记录所有接口调用情况|sellerCode apiName classUrl requestJson responseJson createTime remark;

加入新的表，用于记录 open api 商户的 appid 和 惠家有的small_seller_code的关联信息
open_api_appid


加入新的日志表 lc_open_api_order_insert 记录订单批量插入情况
 


`lc_open_api_operation`
`lc_open_api_order_insert`
`lc_open_api_product_error`
`lc_open_api_order_status`
`lc_open_api_query_log`
`lc_open_api_shipment_status`












