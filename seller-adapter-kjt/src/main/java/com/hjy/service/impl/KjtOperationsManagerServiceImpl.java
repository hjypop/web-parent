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
import com.hjy.dao.product.IPcProductinfoDao;
import com.hjy.helper.DateHelper;
import com.hjy.pojo.entity.system.JobExectimer;
import com.hjy.selleradapter.job.JobGetChangeProductFromKJT;
import com.hjy.service.IKjtOperationsManagerService;

@Service("kjtOperationsManagerService")
public class KjtOperationsManagerServiceImpl implements IKjtOperationsManagerService {

	@Resource
	private IJobExectimerDao jobExectimerDao;
	
	@Resource
	private IPcProductinfoDao pcProductinfoDao;
	
	
	
	
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
	
}
















