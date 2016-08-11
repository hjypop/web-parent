package com.hjy.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.hjy.common.bill.HexUtil;
import com.hjy.common.bill.MD5Util;
import com.hjy.request.Request;
import com.hjy.request.data.OrderInfoRequest;
import com.hjy.request.data.OrderInfoStatus;
import com.hjy.request.data.OrderShipment;

public class DataInit {
	
	public static void main(String[] args) {
		
		Request request = DataInit.apiInsertShipmentsTest();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", request.getAppid());
		map.put("data", request.getData());
		map.put("method", request.getMethod());
		map.put("timestamp", request.getTimestamp());
		map.put("nonce", request.getNonce());
		List<String> list = new ArrayList<String>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		Collections.sort(list); // 对List内容进行排序
		StringBuffer str = new StringBuffer();
		for (String nameString : list) {
			str.append(nameString);
		}
		str.append(request.getAppSecret());
		String sign = HexUtil.toHexString(MD5Util.md5(str.toString()));
		
		System.out.println(sign); 
	}
	
	
	public static Request apiInsertShipmentsTest(){
		Request r = new Request();
		r.setMethod("Order.Shipments");
		r.setAppid("appid-shipments-insert");
		r.setAppSecret("1122334");
		r.setTimestamp("2016-08-11 11:31:58");
		r.setNonce("4"); 
		r.setSign("ce808b5149d02f81fb6881e10da67a06"); 
		
	 
		List<OrderShipment> list = new ArrayList<OrderShipment>();
		OrderShipment a = new OrderShipment();
		a.setOrderCode("DD150623100019");
		a.setLogisticseCode("LC141013100001");
		a.setLogisticseName("韵达物流1111");
		a.setWaybill("YD86851247598");
		a.setRemark("货物已出库");
		
		OrderShipment b = new OrderShipment();
		b.setOrderCode("DD150623100134");
		b.setLogisticseCode("shentong");
		b.setLogisticseName("北京申通3333");
		b.setWaybill("ST229318770084");
		b.setRemark("马上发货");
		
		OrderShipment c = new OrderShipment();
		c.setOrderCode("DD150626100408");
		c.setLogisticseCode("shunfeng");
		c.setLogisticseName("顺风速运2222");
		c.setWaybill("SF919652568865");
		c.setRemark("货物已出库，马上发货");
		
		OrderShipment d = new OrderShipment();      // 关键字段不全订单
		d.setOrderCode("DD150629100423");
		d.setLogisticseCode("");
		d.setLogisticseName("顺风速运");        
		d.setRemark("货物已出库，马上发货");
		
		OrderShipment e = new OrderShipment();    // 非惠家有订单
		e.setOrderCode("DD00000000002");
		e.setLogisticseCode("cainiao");
		e.setLogisticseName("菜鸟快运");
		e.setWaybill("CN823987888868");
		e.setRemark("货物已出库，马上发货");
		
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		list.add(e);

		r.setData(JSON.toJSONString(list)); 
		
		return r; 
	}
	
	public static Request getOrderInfoByJsonTest(){
		Request r = new Request();
		r.setMethod("Order.List");
		r.setAppid("appid-order-list");
		r.setAppSecret("1122334");
		r.setTimestamp("2016-08-11 11:31:58");
		r.setNonce("4"); 
		r.setSign("d7e67b07c7983da4603dd467b2cf6f78"); 
		
		OrderInfoRequest info = new OrderInfoRequest();
		
		r.setData(JSON.toJSONString(info)); 
		
		return r; 
	}
 
	public static Request apiUpdateOrderStatusTest(){
		
		Request r = new Request();
		r.setMethod("Order.UpdateOrderStatus");
		r.setAppid("appid-1-d-e-r-t");
		r.setAppSecret("1122334");
		r.setTimestamp("2016-08-11 11:31:58");
		r.setNonce("4"); 
		r.setSign("78c6fd898937c90cbbb07e40648afd2f"); 
		
		
		List< OrderInfoStatus> list = new ArrayList<OrderInfoStatus>();
		OrderInfoStatus os1 = new OrderInfoStatus();
		os1.setOrderCode("DD150916819918");
		os1.setOrderStatus("449715390001000399");// 模拟状态错误 
		
		OrderInfoStatus os2 = new OrderInfoStatus();
		os2.setOrderCode("DD150916819919");
		os2.setOrderStatus("4497153900010004"); 
		
		OrderInfoStatus os3 = new OrderInfoStatus();
		os3.setOrderCode("DD150916819920");
		os3.setOrderStatus("4497153900010004"); 
		
		OrderInfoStatus os4 = new OrderInfoStatus();// 模拟非商户订单
		os4.setOrderCode("DD150916808992");
		os4.setOrderStatus("4497153900010004"); 
		
		list.add(os1);
		list.add(os2);
		list.add(os3);
		list.add(os4);
		r.setData(JSON.toJSONString(list)); 
		
		return r; 
	}
}













