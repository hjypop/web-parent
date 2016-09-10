package com.hjy.selleradapter.minspc;

import com.alibaba.fastjson.JSONObject;

public class RsyncOrderOutput extends RsyncMinspc {

	@Override
	public String getRequestMethod() {
		return "Order.SOOutputCustoms";
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
