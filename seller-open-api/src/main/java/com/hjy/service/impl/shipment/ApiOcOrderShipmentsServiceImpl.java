package com.hjy.service.impl.shipment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.api.ILcOpenApiQueryLogDao;
import com.hjy.dao.log.ILcOpenApiShipmentStatusDao;
import com.hjy.dao.order.IOcOrderShipmentsDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.entity.log.LcOpenApiQueryLog;
import com.hjy.entity.log.LcOpenApiShipmentStatus;
import com.hjy.entity.order.OcOrderShipments;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.helper.DateHelper;
import com.hjy.helper.ExceptionHelpter;
import com.hjy.helper.WebHelper;
import com.hjy.request.data.OrderShipment;
import com.hjy.request.data.OrderShipmentsRequest;
import com.hjy.response.ApiShipmentsResponse;
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
	
	@Resource
	private ILcOpenApiShipmentStatusDao logShipmentStatusDao;
	
	@Resource
	private ILcOpenApiQueryLogDao openApiQueryDao;
	
	/**
	 * @descriptions 效验订单后插入物流信息
	 * 
	 * @param json 
	 * @date 2016年8月5日上午10:59:24
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject apiInsertShipments(String json , String sellerCode) {
		JSONObject result = new JSONObject();
		String responseTime = DateHelper.formatDate(new Date());
		result.put("responseTime", responseTime);
		
		List<OrderShipment> list = null;
		try {
			list = JSON.parseArray(json, OrderShipment.class); 	// 解析请求数据
		} catch (Exception e) {
			result.put("code", 1);
			result.put("desc", "请求参数错误，请求数据解析异常");
			return result; 
		}
 
		 
		if(list == null || list.size() == 0){
			result.put("code", 1);
			result.put("desc", "请求参数错误，数据不得为0条");
			return result; 
		}
		if(list.size() > COUNT){
			result.put("code", 1);
			result.put("desc", "请求参数错误，数据不得超过" + COUNT + "条");
			return result; 
		}
		
		String lockcode = WebHelper.getInstance().addLock(10000 , sellerCode + "@ApiOcOrderShipmentsServiceImpl.apiInsertShipments");      // 分布式锁
		if(StringUtils.isNotEmpty(lockcode)) { 
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
				OcOrderinfo info = orderinfoDao.getOrderInfoByCode(new OcOrderinfo(o.getOrderCode() , sellerCode)); 
				if(null == info){
					o.setUid("");
					o.setCreator("");
					o.setCreateTime(""); 
					otherOrderList.add(o);
				}else{
					insertList.add(o);
				}
			}
			if(exceptionOrderList.size() > 0){
				result.put("exceptionOrderList", exceptionOrderList); // 关键字段不全订单
			}
			if(otherOrderList.size() > 0){
				result.put("otherList", otherOrderList); // 非惠家有订单
			}
			
			
			List<OrderShipment> successList = new ArrayList<OrderShipment>(); // 保存同步成功的记录
			List<OrderShipment> updateList = new ArrayList<OrderShipment>(); // 保存insertList中在库中已经存在的对象
			OrderShipment ex = null;
			try{  // 插入物流数据
				List<OrderShipment> insertList_ = new ArrayList<OrderShipment>(); 
				for(OrderShipment s : insertList){
					OcOrderShipments info = dao.findWayBill(s);       // 如果存在多条记录，此处mybatis会报异常
					if(null != info){
						updateList.add(new OrderShipment(info.getUid() , info.getOrderCode() , s.getLogisticseCode() , s.getLogisticseName() , s.getWaybill() , info.getCreator() , info.getCreateTime() , s.getRemark()));
					}else{
						insertList_.add(s);
					}
				}
				insertList = insertList_;
				
				for(OrderShipment s : insertList){
					ex = s;
					dao.insertSelective(new OcOrderShipments(s.getUid() , s.getOrderCode() , s.getLogisticseCode() , s.getLogisticseName() , s.getWaybill() , s.getCreator() , s.getCreateTime() , s.getRemark()));   
					logShipmentStatusDao.insertSelective(new LcOpenApiShipmentStatus(ex.getUid() , sellerCode , ex.getOrderCode() , ex.getLogisticseName() , ex.getWaybill() , 1 , new Date() , "insert success"));
					successList.add(s);
				}
				for(OrderShipment s : updateList){
					ex = s;
					dao.updateSelectiveByUid(new OcOrderShipments(s.getUid() , s.getOrderCode() , s.getLogisticseCode() , s.getLogisticseName() , s.getWaybill() , s.getCreator() , s.getCreateTime() , s.getRemark()));   
					logShipmentStatusDao.insertSelective(new LcOpenApiShipmentStatus(ex.getUid() , sellerCode , ex.getOrderCode() , ex.getLogisticseName() , ex.getWaybill() , 1 , new Date() , "update success"));
					successList.add(s);
				}
				
			}catch(Exception e){
				// 记录异常信息到数据库表  shipmentUid sellerCode orderCode logisticseName wayBill  flag createTime remark
				String desc_ = "平台内部错误，成功 " + successList.size() + " 条，失败 " + (insertList.size() + updateList.size() - successList.size()) + " 条";
				logger.error("插入物流数据异常|" + desc_ , e); 
				String remark_ = "{" + ExceptionHelpter.allExceptionInformation(e) + "}";
				logShipmentStatusDao.insertSelective(new LcOpenApiShipmentStatus(ex.getUid() , sellerCode , ex.getOrderCode() , ex.getLogisticseName() , ex.getWaybill() , 2 , new Date() , remark_));
				
				result.put("successList", successList);
				result.put("code", 11);
				result.put("desc", desc_);
				return result; 
			}finally{
				WebHelper.getInstance().unLock(lockcode);
			}
			
			result.put("code", 0);
			result.put("desc", "请求成功，已同步 " + (insertList.size() + updateList.size()) + " 条订单物流记录");
			return result; 
		}else{			// 处理机房断电、服务器宕机
			result.put("code", 14);
			result.put("desc", "分布式锁生效，同步物流信息已锁定，请联系HJY删除锁" + sellerCode + "@ApiOcOrderShipmentsServiceImpl.apiInsertShipments");
			return result; 
		}
	}

	
	
	
	
	/** Order.ShipmentQuery
	 * @descriptions 查询物流信息
	 *  惠家有：商户；第三方：销售平台。
	 *  惠家有将物流信息发送给第三方，惠家有查询物流
	 *   
	 * @param json 
	 * @param sellerCode 
	 * @date 2016年8月26日下午4:47:44
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject apiSelectShipments(String json, String sellerCode) {
		JSONObject result = new JSONObject();
		String responseTime = DateHelper.formatDate(new Date());
		result.put("responseTime", responseTime);
		// 解析请求数据
		OrderShipmentsRequest request = null;
		try {
			request = JSON.parseObject(json, OrderShipmentsRequest.class);
		} catch (Exception e) {
			result.put("code", 1);
			result.put("desc", "请求参数错误，请求数据解析异常");
			return result; 
		}
		
		List<ApiShipmentsResponse> list = null;
		try {
			 list = dao.apiSelectShipments(request);
			 result.put("code", 0);
			 result.put("desc", "请求成功");
			 result.put("data", list);
			 
			openApiQueryDao.insertSelective(new LcOpenApiQueryLog(UUID.randomUUID().toString().replace("-", ""),
					sellerCode , 
					"Order.ShipmentQuery" , 
					"com.hjy.service.impl.shipment.ApiOcOrderShipmentsServiceImpl.apiSelectShipments" , 
					new Date(),
					1 , 
					json,
					result.toJSONString(),  
					"query success"));
				
		} catch (Exception ex) { 
			logger.error("查询物流信息异常|"  , ex);  
			String remark_ = "{" + ExceptionHelpter.allExceptionInformation(ex)+ "}";  // 记录异常信息到数据库表
			result.put("code", 11);
			result.put("desc", "查询物流信息异常");
			
			openApiQueryDao.insertSelective(new LcOpenApiQueryLog(UUID.randomUUID().toString().replace("-", ""),
					sellerCode , 
					"Order.ShipmentQuery" , 
					"com.hjy.service.impl.shipment.ApiOcOrderShipmentsServiceImpl.apiSelectShipments" , 
					new Date(),
					2 , 
					json,
					result.toJSONString(),  
					remark_));
		}
		
		
		
		return result;
	} 
	
	
	
}


































