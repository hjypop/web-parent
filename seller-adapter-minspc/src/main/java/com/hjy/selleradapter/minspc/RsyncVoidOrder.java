package com.hjy.selleradapter.minspc;

import com.alibaba.fastjson.JSONObject;

public class RsyncVoidOrder extends RsyncMinspc {

	@Override
	public String getRequestMethod() {
		return "Order.SOVoid";
	}

	@Override
	public String setRequestDataJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject doProcess(String responseJson) {
		// TODO Auto-generated method stub
		return null;
	}

}
