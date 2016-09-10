package com.hjy.selleradapter.minspc;

import com.alibaba.fastjson.JSONObject;

public class RsyncOrderStatusList extends RsyncMinspc {

	@Override
	public String getRequestMethod() {
		// TODO Auto-generated method stub
		return "Order.SOTrace";
	}

	@Override
	public String setRequestDataJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doProcess(String responseJson) {
		// TODO Auto-generated method stub
	}

}
