package com.hjy.service.shipment;

import com.alibaba.fastjson.JSONObject;
import com.hjy.entity.order.OcOrderShipments;
import com.hjy.service.IBaseService;


public interface IApiOcOrderShipmentsService  extends IBaseService<OcOrderShipments, Integer>{
	
	/**
	 * @descriptions 效验订单后插入物流信息
	 * 
	 * @param json 
	 * @date 2016年8月5日上午10:59:24
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject apiInsertShipments(String json , String sellerCode);
}
