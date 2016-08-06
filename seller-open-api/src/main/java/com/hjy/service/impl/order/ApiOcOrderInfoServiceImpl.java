package com.hjy.service.impl.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.log.ILcOpenApiOrderStatusDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.entity.log.LcOpenApiOrderStatus;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.helper.DateHelper;
import com.hjy.helper.ExceptionHelpter;
import com.hjy.request.data.OrderInfoRequest;
import com.hjy.request.data.OrderInfoStatus;
import com.hjy.request.data.OrderInfoStatusRequest;
import com.hjy.response.data.OrderInfoResponse;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.order.IApiOcOrderInfoService;

@Service("apiOcOrderInfoService")
public class ApiOcOrderInfoServiceImpl extends BaseServiceImpl<OcOrderinfo, Integer> implements IApiOcOrderInfoService{
	
	private static Integer COUNT = 5000;      // 一次性批处理的数据数量

	@Resource
	private IOcOrderinfoDao dao;

	@Resource
	private ILcOpenApiOrderStatusDao openApiOrderStatusDao;
	
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
		// 解析请求数据
		OrderInfoRequest request = null;
		try {
			request = JSON.parseObject(json, OrderInfoRequest.class);
		} catch (Exception e) {
			result.put("code", 1);
			result.put("desc", "请求参数错误，请求数据解析异常");
			return result; 
		}
		
		
		if(StringUtils.isBlank(request.getSellerCode())){
			result.put("code", 14);
			result.put("desc", "请求参数seller code不得为空");
			return result;
		}
		String sellerCode = request.getSellerCode();
		// TODO 关联查询商家code是否存在
//		if(count == 0){
//			result.put("code", 3);
//			result.put("desc", "错误的商家编号，无API访问权限");
//			return result;
//		}
		
		List<OrderInfoResponse> list = dao.getOpenApiOrderinfoList(request);
		result.put("code", 0);
		result.put("desc", "请求成功");
		result.put("data", list);
		return result; 
	} 
	
	
	/**
	 * @descriptions 订单变更： 更新订单状态信息
	 * 	包含效验对方传入错误的订单
	 * 
	 * @param info
	 * @date 2016年8月3日上午10:23:53
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject updateOrderStatus(String json) {
		JSONObject result = new JSONObject();
		// 解析请求数据
		OrderInfoStatusRequest request = null;
		try {
			request = JSON.parseObject(json, OrderInfoStatusRequest.class);
		} catch (Exception e) {
			result.put("code", 1);
			result.put("desc", "请求参数错误，请求数据解析异常");
			return result;
		}
		String sellerCode = request.getSellerCode();
		// TODO 关联查询商家code是否存在
//		if(count == 0){
//			result.put("code", 3);
//			result.put("desc", "错误的商家编号，无API访问权限");
//			return result;
//		}
		
		List<OrderInfoStatus> list = request.getList();
		if(list.size() > COUNT){
			result.put("code", 1);
			result.put("desc", "请求参数错误，数据不得超过" + COUNT + "条");
			return result; 
		}
		if(list.size() == 0){
			result.put("code", 1);
			result.put("desc", "请求参数错误，数据不得为0条");
			return result; 
		}
		
		List<OrderInfoStatus> updateList = new ArrayList<OrderInfoStatus>();
		List<OrderInfoStatus> exceptionStatusList = new ArrayList<OrderInfoStatus>();
		for( int i = 0 ; i < list.size() ; i ++){
			list.get(i).setUpdateTime(DateHelper.formatDate(new Date()));
			if(this.validateOrderStatus(list.get(i))){
				updateList.add(list.get(i));
			}else{
				exceptionStatusList.add(list.get(i));
			}
		}
		
		int count = 0;
		OrderInfoStatus e = null;
		try {
			for(OrderInfoStatus o : updateList){
				e = o;
				dao.apiUpdateOrderinfoStatus(o);
				// 插入一条同步日志记录      zid   sellerCode  orderCode   orderStatus createTime 
				openApiOrderStatusDao.insertSelective(new LcOpenApiOrderStatus(sellerCode , o.getOrderCode() , o.getOrderStatus() , new Date() , "update success"));
				count ++;
			}
		} catch (Exception ex) {
			String remark_ = "update exception : {" + ExceptionHelpter.allExceptionInformation(ex)+ "}";  // 记录异常信息到数据库表
			openApiOrderStatusDao.insertSelective(new LcOpenApiOrderStatus(sellerCode , e.getOrderCode() , e.getOrderStatus() , new Date() , remark_));
			result.put("code", 11);
			result.put("desc", "平台内部错误，成功 " + count + " 条，失败 " + (updateList.size() - count) + " 条");
			return result; 
		}
		
		result.put("code", 0);
		result.put("desc", "请求成功，已同步 " + updateList.size() + " 条订单状态记录");
		if(exceptionStatusList.size() > 0){
			result.put("订单状态非法记录", exceptionStatusList);
		}
		return result;
	}
	
	
	/**
	 * @descriptions 效验订单状态
	 * 
	 * @param status 
	 * @date 2016年8月5日下午2:37:39
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	private boolean validateOrderStatus(OrderInfoStatus info){
		boolean flag = false;
		if(StringUtils.isBlank(info.getOrderCode())){
			return flag;
		}
		String status = info.getOrderStatus();
		if(StringUtils.startsWith(status, "449715390001000") && StringUtils.endsWithAny(status, new String[] {"1" , "2" , "3" , "4" , "5" , "6" , "7" })){
			flag = true;
		}
		return flag;
	}


	
}



























