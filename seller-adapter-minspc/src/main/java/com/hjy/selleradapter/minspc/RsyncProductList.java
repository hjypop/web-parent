package com.hjy.selleradapter.minspc;

import com.alibaba.fastjson.JSONObject;

public class RsyncProductList extends RsyncMinspc {

	@Override
	public String getRequestMethod() {
		return "Subscribe.ProductList";
	}

	
	//	Status：-1 （当前有库存的全部订阅产品列表）
	//	Status：0 （当前有库存的保税贸易订阅产品列表）
	//	Status：1 （当前有库存的海外直邮订阅产品列表）
	//	Status：2 （当前有库存的一般贸易订阅产品列表）
	public String setRequestDataJson() {
		return "{\"status\":\"-1\"}";
	}

	@Override
	public JSONObject doProcess(String responseJson) {
		// TODO Auto-generated method stub
		return null;
	}

}
