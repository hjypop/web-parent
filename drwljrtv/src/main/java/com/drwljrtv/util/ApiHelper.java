package com.drwljrtv.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core.system.PureNetUtil;

@Component
public class ApiHelper {

	private static ApiHelper self;

	public static ApiHelper getInstance() {
		if (self == null) {
			synchronized (ApiHelper.class) {
				if (self == null)
					self = new ApiHelper();
			}
		}
		return self;
	}

	/**
	 * 
	 * 方法: getResult <br>
	 * 描述: 获取接口返回结果 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月5日 下午7:59:19
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getResult(Map<String, String> param) {
		JSONObject obj = new JSONObject();
		String response = PureNetUtil.post(Constant.API_URL, param);
		if (StringUtils.isNotBlank(response)) {
			JSONObject result = JSONObject.parseObject(response);
			if (result != null) {
				obj = result.getJSONObject("data");
			}
		}
		return obj;
	}

	/**
	 * 
	 * 方法: getObj <br>
	 * 描述: 获取对象数据 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月5日 上午10:14:00
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getObj(Map<String, String> param) {
		JSONObject obj = new JSONObject();
		String response = PureNetUtil.post(Constant.API_URL, param);
		if (StringUtils.isNotBlank(response)) {
			JSONObject result = JSONObject.parseObject(response);
			if (result != null) {
				obj = result.getJSONObject("data");
			}
		}
		return obj;
	}

	/**
	 * 
	 * 方法: getData <br>
	 * 描述: 获取数据集合 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月5日 上午11:40:00
	 * 
	 * @param param
	 * @return
	 */
	public JSONArray getData(Map<String, String> param) {
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		String response = PureNetUtil.post(Constant.API_URL, param);
		if (StringUtils.isNotBlank(response)) {
			JSONObject result = JSONObject.parseObject(response);
			if (result != null) {
				obj = result.getJSONObject("data");
				if (StringUtils.equals(obj.getString("state"), "ok")) {
					array = obj.getJSONArray("data");
				}
			}
		}
		return array;
	}
}
