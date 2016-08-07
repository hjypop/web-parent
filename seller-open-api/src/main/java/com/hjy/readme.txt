

Yangcl标记如下：
ApiOcOrderInfoServiceImpl -> updateOrderStatus(String json) 需要加入新的日志表 lc_open_api_order_status |zid   sellerCode  orderCode   orderStatus createTime
新添加了一个字段 flag  1 成功 2失败，失败则remark字段保存的异常信息


ApiOcOrderShipmentsServiceImpl -> apiInsertShipments 需要加入新的日志表 lc_open_api_shipment_status |shipmentUid sellerCode orderCode logisticseName wayBill  flag 1 成功 2失败 createTime remark

加入新的日志表 lc_open_api_operation  记录所有接口调用情况|sellerCode apiName classUrl requestJson responseJson createTime remark;

