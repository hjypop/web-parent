package com.hjy.service.impl.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.hjy.dao.api.IApiProductInfoDao;
import com.hjy.dao.api.ILcOpenApiOrderInsertDao;
import com.hjy.dao.api.ILcOpenApiQueryLogDao;
import com.hjy.dao.log.ILcOpenApiOrderStatusDao;
import com.hjy.dao.order.IOcOrderaddressDao;
import com.hjy.dao.order.IOcOrderdetailDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.dto.order.BillInfo;
import com.hjy.dto.order.OrderInfoInsert;
import com.hjy.entity.log.LcOpenApiOrderInsert;
import com.hjy.entity.log.LcOpenApiOrderStatus;
import com.hjy.entity.log.LcOpenApiQueryLog;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.helper.DateHelper;
import com.hjy.helper.ExceptionHelper;
import com.hjy.helper.SignHelper;
import com.hjy.helper.WebHelper;
import com.hjy.request.data.OrderInfoRequest;
import com.hjy.request.data.OrderInfoRequestDto;
import com.hjy.request.data.OrderInfoStatus;
import com.hjy.request.data.OrderInfoStatusDto;
import com.hjy.request.data.OrderProductInfo;
import com.hjy.response.OrderInfoResponse;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.order.IApiOcOrderInfoService;

@Service("apiOcOrderInfoService")
public class ApiOcOrderInfoServiceImpl extends BaseServiceImpl<OcOrderinfo, Integer> implements IApiOcOrderInfoService{
	
	private static Integer COUNT = 20;       // 一次性批处理的数据数量 

	@Resource
	private IOcOrderinfoDao dao;
	
	@Resource
	private IOcOrderdetailDao orderDetailDao;

	@Resource
	private ILcOpenApiOrderStatusDao openApiOrderStatusDao;
	
	@Resource
	private ILcOpenApiOrderInsertDao lcOpenApiOrderInsertDao; 
	
	@Resource
	private ILcOpenApiQueryLogDao openApiQueryDao;
	
	@Resource
	private IOcOrderaddressDao addressDao;
	
	@Resource
	private IApiProductInfoDao apiProductInfoDao; 
	
	/**
	 * @descriptions 根据Json串查询订单信息|依据商户编码、开始时间和结束时间来查询一批订单
	 *
	 *	签名方式为：sellerCode + JSON.toJSONString(list) + responseTime
	 * 接口标识：Order.List
	 *
	 * @param json
	 * @date 2016年8月3日上午10:43:17
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
	@Override
	public JSONObject getOrderInfoByJson(String json , String sellerCode) {
		JSONObject result = new JSONObject();
		String responseTime = DateHelper.formatDate(new Date());
		result.put("responseTime", responseTime);
		// 解析请求数据
		OrderInfoRequest request = null;
		try {
			request = JSON.parseObject(json, OrderInfoRequest.class);
		} catch (Exception e) {
			result.put("code", 1);
			result.put("desc", "请求参数错误，请求数据解析异常");
			return result; 
		}
		 
		try {
			String startTime = "";
			String endTime = "";
			if(StringUtils.isAnyBlank(request.getStartTime() , request.getEndTime())){
				startTime = this.getNextDate(new Date());  
				endTime = DateHelper.formatDateZero(new Date()); 
			}else{
				startTime = request.getStartTime();
				endTime = request.getEndTime();
				if(!this.compareDate(startTime, endTime)){
					result.put("code", 1);
					result.put("desc", "请求参数错误，开始时间不得大于结束时间");
					return result; 
				}
			}
			
			List<OrderInfoResponse> list = dao.getOpenApiOrderinfoList(new OrderInfoRequestDto(sellerCode, request.getOrderCode(), startTime, endTime));
			
			String sign = SignHelper.md5Sign(sellerCode + JSON.toJSONString(list) + responseTime);
			result.put("code", 0);
			result.put("desc", "请求成功");
			result.put("data", list);
			result.put("sign", sign); 
			openApiQueryDao.insertSelective(new LcOpenApiQueryLog(UUID.randomUUID().toString().replace("-", ""),
					sellerCode , 
					"Order.List" , 
					"com.hjy.service.impl.order.ApiOcOrderInfoServiceImpl.getOrderInfoByJson" , 
					new Date(),
					1 , 
					json,
					result.toJSONString(),  
					"query success"));
			return result; 
		} catch (Exception ex) {
			logger.error("查询订单状态信息异常|"  , ex);  
			String remark_ = "{" + ExceptionHelper.allExceptionInformation(ex)+ "}";  // 记录异常信息到数据库表
			result.put("code", 11);
			result.put("desc", "查询订单状态信息异常");
			openApiQueryDao.insertSelective(new LcOpenApiQueryLog(UUID.randomUUID().toString().replace("-", ""),
					sellerCode , 
					"Order.List" , 
					"com.hjy.service.impl.order.ApiOcOrderInfoServiceImpl.getOrderInfoByJson" , 
					new Date(),
					2 , 
					json,
					result.toJSONString(), 
					remark_));
			return result; 
		}
	} 
	
	
	/**
	 * @descriptions 订单变更： 更新订单状态信息
	 * 	商户已经发货或用户取消订单后，将订单对应的状态传递给惠家有，惠家有更新库
	 * 	中的订单
	 *  接口标识：Order.UpdateOrderStatus
	 * 
	 * @param info
	 * @date 2016年8月3日上午10:23:53
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject updateOrderStatus(String json , String sellerCode) {
		JSONObject result = new JSONObject();
		String responseTime = DateHelper.formatDate(new Date());
		result.put("responseTime", responseTime);
		// 解析请求数据
		List<OrderInfoStatus> list = null;
		try {
			list = JSON.parseArray(json, OrderInfoStatus.class);
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
		
		String lockcode = WebHelper.getInstance().addLock(10000 , sellerCode + "@ApiOcOrderInfoServiceImpl.updateOrderStatus");      // 分布式锁
		if(StringUtils.isNotEmpty(lockcode)) {
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
			if(exceptionStatusList.size() > 0){
				result.put("errorStatusList", exceptionStatusList); // 订单状态非法记录
			}
			List<OrderInfoStatus> successList = new ArrayList<OrderInfoStatus>(); // 保存同步成功的记录
			List<OrderInfoStatus> errorList = new ArrayList<OrderInfoStatus>(); // 保存 非此商户订单 的记录
			OrderInfoStatus e = null;
			try {
				for(OrderInfoStatus o : updateList){
					e = o;
					Integer count = dao.apiUpdateOrderinfoStatus(new OrderInfoStatusDto(o.getOrderCode() , o.getOrderStatus() , o.getUpdateTime() , sellerCode));
					// 插入一条同步日志记录      zid   sellerCode  orderCode   orderStatus createTime 
					if(count != null && count == 1){
						openApiOrderStatusDao.insertSelective(new LcOpenApiOrderStatus(sellerCode , o.getOrderCode() , o.getOrderStatus() , 1 , new Date() , "update success"));
						successList.add(o);
					}else if(count != null && count == 0){
						errorList.add(o);
					}
				}
			} catch (Exception ex) {
				String desc_ = "平台内部错误，成功 " + successList.size() + " 条，失败 " + (updateList.size() - successList.size()) + " 条";
				logger.error("更新订单状态信息异常|" + desc_ , ex);  
				String remark_ = "{" + ExceptionHelper.allExceptionInformation(ex)+ "}";  // 记录异常信息到数据库表
				openApiOrderStatusDao.insertSelective(new LcOpenApiOrderStatus(sellerCode , e.getOrderCode() , e.getOrderStatus() , 2 , new Date() , remark_));
				if(errorList.size() > 0){
					result.put("errorSellerCodeList", errorList); // 非此商户订单
				}
				result.put("code", 11);
				result.put("desc", desc_);
				return result; 
			}finally {
				WebHelper.getInstance().unLock(lockcode);
			}
			
			result.put("code", 0);
			result.put("desc", "请求成功，已同步 " + successList.size() + " 条订单状态记录"); 
			if(errorList.size() > 0){
				result.put("errorSellerCodeList", errorList);// 非此商户订单
			}
			return result;
		}else{             // 处理机房断电、服务器宕机
			result.put("code", 14);
			result.put("desc", "分布式锁生效，更新订单状态信息已锁定，请联系HJY删除锁" + sellerCode + "@ApiOcOrderInfoServiceImpl.updateOrderStatus");
			return result; 
		}
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

	/**
	 * @descriptions 获取第二天的时间
	*
	* @date 2016年8月9日 下午9:42:54
	* @author Yangcl 
	* @version 1.0.0.1
	 */
	private String getNextDate(Date date){
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.DATE , -1);//把日期往后增加一天.整数往后推,负数往前移动
		 date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		 
		 return formatter.format(date);
	}


	/** Order.Insert
	 * @descriptions 插入订单状态信息       
	 *  惠家有：商户；第三方：销售平台。
	 *  第三方将订单信息发送给惠家有，惠家有插入订单
	 * 
	 * @param info
	 * @date 2016年8月26日下午4:38:58
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject insertOrder(String json, String sellerCode) {
		JSONObject result = new JSONObject();
		// 解析请求数据
		List<OrderInfoInsert> list = null;
		try {
			list = JSON.parseArray(json, OrderInfoInsert.class);
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
		String lockcode = WebHelper.getInstance().addLock(10000 , sellerCode + "@ApiOcOrderInfoServiceImpl.insertOrder");      // 分布式锁
		if(StringUtils.isNotEmpty(lockcode)) {
			List<OrderInfoInsert> rightList = new ArrayList<OrderInfoInsert>();   // 保存正确的数据
			List<OrderInsertError> errorList = new ArrayList<OrderInsertError>();   // 保存错误的数据
			for(OrderInfoInsert i : list){
				JSONObject validate = this.orderInfoValidate(i);
				if(validate.getBoolean("flag")){
					i.setOrder_souce(i.getOrder_souce() + sellerCode);
					rightList.add(i);
				}else{
					errorList.add(new OrderInsertError(validate.getString("msg") , i)); 
				}
			} 		 
			
			String json_ = JSON.toJSONString(list);
			String remark_ = "";
			try {
				// TODO 调用接口|准备循环插入订单
				String api = this.getConfig("openapi.com_cmall_familyhas_api_APiCreateOrde");
				
				
				
				if(errorList.size() == 0){
					remark_ = "insert success";
				}else{
					remark_ = JSON.toJSONString(errorList);
				}
				lcOpenApiOrderInsertDao.insertSelective(new LcOpenApiOrderInsert(sellerCode , 1 , new Date() ,  json_ , remark_));
			} catch (Exception ex){
				String desc_ = "平台内部错误";
				logger.error(sellerCode + "批量插入订单信息异常|" + desc_ , ex);  
				remark_ = "{" + ExceptionHelper.allExceptionInformation(ex)+ "}";  // 记录异常信息到数据库表
				lcOpenApiOrderInsertDao.insertSelective(new LcOpenApiOrderInsert(sellerCode , 2 , new Date() ,  json_ , remark_));
			}finally {
				WebHelper.getInstance().unLock(lockcode);
			}
			
		}else{    // 处理机房断电、服务器宕机
			result.put("code", 14);
			result.put("desc", "分布式锁生效，插入订单状态信息已锁定，请联系HJY删除锁" + sellerCode + "@ApiOcOrderInfoServiceImpl.insertOrder");
			return result; 
		}
		
		result.put("responseTime", DateHelper.formatDate(new Date()));
		return result;
	}
	
	/**
	 * @description: 效验订单信息的合法性 
	 * 
	 * @param e
	 * @author Yangcl 
	 * @date 2016年12月7日 下午2:01:33 
	 * @version 1.0.0.1
	 */
	private JSONObject orderInfoValidate(OrderInfoInsert e){
		JSONObject result = new JSONObject();
		result.put("flag", true);
		if(StringUtils.isAnyBlank(
				e.getBuyer_name(),e.getBuyer_mobile() , e.getBuyer_address() , 
				e.getRemark() , e.getPay_type() , e.getApp_vision() , 
				e.getOrder_souce(), e.getOrder_type() )){ 
			result.put("flag", false);
			result.put("msg", "请求字段信息不全");
			return result;
		}
		if(!e.getPay_type().equals("449716200001") && !e.getPay_type().equals("449716200002")){
			result.put("flag", false);
			result.put("msg", "pay_type效验错误");
			return result;
		}
		if(e.getOrder_souce().equals("449715190001")){ 
			result.put("flag", false);
			result.put("msg", "order_souce效验错误");
			return result;
		}
		if(e.getOrder_type().equals("449715200005")){ 
			result.put("flag", false);
			result.put("msg", "order_type效验错误");
			return result;
		}
		if(e.getCheck_pay_money() <= 0){
			result.put("flag", false);
			result.put("msg", "应付款为非法数字");
			return result;
		}
		
		BillInfo b = e.getBillInfo();
		if(StringUtils.isAnyBlank(b.getBill_title() , b.getBill_detail() , b.getBill_Type())){
			result.put("flag", false);
			result.put("msg", "发票关键信息为空");
			return result;
		}
		if(!b.getBill_Type().equals("449746310001")){
			result.put("flag", false);
			result.put("msg", "错误的发票类型");
			return result;
		}
		
		
		List<OrderProductInfo> list = e.getGoods();
		if(list.size() == 0){
			result.put("flag", false);
			result.put("msg", "商品列表为空");
			return result;
		}
		
		for(OrderProductInfo i : list){
			if(StringUtils.isAnyBlank(i.getSku_code(),i.getProduct_code(),i.getChooseFlag(),i.getSku_code())){
				result.put("flag", false);
				result.put("msg", "商品列表关键字段非法");
				return result;
			}
			Map<String , String> map = new HashMap<String, String>();
			map.put("pcode", i.getProduct_code());
			map.put("scode", i.getSku_code());  
			int count = apiProductInfoDao.validateOrderProductInfo(map);
			if(count == 0){
				result.put("flag", false);
				result.put("msg", "该订单包含非惠家有平台商品信息");
				return result;
			}
		}
		
		return result;
	}
	
	
	/**
	 * @descriptions 比较两个时间的大小 如果两个时间相等则返回false
	 * 
	 * @tips 如果两个时间相等则a.compareTo(b) = 0
	 * 
	 * @param a not null
	 * @param b not null 
	 * @return boolean 
	 * 
	 * @refactor 
	 * @author Yangcl
	 * @date 2016-5-5-下午2:52:13
	 * @version 1.0.0.1
	 */
	private boolean compareDate(String a , String b){
		if(StringUtils.isAnyBlank(a , b)){
			return false;
		}
		return a.compareTo(b) < 0;
	}
}



/**
 * @description: 描述订单插入时候出现的效验失败的第三方订单信息
 * 	为防止暴类，这里采用内部类。 
 * 
 * @author Yangcl
 * @date 2016年12月7日 下午3:31:13 
 * @version 1.0.0
 */
class OrderInsertError{
	private String msg;
	private OrderInfoInsert entity;
	
	public OrderInsertError(String msg, OrderInfoInsert entity) {
		this.msg = msg;
		this.entity = entity;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public OrderInfoInsert getEntity() {
		return entity;
	}
	public void setEntity(OrderInfoInsert entity) {
		this.entity = entity;
	}
}
























