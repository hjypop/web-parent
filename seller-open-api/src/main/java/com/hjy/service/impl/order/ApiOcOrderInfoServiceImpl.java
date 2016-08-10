package com.hjy.service.impl.order;

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
import com.hjy.dao.api.ILcOpenApiQueryLogDao;
import com.hjy.dao.log.ILcOpenApiOrderStatusDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.entity.log.LcOpenApiOrderStatus;
import com.hjy.entity.log.LcOpenApiQueryLog;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.helper.DateHelper;
import com.hjy.helper.ExceptionHelpter;
import com.hjy.helper.SignHelper;
import com.hjy.helper.WebHelper;
import com.hjy.request.data.OrderInfoRequest;
import com.hjy.request.data.OrderInfoRequestDto;
import com.hjy.request.data.OrderInfoStatus;
import com.hjy.request.data.OrderInfoStatusDto;
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
	public JSONObject getOrderInfoByJson(String json) {
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
		
		
		if(StringUtils.isBlank(request.getSellerCode())){
			result.put("code", 14);
			result.put("desc", "请求参数seller code不得为空");
			return result;
		}
		String sellerCode = request.getSellerCode();
		result.put("sellerCode", sellerCode);
		// TODO 关联查询商家code是否存在
//		if(count == 0){
//			result.put("code", 3);
//			result.put("desc", "错误的商家编号，无API访问权限");
//			return result;
//		}
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
					"com.hjy.service.impl.order.getOrderInfoByJson" , 
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
					"com.hjy.service.impl.order.getOrderInfoByJson" , 
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
	public JSONObject updateOrderStatus(String json) {
		JSONObject result = new JSONObject();
		String responseTime = DateHelper.formatDate(new Date());
		result.put("responseTime", responseTime);
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
		result.put("sellerCode", sellerCode);
		
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
				result.put("successList", successList);
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
			result.put("successList", successList);
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
}



























