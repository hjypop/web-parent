package com.hjy.dao.order;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.entity.order.OcOrderShipments;
import com.hjy.request.data.OrderShipment;

public interface IOcOrderShipmentsDao extends BaseDao<OcOrderShipments, Integer>{

	/**
	 * @descriptions open api 批量插入订单对应物流信息
	 * 
	 * @param list 
	 * @date 2016年8月5日上午11:38:21
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public Integer apiBatchInsert(List<OrderShipment> list );
	
	/**
	 * @descriptions 根据 order_code logisticse_code waybill 三个条件验证此条数据是否存在
	 * 
	 * @param info 
	 * @date 2016年8月10日下午2:55:59
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public OcOrderShipments findWayBill(OrderShipment info);
	
	
	public Integer updateSelectiveByUid(OcOrderShipments info);
}
