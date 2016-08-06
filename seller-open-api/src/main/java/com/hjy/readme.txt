

Yangcl标记如下：
ApiOcOrderInfoServiceImpl -> updateOrderStatus(String json) 需要加入新的日志表 lc_open_api_order_status |zid   sellerCode  orderCode   orderStatus createTime
新添加了一个字段 flag  1 成功 2失败，失败则remark字段保存的异常信息



lc_open_api_shipment_status


