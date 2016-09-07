package com.hjy.selleradapter.minspc;

import com.alibaba.fastjson.JSONObject;


/**
 * 
 * @title: com.hjy.selleradapter.minspc.RsyncSubscribeOrder.java 
 * @description: 生成订阅订单（并发送海关）|完成接口请求等内容。
 *
 * @author Yangcl
 * @date 2016年9月6日 下午2:36:49 
 * @version 1.0.0
 */
public class RsyncSubscribeOrder extends RsyncMinspc{

	public JSONObject doProcess(String responseJson) {
		// TODO 调用OpenApi 拼装响应数据
		
		return null;
	}

	public String getRequestMethod() {
		return "SubscribeOrder.Create";
	}

	public String setRequestDataJson() {
		return "";
	}

	

}
