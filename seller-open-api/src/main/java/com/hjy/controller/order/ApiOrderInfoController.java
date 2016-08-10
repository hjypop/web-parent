package com.hjy.controller.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.entity.log.LcOpenApiOperation;
import com.hjy.helper.DateHelper;
import com.hjy.request.data.OrderInfoRequest;
import com.hjy.request.data.OrderInfoStatus;
import com.hjy.request.data.OrderInfoStatusRequest;
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
		OrderInfoRequest  ooo = new OrderInfoRequest();
		ooo.setSellerCode("SF03150617100010");
		json = JSON.toJSONString(ooo);
		
		Date requestTime = new Date();
		JSONObject result = service.getOrderInfoByJson(json);
		// sellerCode apiName classUrl requestJson responseJson createTime remark
		logService.insertSelective(new LcOpenApiOperation(UUID.randomUUID().toString().replace("-", ""),
				result.getString("sellerCode") == null ? "错误的数据请求": result.getString("sellerCode") , 
				"list",
				"com.hjy.controller.order.apiGetOrderInfo",
				json,
				result.toJSONString(),
				new Date(), 
				requestTime,
				DateHelper.parseDate(result.getString("responseTime")), 
				"remark"));
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
		
		json = this.apiUpdateOrderStatusTest();
		
		Date requestTime = new Date();
		JSONObject result = service.updateOrderStatus(json);
		// sellerCode apiName classUrl requestJson responseJson createTime remark
		logService.insertSelective(new LcOpenApiOperation(UUID.randomUUID().toString().replace("-", ""),
				result.getString("sellerCode") == null ? "错误的数据请求": result.getString("sellerCode") , 
				"update_order_status",
				"com.hjy.controller.order.apiUpdateOrderStatus",
				json,
				result.toJSONString(),
				new Date(), 
				requestTime,
				DateHelper.parseDate(result.getString("responseTime")), 
				"remark"));
		return result;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * @descriptions 为apiUpdateOrderStatus()方法模拟测试数据
	*	
	* json = this.apiUpdateOrderStatusTest();
	*
	* @date 2016年8月9日 下午8:16:46
	* @author Yangcl 
	* @version 1.0.0.1
	 */
	public String apiUpdateOrderStatusTest(){
		OrderInfoStatusRequest info = new OrderInfoStatusRequest();
		info.setCreateTime(DateHelper.formatDate(new Date())); 
		info.setSellerCode("SF0-OPEN-API-TEST-4");
		info.setSellerKey(""); 
		List< OrderInfoStatus> list = new ArrayList<OrderInfoStatus>();
		OrderInfoStatus os1 = new OrderInfoStatus();
		os1.setOrderCode("DD150916819918");
		os1.setOrderStatus("449715390001000399");// 模拟状态错误
		os1.setUpdateTime("2018-08-08 18:08:08");
		
		OrderInfoStatus os2 = new OrderInfoStatus();
		os2.setOrderCode("DD150916819919");
		os2.setOrderStatus("4497153900010002");
		os2.setUpdateTime("2019-08-08 18:08:08");
		
		OrderInfoStatus os3 = new OrderInfoStatus();
		os3.setOrderCode("DD150916819920");
		os3.setOrderStatus("4497153900010002");
		os3.setUpdateTime("2019-08-08 18:08:08");
		
		OrderInfoStatus os4 = new OrderInfoStatus();// 模拟非商户订单
		os4.setOrderCode("DD150916808992");
		os4.setOrderStatus("4497153900010004");
		os4.setUpdateTime("2018-08-08 18:08:08");
		
		list.add(os1);
		list.add(os2);
		list.add(os3);
		list.add(os4);
		info.setList(list);
		
		
		return JSON.toJSONString(info); 
	}
}




















