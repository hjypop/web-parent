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
	public JSONObject getOrderInfoByJson(String json , String sellerCode);
	
	
	/**
	 * @descriptions 订单变更： 更新订单状态信息
	 * 	包含效验对方传入错误的订单
	 * 
	 * @param info
	 * @date 2016年8月3日上午10:23:53
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject updateOrderStatus(String json , String sellerCode);
	
	
	/**
	 * @descriptions 插入订单状态信息
	 *  惠家有：商户；第三方：销售平台。
	 *  第三方将订单信息发送给惠家有，惠家有插入订单
	 * 
	 * @param info
	 * @date 2016年8月26日下午4:38:58
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject insertOrder(String json , String sellerCode);
	
	
	
	
}















