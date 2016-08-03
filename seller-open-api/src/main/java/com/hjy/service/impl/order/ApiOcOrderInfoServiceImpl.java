package com.hjy.service.impl.order;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.request.data.OrderInfoRequest;
import com.hjy.response.ApiResponse;
import com.hjy.response.data.OrderInfoResponse;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.order.IApiOcOrderInfoService;

@Service("apiOcOrderInfoService")
public class ApiOcOrderInfoServiceImpl extends BaseServiceImpl<OcOrderinfo, Integer> implements IApiOcOrderInfoService{

	@Resource
	private IOcOrderinfoDao dao;

	
	/**
	 * @descriptions 根据Json串查询订单信息|seller-open-api项目中使用
	 *
	 * @param json
	 * @date 2016年8月3日上午10:43:17
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
	@Override
	public JSONObject getOrderInfoByJson(String json) {
		JSONObject result = new JSONObject();
		ApiResponse<OrderInfoResponse> ar = new ApiResponse<OrderInfoResponse>(); 
		// 解析请求数据
		OrderInfoRequest request = JSON.parseObject(json, OrderInfoRequest.class);
		if(StringUtils.isBlank(request.getSellerCode())){
			ar.setCode(14);
			ar.setDesc("请求参数seller code不得为空"); 
			result.put("error-response", ar);
			return result;
		}
		
		
		
		
		
		
		
		
		return null;
	} 
	
}



























