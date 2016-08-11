package com.hjy.controller.shipment;

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
import com.hjy.request.data.OrderShipment;
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
	 * @descriptions 订单物流变更：根据传入的json串插入物流信息 
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
		
		json = this.apiInsertShipmentsTest();
		
		Date requestTime = new Date();
		JSONObject result = service.apiInsertShipments(json , "seller code");  // TODO 此处解析后需要传入seller code 
		// sellerCode apiName classUrl requestJson responseJson createTime remark
		logService.insertSelective(new LcOpenApiOperation(UUID.randomUUID().toString().replace("-", ""),
				"seller code",   			// TODO 此处解析后需要传入seller code 
				"Order.Shipments",
				"ApiOcOrderShipmentsServiceImpl.apiInsertShipments",
				json,
				result.toJSONString(),
				new Date(), 
				requestTime,
				DateHelper.parseDate(result.getString("responseTime")),
				"remark"));
		return result;
	}
	
	
	
	/**
	 * @descriptions 为apiInsertShipments()方法模拟测试数据
	*	
	* json = this.apiInsertShipmentsTest();
	*
	* @date 2016年8月9日 下午8:16:46
	* @author Yangcl 
	* @version 1.0.0.1
	 */
	public String apiInsertShipmentsTest(){

		// SF0-OPEN-API-TEST-4  
		
		List<OrderShipment> list = new ArrayList<OrderShipment>();
		
		OrderShipment a = new OrderShipment();
		a.setUid("");
		a.setOrderCode("DD150916819918");
		a.setLogisticseCode("LC141013100001");
		a.setLogisticseName("韵达物流1");
		a.setWaybill("YD86851247598");
		a.setCreator("");
		a.setCreateTime(""); 
		a.setRemark(""); 
		
		OrderShipment b = new OrderShipment();
		b.setUid("");
		b.setOrderCode("DD150916819919");
		b.setLogisticseCode("shentong");
		b.setLogisticseName("北京申通2");
		b.setWaybill("ST229318770084");
		b.setCreator("");
		b.setCreateTime(""); 
		b.setRemark("申通"); 
		
		OrderShipment c = new OrderShipment();
		c.setUid("");
		c.setOrderCode("DD150916819920");
		c.setLogisticseCode("shunfeng");
		c.setLogisticseName("顺风速运");
		c.setWaybill("SF919652568865");
		c.setCreator("");
		c.setCreateTime(""); 
		c.setRemark("顺风"); 
		
		OrderShipment d = new OrderShipment();
		d.setUid("");
		d.setOrderCode("DD150916819921");
		d.setLogisticseCode("");
		d.setLogisticseName("顺风速运");
		d.setWaybill("");
		d.setCreator("");
		d.setCreateTime(""); 
		d.setRemark("关键字段不全订单"); 
		
		OrderShipment e = new OrderShipment();
		e.setUid("");
		e.setOrderCode("DD00000000002");
		e.setLogisticseCode("cainiao");
		e.setLogisticseName("菜鸟快运");
		e.setWaybill("CN823987888868");
		e.setCreator("");
		e.setCreateTime(""); 
		e.setRemark("非惠家有订单"); 
		
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		list.add(e);
//		list.add(f);
		
		return JSON.toJSONString(list);
	}
	
}



//OrderShipment f = new OrderShipment();
//f.setUid("");
//f.setOrderCode("DD150916819922");
//f.setLogisticseCode("");
//f.setLogisticseName("");
//f.setWaybill("");
//f.setCreator("");
//f.setCreateTime(""); 
//f.setRemark("非惠家有订单"); 


















