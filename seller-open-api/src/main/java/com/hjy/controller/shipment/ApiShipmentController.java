package com.hjy.controller.shipment;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hjy.service.operation.IApiLcOpenApiOperationService;
import com.hjy.service.shipment.IApiOcOrderShipmentsService;


/**
 * @descriptions 对外提供物流信息相关接口
 * 
 * @date 2016年8月3日上午10:19:11
 * @author Yangcl
 * @version 1.0.1
 */
@Controller
@RequestMapping("shipment")
public class ApiShipmentController {
	private static Logger logger=Logger.getLogger(ApiShipmentController.class);
	
	@Autowired
	private IApiOcOrderShipmentsService service;
	
	@Autowired
	private IApiLcOpenApiOperationService logService;
	
	/**
	 * @descriptions 订单变更：根据传入的json串插入物流信息 
	 * 	包含效验对方传入错误的订单
	 * 
	 * @param info
	 * @date 2016年8月3日上午10:23:53
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "insert_shipments", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject apiInsertShipments(String json){
		
		return service.apiInsertShipments(json);
	}
	
	
	
	
	
}





















