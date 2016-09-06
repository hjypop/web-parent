package com.hjy.controller;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.hjy.helper.SignHelper;
import com.hjy.request.Request;
import com.hjy.request.data.OrderDetailInsert;
import com.hjy.request.data.OrderInfoInsert;
import com.hjy.request.data.OrderInfoRequest;
import com.hjy.request.data.OrderInfoStatus;
import com.hjy.request.data.OrderShipment;
import com.hjy.request.data.OrderShipmentsRequest;

public class DataInit {
	
	public static void main(String[] args) {
		
		
	}
	
	
	public static Request ShipmentQuery(){
		Request r = new Request();
		r.setMethod("Order.ShipmentQuery");
		r.setAppid("SI10182"); 
		r.setTimestamp("2016-08-31 10:31:58");
		r.setNonce("4"); 
		r.setAppSecret("83c0e6f4aa5f11e39ee0000c298b20fc");

		OrderShipmentsRequest o = new OrderShipmentsRequest();
		o.setStartTime("2016-07-31 10:31:58");
		o.setEndTime("2016-08-31 10:31:58");
		r.setData(JSON.toJSONString(o)); 
		
		r.setSign(getSign(r));  
		return r; 
	}
	
	
	
	
	public static Request orderInfoBatchInsertTest(){
		Request r = new Request();
		r.setMethod("Order.Insert");
		r.setAppid("SI10182"); 
		r.setTimestamp("2016-08-30 11:31:58");
		r.setNonce("4"); 
		r.setAppSecret("83c0e6f4aa5f11e39ee0000c298b20fc");
		
		List<OrderInfoInsert> oList = new ArrayList<OrderInfoInsert>();
		String orderCode = "TBI88996";
		String producdCode = "PC88596-";
		String skuCode = "SKU12340g-";
		for(int i = 0 ; i < 50 ; i ++){
			OrderInfoInsert o = new OrderInfoInsert();
			o.setOrderCode(orderCode + i);
			o.setPayType("449716200009");              // 根据此值删除数据
			if(i % 9 == 0 ){
				o.setSendType(""); 
			}else{
				o.setSendType("449715210001");
			}
			int pm =  (int)(Math.random()*100);
			o.setProductMoney(BigDecimal.valueOf(Long.valueOf(pm))); 
			o.setTransportMoney(BigDecimal.ONE); 
			o.setOrderMoney(o.getProductMoney().add(BigDecimal.TEN));
			
			o.setPayedMoney(BigDecimal.valueOf(Double.valueOf(32.99)));   
			
			o.setProductName("窈窕淑女 - 我好逑！");
			o.setDueMoney(o.getOrderMoney()); 
			
			List<OrderDetailInsert> list = new ArrayList<OrderDetailInsert>();
			for(int j = 0 ; j < 3 ; j ++){
				OrderDetailInsert d = new OrderDetailInsert();
				d.setSkuCode(skuCode + pm);
				d.setProductCode(producdCode + pm);
				if(i % 2 == 0 ){
					d.setSkuName("性感黑丝袜"); 
				}else{
					d.setSkuName("振动棒"); 
				}
				d.setSkuPrice(BigDecimal.valueOf(72.9)); 
				d.setSkuNmu(1);
				d.setShowPrice(BigDecimal.valueOf(72.9)); 
				list.add(d);
			}
			o.setList(list);
			oList.add(o);
		}
		r.setData(JSON.toJSONString(oList)); 
		
		r.setSign(getSign(r));  
		return r; 
	}
	
	
	
	
	
	
	
	
	
	
	public static Request apiInsertShipmentsTest(){
		Request r = new Request();
		r.setMethod("Order.Shipments");
		r.setAppid("SI10000");
		r.setAppSecret("83c0dc04aa5f11e39ee0000c298b20fc");
		r.setTimestamp("2016-08-11 11:31:58");
		r.setNonce("4"); 
		r.setSign("c062f07197d91c8ae627fb059e0f4217"); 
		
	 
		List<OrderShipment> list = new ArrayList<OrderShipment>();
		OrderShipment a = new OrderShipment();
		a.setOrderCode("abc");
		a.setLogisticseCode("abc");
		a.setLogisticseName("abc");
		a.setWaybill("abc");
		a.setRemark("abc"); 
		list.add(a);
				
		OrderShipment b = new OrderShipment();
		b.setOrderCode("b");
		b.setLogisticseCode("b");
		b.setLogisticseName("b");
		b.setWaybill("ST229318770084");
		b.setRemark("b");
		list.add(b);
		
		OrderShipment c = new OrderShipment();
		c.setOrderCode("DD150626100408");
		c.setLogisticseCode("shunfeng");
		c.setLogisticseName("bbb2222");
		c.setWaybill("SF919652568865");
		c.setRemark("b43365水电费");		
		list.add(c);
				
		
		
		
		
		
		
		
		
		
//		OrderShipment a = new OrderShipment();
//		a.setOrderCode("DD150623100019");
//		a.setLogisticseCode("LC141013100001");
//		a.setLogisticseName("韵达物流1111");
//		a.setWaybill("YD86851247598");
//		a.setRemark("出库");
		
//		OrderShipment b = new OrderShipment();
//		b.setOrderCode("DD150623100134");
//		b.setLogisticseCode("shentong");
//		b.setLogisticseName("北京申通3333");
//		b.setWaybill("ST229318770084");
//		b.setRemark("马上发货");
		
//		OrderShipment c = new OrderShipment();
//		c.setOrderCode("DD150626100408");
//		c.setLogisticseCode("shunfeng");
//		c.setLogisticseName("顺风速运2222");
//		c.setWaybill("SF919652568865");
//		c.setRemark("货物已出库，马上发货");
		
//		OrderShipment d = new OrderShipment();      // 关键字段不全订单
//		d.setOrderCode("DD150629100423");
//		d.setLogisticseCode("");
//		d.setLogisticseName("顺风速运");        
//		d.setRemark("货物已出库，马上发货");
//		
//		OrderShipment e = new OrderShipment();    // 非惠家有订单
//		e.setOrderCode("DD00000000002");
//		e.setLogisticseCode("cainiao");
//		e.setLogisticseName("菜鸟快运");
//		e.setWaybill("CN823987888868");
//		e.setRemark("货物已出库，马上发货");
		
//		list.add(a);
//		list.add(b);
//		list.add(c);
//		list.add(d);
//		list.add(e);

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
	
	private static String getSign(Request request){
		String sign = "";
		try { 
			Map<String, String> map = new HashMap<String, String>();
			map.put("appid", request.getAppid());
			map.put("data", URLEncoder.encode(request.getData(), "UTF-8"));
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
			sign = SignHelper.md5Sign(str.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;
	}
}













