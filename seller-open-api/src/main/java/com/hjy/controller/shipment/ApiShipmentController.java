package com.hjy.controller.shipment;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hjy.entity.log.LcOpenApiOperation;
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
		JSONObject result = service.apiInsertShipments(json);
		// sellerCode apiName classUrl requestJson responseJson createTime remark
		logService.insertSelective(new LcOpenApiOperation(UUID.randomUUID().toString().replace("-", ""),
				result.getString("sellerCode") == null ? "错误的数据请求": result.getString("sellerCode") , 
				"insert_shipments",
				"com.hjy.controller.shipment.apiInsertShipments",
				json,
				result.toJSONString(),
				new Date(), "remark"));
		return result;
	}
	
	
	
	
	
}






















