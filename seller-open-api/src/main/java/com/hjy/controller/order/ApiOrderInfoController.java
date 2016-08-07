package com.hjy.controller.order;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.entity.log.LcOpenApiOperation;
import com.hjy.request.data.OrderInfoRequest;
import com.hjy.service.operation.IApiLcOpenApiOperationService;
import com.hjy.service.order.IApiOcOrderInfoService;


/**
 * @descriptions 对外提供订单信息相关接口
 * 
 * @date 2016年8月3日上午10:19:11
 * @author Yangcl
 * @version 1.0.1
 */
@Controller
@RequestMapping("order_info")
public class ApiOrderInfoController {
	private static Logger logger=Logger.getLogger(ApiOrderInfoController.class);
	
	@Autowired
	private IApiOcOrderInfoService service;
	
	@Autowired
	private IApiLcOpenApiOperationService logService;
	
	/**
	 * @descriptions 根据传入的json串查询订单信息
	 * 
	 * @param info
	 * @date 2016年8月3日上午10:23:53
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject apiGetOrderInfo(String json){
//		OrderInfoRequest  ooo = new OrderInfoRequest();
//		ooo.setSellerCode("SF03150617100010");
//		json = JSON.toJSONString(ooo);
		
		JSONObject result = service.getOrderInfoByJson(json);
		// sellerCode apiName classUrl requestJson responseJson createTime remark
		logService.insertSelective(new LcOpenApiOperation(UUID.randomUUID().toString().replace("-", ""),
				result.getString("sellerCode")  , 
				"list",
				"com.hjy.controller.order.apiGetOrderInfo",
				json,
				result.toJSONString(),
				new Date(), "remark"));
		return  result;
	}
	

	/**
	 * @descriptions 订单变更： 更新订单状态信息
	 * 	包含效验对方传入错误的订单
	 * 
	 * @param info
	 * @date 2016年8月3日上午10:23:53
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "update_order_status", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject apiUpdateOrderStatus(String json){
		JSONObject result = service.updateOrderStatus(json);
		// sellerCode apiName classUrl requestJson responseJson createTime remark
		logService.insertSelective(new LcOpenApiOperation(UUID.randomUUID().toString().replace("-", ""),
				result.getString("sellerCode")  , 
				"update_order_status",
				"com.hjy.controller.order.apiUpdateOrderStatus",
				json,
				result.toJSONString(),
				new Date(), "remark"));
		return result;
	}
	
}




















