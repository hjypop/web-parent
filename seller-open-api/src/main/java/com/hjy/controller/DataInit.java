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
import com.hjy.request.data.OrderInfoStatus;

public class DataInit {
	
	public static void main(String[] args) {
		
		Request request = DataInit.apiUpdateOrderStatusTest();
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
 
	public static Request apiUpdateOrderStatusTest(){
		
		Request r = new Request();
		r.setMethod("Order.UpdateOrderStatus");
		r.setAppid("appid-1-d-e-r-t");
		r.setAppSecret("1122334");
		r.setTimestamp("2016-08-11 11:31:58");
		r.setNonce("4"); 
		r.setSign("f6c423d220fe5dbac0d6749124c56af7"); 
		
		
		List< OrderInfoStatus> list = new ArrayList<OrderInfoStatus>();
		OrderInfoStatus os1 = new OrderInfoStatus();
		os1.setOrderCode("DD150916819918");
		os1.setOrderStatus("449715390001000399");// 模拟状态错误
		os1.setUpdateTime("2018-08-08 18:08:08");
		
		OrderInfoStatus os2 = new OrderInfoStatus();
		os2.setOrderCode("DD150916819919");
		os2.setOrderStatus("4497153900010004");
		os2.setUpdateTime("2019-08-08 18:08:08");
		
		OrderInfoStatus os3 = new OrderInfoStatus();
		os3.setOrderCode("DD150916819920");
		os3.setOrderStatus("4497153900010004");
		os3.setUpdateTime("2019-08-08 18:08:08");
		
		OrderInfoStatus os4 = new OrderInfoStatus();// 模拟非商户订单
		os4.setOrderCode("DD150916808992");
		os4.setOrderStatus("4497153900010004");
		os4.setUpdateTime("2018-08-08 18:08:08");
		
		list.add(os1);
		list.add(os2);
		list.add(os3);
		list.add(os4);
		r.setData(JSON.toJSONString(list)); 
		
		return r; 
	}
}













