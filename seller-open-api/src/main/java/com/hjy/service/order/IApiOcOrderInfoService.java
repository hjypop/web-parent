package com.hjy.service.order;

import com.alibaba.fastjson.JSONObject;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.service.IBaseService;

public interface IApiOcOrderInfoService  extends IBaseService<OcOrderinfo, Integer>{

	/**
	 * @descriptions 根据Json串查询订单信息|seller-open-api项目中使用
	 *
	 * @param json
	 * @date 2016年8月3日上午10:43:17
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
	public JSONObject getOrderInfoByJson(String json);
}
