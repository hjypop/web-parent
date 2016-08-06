package com.hjy.service.impl.shipment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.order.IOcOrderShipmentsDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.entity.order.OcOrderShipments;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.helper.DateHelper;
import com.hjy.request.data.OrderShipment;
import com.hjy.request.data.ShipmentRequest;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.shipment.IApiOcOrderShipmentsService;

/**
 * @descriptions api 订单物流信息同步接口 
 * 
 * @date 2016年8月5日上午10:45:32
 * @author Yangcl
 * @version 1.0.1
 */
@Service("apiOcOrderShipmentsService")
public class ApiOcOrderShipmentsServiceImpl extends BaseServiceImpl<OcOrderShipments, Integer> implements IApiOcOrderShipmentsService{

	private static Integer COUNT = 5000;      // 一次性批处理的数据数量
	
	@Resource
	private IOcOrderShipmentsDao dao ;
	
	@Resource
	private IOcOrderinfoDao orderinfoDao;
	
	/**
	 * @descriptions 效验订单后插入物流信息
	 * 
	 * @param json 
	 * @date 2016年8月5日上午10:59:24
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject apiInsertShipments(String json) {
		JSONObject result = new JSONObject();
		// 解析请求数据
		ShipmentRequest request = null;
		try {
			request = JSON.parseObject(json, ShipmentRequest.class);
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
		List<OrderShipment> list = request.getList();
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
		
		List<OrderShipment> correctList = new ArrayList<OrderShipment>(); // 保存合法的物流信息
		List<OrderShipment> exceptionOrderList = new ArrayList<OrderShipment>();  // 异常的订单物流信息|关键字段不全，不做处理，返回给调用方
		for(int i = 0 ; i < list.size() ; i ++){
			if(StringUtils.isNotBlank(list.get(i).getOrderCode()) && StringUtils.isNotBlank(list.get(i).getLogisticseCode()) && 
					StringUtils.isNotBlank(list.get(i).getLogisticseName()) && StringUtils.isNotBlank(list.get(i).getWaybill())){
				
				list.get(i).setUid(UUID.randomUUID().toString().replace("-", ""));
				list.get(i).setCreator(sellerCode); 
				list.get(i).setCreateTime(DateHelper.formatDate(new Date())); 
				correctList.add(list.get(i)); 
			}else{
				exceptionOrderList.add(list.get(i)); 
			}
		}
		List<OrderShipment> insertList = new ArrayList<OrderShipment>(); // 保存在我们库中的订单的物流信息，排除非法订单
		List<OrderShipment> otherOrderList = new ArrayList<OrderShipment>();  // 非惠家有订单的物流信息，不做处理，返回给调用方
		// 效验订单，判断是否有不在我们库中的订单	 
		for(OrderShipment o : correctList){
			OcOrderinfo info = orderinfoDao.findOrderInfoByOrderCode(o.getOrderCode());
			if(null == info){
				otherOrderList.add(o);
			}else{
				insertList.add(o);
			}
		}
		
		int count = 0;
		OrderShipment ex = null;
		try{  // 插入物流数据
			for(OrderShipment s : insertList){
				ex = s;
				dao.insertSelective(new OcOrderShipments(s.getUid() , s.getOrderCode() , s.getLogisticseCode() , s.getLogisticseName() , s.getWaybill() , s.getCreator() , s.getCreateTime() , s.getRemark()));   
				
				count ++;
			}
			
		}catch(Exception e){
			// TODO 记录异常信息到数据库表 @@@@@@@@@@@@@@@@@@@@@@ 
			result.put("code", 11);
			result.put("desc", "平台内部错误，成功 " + count + " 条，失败 " + (insertList.size() - count) + " 条");
			return result; 
		}
		result.put("code", 0);
		result.put("desc", "请求成功，已同步 " + insertList.size() + " 条订单物流记录");
		if(exceptionOrderList.size() > 0){
			result.put("关键字段不全订单", exceptionOrderList);
		}
		if(otherOrderList.size() > 0){
			result.put("非惠家有订单", otherOrderList);
		}
		return result; 
	} 
	
	
	
}


































