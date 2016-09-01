package com.hjy.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.IJobExectimerDao;
import com.hjy.dao.ILcRsyncKjtLogDao;
import com.hjy.dao.order.IOcOrderdetailDao;
import com.hjy.dao.product.IPcProductinfoDao;
import com.hjy.dto.KjtProductInfo;
import com.hjy.dto.QueryKjtLog;
import com.hjy.entity.LcRsyncKjtLog;
import com.hjy.helper.DateHelper;
import com.hjy.helper.ExceptionHelpter;
import com.hjy.pojo.entity.system.JobExectimer;
import com.hjy.redis.core.RedisLaunch;
import com.hjy.redis.srnpr.ERedisSchema;
import com.hjy.selleradapter.job.JobForInventory;
import com.hjy.selleradapter.job.JobGetChangeProductFromKJT;
import com.hjy.service.IKjtOperationsManagerService;

@Service("kjtOperationsManagerService")
public class KjtOperationsManagerServiceImpl implements IKjtOperationsManagerService {

	@Resource
	private IJobExectimerDao jobExectimerDao;
	
	@Resource
	private IPcProductinfoDao pcProductinfoDao;
	
	@Resource
	private ILcRsyncKjtLogDao lcRsyncKjtLogDao;  
	
	@Resource
	private IOcOrderdetailDao orderDetailDao;
	
	
	
	public JSONObject funcOne(String json, HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("kjt-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		try {
			List<String> list = JSON.parseArray(json, String.class);
			new JobGetChangeProductFromKJT(list).doExecute(null); 
			result.put("status", "success");
			result.put("desc", "请求执行完成");
		} catch (Exception e) {
			result.put("status", "success");
			result.put("desc", "非法的Json数据");
		}
		return result;
	}

	public JSONObject funcTwo(String s, String e, HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("kjt-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		
		new JobGetChangeProductFromKJT(s , e).doExecute(null); 
		result.put("status", "success");
		result.put("desc", "请求执行完成");
		return result; 
	}

	public JSONObject funcThree(String execTime, String remark, HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("kjt-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		
		JobExectimer e = new JobExectimer();
		if(StringUtils.isNotBlank(execTime)){
			e.setExecTime(DateHelper.parseDate(execTime));
		}else{
			e.setExecTime(new Date()); 
		}
		e.setRemark(remark); 
		e.setExecNumber(0);
		
		jobExectimerDao.updateSelectiveByFlag(e);
		
		result.put("status", "success");
		result.put("desc", "请求执行完成");
		
		return result;
	}

	
	public JSONObject funcFour(String json, HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("kjt-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		try {
			List<String> list = JSON.parseArray(json, String.class);
			pcProductinfoDao.updateNullByProductCode(list);
			result.put("status", "success");
			result.put("desc", "请求执行完成");
		} catch (Exception e) {
			result.put("status", "success");
			result.put("desc", "非法的Json数据");
		}
		return result;
	}

	
	public JSONObject funcFive(String json, HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("kjt-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		
		try {
			List<String> list = JSON.parseArray(json, String.class);
			new JobForInventory(list).doExecute(null); 
			result.put("status", "success");
			result.put("desc", "请求执行完成");
		} catch (Exception e) {
			result.put("status", "success");
			result.put("desc", "非法的Json数据");
		}
		return result;
	}

	
	public JSONObject funcSix(String json, HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("kjt-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		
		try {
			List<String> list = JSON.parseArray(json, String.class);
			this.redisReloadProductInfo(list);
			
			result.put("status", "success");
			result.put("desc", "请求执行完成");
		} catch (Exception e) {
			result.put("status", "success");
			result.put("desc", "非法的Json数据");
		}
		return result;
	}
	
	
	// 刷新Sku信息 
	private boolean redisReloadProductInfo(List<String> list ){ 
		for(String i : list){ 
			RedisLaunch.setFactory(ERedisSchema.Product).del(i);
			RedisLaunch.setFactory(ERedisSchema.ProductSku).del(i);
			RedisLaunch.setFactory(ERedisSchema.ProductSales).del(i);		//刷新销量缓存
		}
		return true;
	}

	
	public JSONObject queryKjtlog(QueryKjtLog dto) {
		JSONObject result = new JSONObject();
		LcRsyncKjtLog kjt = new LcRsyncKjtLog();
		kjt.setRsyncTarget(dto.getRsyncTarget());
		kjt.setRequestTime(dto.getRequestTime());
		kjt.setResponseData(dto.getResponseData());
		kjt.setRequestData(dto.getRequestData()); 
		try {
			List<LcRsyncKjtLog> logList = lcRsyncKjtLogDao.selectLogByType(kjt); 
			if(logList != null && logList.size() != 0){
				result.put("logList", logList);
			}else{
				result.put("logList", "log-list-null");
			}
			
			KjtProductInfo entity = orderDetailDao.findKjtProductInfo(dto.getOrderCode()); 
			if(entity != null){
				result.put("entity", entity);
			}else{
				result.put("entity", "entity-null");
			}
			result.put("status", "success");
		} catch (Exception ex) {
			result.put("status", "error");
			String remark_ = "{" + ExceptionHelpter.allExceptionInformation(ex)+ "}";
			result.put("msg", remark_); 
		}
		
		return result;
	}
	
}
















