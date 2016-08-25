package com.hjy.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.IJobExectimerDao;
import com.hjy.helper.DateHelper;
import com.hjy.pojo.entity.system.JobExectimer;
import com.hjy.selleradapter.job.JobGetChangeProductFromKJT;
import com.hjy.service.IKjtOperationsManagerService;

@Service("kjtOperationsManagerService")
public class KjtOperationsManagerServiceImpl implements IKjtOperationsManagerService {

	@Resource
	private IJobExectimerDao jobExectimerDao;
	
	public JSONObject funcOne(List<String> list) {
		JSONObject result = new JSONObject();
		new JobGetChangeProductFromKJT(list).doExecute(null); 
		result.put("status", "success");
		result.put("desc", "请求执行完成");
		return result;
	}

	public JSONObject funcTwo(String s, String e) {
		JSONObject result = new JSONObject();
		new JobGetChangeProductFromKJT(s , e).doExecute(null); 
		result.put("status", "success");
		result.put("desc", "请求执行完成");
		return result; 
	}

	public JSONObject funcThree(String execTime, String remark) {
		JSONObject result = new JSONObject();
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
	
}
















