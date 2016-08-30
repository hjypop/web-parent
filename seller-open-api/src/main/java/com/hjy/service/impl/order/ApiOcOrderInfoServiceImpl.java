package com.hjy.service.impl.order;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.ExculdeNullField;
import com.hjy.dao.api.ILcOpenApiOrderInsertDao;
import com.hjy.dao.api.ILcOpenApiQueryLogDao;
import com.hjy.dao.log.ILcOpenApiOrderStatusDao;
import com.hjy.dao.order.IOcOrderdetailDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.entity.log.LcOpenApiOrderInsert;
import com.hjy.entity.log.LcOpenApiOrderStatus;
import com.hjy.entity.log.LcOpenApiQueryLog;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.helper.DateHelper;
import com.hjy.helper.ExceptionHelpter;
import com.hjy.helper.SignHelper;
import com.hjy.helper.WebHelper;
import com.hjy.model.order.OrderDetail;
import com.hjy.request.data.OrderDetailInsert;
import com.hjy.request.data.OrderInfoInsert;
import com.hjy.request.data.OrderInfoRequest;
import com.hjy.request.data.OrderInfoRequestDto;
import com.hjy.request.data.OrderInfoStatus;
import com.hjy.request.data.OrderInfoStatusDto;
import com.hjy.response.OrderInfoResponse;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.order.IApiOcOrderInfoService;

@Service("apiOcOrderInfoService")
public class ApiOcOrderInfoServiceImpl extends BaseServiceImpl<OcOrderinfo, Integer> implements IApiOcOrderInfoService{
	
	private static Integer COUNT = 5000;       // 一次性批处理的数据数量 

	@Resource
	private IOcOrderinfoDao dao;
	
	@Resource
	private IOcOrderdetailDao orderDetailDao;

	@Resource
	private ILcOpenApiOrderStatusDao openApiOrderStatusDao;
	
	@Resource
	private ILcOpenApiOrderInsertDao openApiOrderInsertDao; 
	
	@Resource
	private ILcOpenApiQueryLogDao openApiQueryDao;
	
	/**
	 * @descriptions 根据Json串查询订单信息|seller-open-api项目中使用
	 *
	 *	签名方式为：sellerCode + JSON.toJSONString(list) + responseTime
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
			String startTime = DateHelper.formatDateZero(new Date());  
			String endTime = this.getNextDate(new Date()); 
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
			String remark_ = "{" + ExceptionHelpter.allExceptionInformation(ex)+ "}";  // 记录异常信息到数据库表
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
	 * 	包含效验对方传入错误的订单
	 *  
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
				String remark_ = "{" + ExceptionHelpter.allExceptionInformation(ex)+ "}";  // 记录异常信息到数据库表
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
		 calendar.add(calendar.DATE , 1);//把日期往后增加一天.整数往后推,负数往前移动
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
		String responseTime = DateHelper.formatDate(new Date());
		result.put("responseTime", responseTime);
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
			List<OcOrderinfo> insertList = new ArrayList<OcOrderinfo>(); // 存放合法的数据记录
			List<OrderDetail> insertOrderDetailList = new ArrayList<OrderDetail>(); // 存放合法的数据记录
			List<OrderInfoInsert> errorList = new ArrayList<OrderInfoInsert>(); // 存放数据不完整的记录
			try {
				for(OrderInfoInsert i : list){
					OcOrderinfo e = new OcOrderinfo();
					if(this.validate(i, e)){
						List<OrderDetailInsert> dList = i.getList();
						if(dList != null && dList.size() !=0){
							List<OrderDetail> odList = new ArrayList<OrderDetail>(); // 临时存储
							for(OrderDetailInsert d : dList){
								OrderDetail od = new OrderDetail();
								if(this.validate(d, od)){
									od.setUid(UUID.randomUUID().toString().replace("-", "")); 
									od.setOrderCode(i.getOrderCode()); 
									od.setGiftFlag("1"); 
									odList.add(od);
								}else{ // 如果 list 中的 sku 信息不合法则认为这条数据错误
									errorList.add(i);
									break; // 直接跳出循环
								}
							}
							if(dList.size() == odList.size()){
								insertOrderDetailList.addAll(odList);
								e.setUid(UUID.randomUUID().toString().replace("-", "")); 
								e.setOrderSource("449715190009");
								e.setOrderType("449715200005"); 
								e.setOrderStatus("4497153900010002"); 
								e.setSellerCode("SI2003"); 
								e.setCreateTime(DateHelper.formatDate(new Date())); 
								e.setOrderChannel("449747430006"); 
								e.setOrderStatusExt("4497153900140002");
								e.setSmallSellerCode(sellerCode); 
								e.setOrderAuditStatus("449746680003"); 
								e.setLowOrder("449747110001"); 
								insertList.add(e);
							}
							
						}else{     // 如果sku list 信息为空，则认为这条数据错误
							errorList.add(i);
						}
					}else{
						errorList.add(i);
					}
				} 		// 数据清洗分离完成 
			} catch (Exception e) {
				String desc_ = "平台内部错误";
				logger.error(sellerCode + "合法性验证异常|" + desc_ , e);  
				String remark_ = "{" + ExceptionHelpter.allExceptionInformation(e)+ "}";  // 记录异常信息到数据库表
				openApiOrderInsertDao.insertSelective(new LcOpenApiOrderInsert(sellerCode , 2 , new Date() ,  "合法性验证异常" , remark_));
			}finally {
				WebHelper.getInstance().unLock(lockcode);
			}
			
			if(errorList.size() != 0){
				list.removeAll(errorList);
				result.put("errorList", errorList); 
			}
			String json_ = JSON.toJSONString(list);
			// 准备批量插入数据到oc_orderinfo表 和 oc_orderdetail表
			String remark_ = "";
			try {
				if(insertList.size() != 0){
					dao.apiBatchInsert(insertList);
					orderDetailDao.apiBatchInsert(insertOrderDetailList);
					remark_ = "batch insert success";
				}else{
					remark_ = "insert list = 0";
				}
				openApiOrderInsertDao.insertSelective(new LcOpenApiOrderInsert(sellerCode , 1 , new Date() ,  json_ , remark_));
			} catch (Exception ex){
				String desc_ = "平台内部错误";
				logger.error(sellerCode + "批量插入订单信息异常|" + desc_ , ex);  
				remark_ = "{" + ExceptionHelpter.allExceptionInformation(ex)+ "}";  // 记录异常信息到数据库表
				openApiOrderInsertDao.insertSelective(new LcOpenApiOrderInsert(sellerCode , 2 , new Date() ,  json_ , remark_));
			}finally {
				WebHelper.getInstance().unLock(lockcode);
			}
			
		}else{             // 处理机房断电、服务器宕机
			result.put("code", 14);
			result.put("desc", "分布式锁生效，插入订单状态信息已锁定，请联系HJY删除锁" + sellerCode + "@ApiOcOrderInfoServiceImpl.insertOrder");
			return result; 
		}
		
		return result;
	}
	
	
	/**
	 * @descriptions  验证对象中的值是否合法并赋值
	 * 
	 * @param t 要验证的对象
	 * @param entity 要赋值的对象
	 * @return entity
	 * @date 2016年8月29日下午12:10:38
	 * @author Yangcl 
	 * @version 1.0.0.1
	 * @param <E>
	 */
	private  <T , E> boolean validate(T t , E e){
		Field[] fields = t.getClass().getDeclaredFields();
		 try {
			 for(int i = 0 ; i < fields.length ; i ++){
				 Field field = fields[i];
				 String name = field.getName();
				 String func = "get" + name.substring(0,1).toUpperCase()+name.substring(1);
				 Method m = t.getClass().getMethod(func);
	             String value = String.valueOf(m.invoke(t)); 
	             if(value.equals("null")) {
	            	 value = null;
	             }
	             if( !field.isAnnotationPresent(ExculdeNullField.class) && StringUtils.isBlank(value) ){    
	            	 // 不包含此标签则是必传字段，如果没传值则认为此条记录错误
	            	 return false; 
	             }else if(field.isAnnotationPresent(ExculdeNullField.class) && StringUtils.isBlank(value) ){
	            	 // ExculdeNullField注解标识的字段为空，则不再对其反射设值。
	            	 continue;
	             }
	             
	             // 赋值 
	             String func_ = "set" + name.substring(0,1).toUpperCase()+name.substring(1); 
				 Method m_ = e.getClass().getMethod(func_  , m.invoke(t).getClass());
				 @SuppressWarnings("rawtypes")
				Class[] c = m_.getParameterTypes();
				 if(c[0] == String.class) {
		 	 		 m_.invoke(e , value);
		 	 	 }else if(c[0] == BigDecimal.class) {
		 	 		 m_.invoke(e , BigDecimal.valueOf(Double.valueOf(value)));
		 	 	 }else if(c[0] == Integer.class) {
					 m_.invoke(e ,Integer.valueOf(value));
			 	 }else if(c[0] == Boolean.class) {
		 	 		 m_.invoke(e , Boolean.valueOf(value));
		 	 	 }else if(c[0] == Float.class){
		 		 	 m_.invoke(e , Float.valueOf(value));
		 	 	 }else if(c[0] == Double.class) {
	 	 		 	 m_.invoke(e , Double.valueOf(value));
		 	 	 }else if(c[0] == Byte.class) {
		 	 		 m_.invoke(e , Byte.valueOf(value));
		 	 	 }
			 }
		 } catch (NoSuchMethodException ex) {
//			 ex.printStackTrace();  不做处理即可
		 } catch (SecurityException ex) {
			 ex.printStackTrace();
		 } catch (IllegalAccessException ex) {
			 ex.printStackTrace();
		 } catch (IllegalArgumentException ex) {
			 ex.printStackTrace();
		 } catch (InvocationTargetException ex) {
			 ex.printStackTrace();
		 } 
		 
		return true;
	}
	
}



























