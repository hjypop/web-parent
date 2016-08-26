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
	
	
	/**
	 * @descriptions 效验后 查询物流信息
	 *  惠家有：商户；第三方：销售平台。
	 *  惠家有将物流信息发送给第三方，惠家有查询物流
	 *   
	 * @param json 
	 * @param sellerCode 
	 * @date 2016年8月26日下午4:47:44
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject apiSelectShipments(String json , String sellerCode); 
}


















