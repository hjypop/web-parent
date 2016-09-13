package com.hjy.selleradapter.minspc;

import java.util.Date;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.Inject;
import com.hjy.dao.IJobExectimerDao;
import com.hjy.dao.order.IOcKjSellerSeparateOrderDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.dto.request.subscribeOrder.SoRequest;
import com.hjy.dto.response.subscribeOrder.SoResponse;
import com.hjy.helper.ExceptionHelper;
import com.hjy.pojo.entity.system.JobExectimer;


/**
 * @title: com.hjy.selleradapter.minspc.RsyncSubscribeOrder.java 
 * @description: 生成订阅订单（并发送海关）|完成接口请求等内容。
 *
 * @author Yangcl
 * @date 2016年9月6日 下午2:36:49 
 * @version 1.0.0
 */
public class RsyncSubscribeOrder extends RsyncMinspc{
	
	private static Logger logger = Logger.getLogger(RsyncSubscribeOrder.class);
	
	@Inject
	private IOcOrderinfoDao orderinfoDao;
	
	@Inject
	private IJobExectimerDao jobExectimerDao;
	
	@Inject 
	private IOcKjSellerSeparateOrderDao kjSellerSeparateOrderDao; // 拆单  

	private SoRequest soRequest;    
	

	public String doProcess(String responseJson) {
		Date currentTime = new Date();
		JobExectimer update = new JobExectimer();
		update.setBeginTime(currentTime); 
		
		SoResponse entity = null;
		try {
			entity = JSON.parseObject(responseJson, SoResponse.class); 
		} catch (Exception e) {
			String message = "响应消息体错误，响应数据解析异常，请联系民生品粹，异常信息如下：\n" + ExceptionHelper.allExceptionInformation(e); 
			logger.error(message);
			return message; 
		} 
		
		if(entity.getCode().equals("0")){
			update.setFlagSuccess(1);
			
			
			
			
		}else{
			update.setFlagSuccess(0);
		}
		update.setRemark(JSONObject.toJSONString(entity)); 
		
		// 更新job_exectimer表
		update.setEndTime(new Date());
		jobExectimerDao.updateSelectiveByOrderCode(update); 
		
		
		return "Rsync Subscribe Order Success "; 
	}

	/**
	 * @description: 
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月7日 下午2:58:26 
	 * @version 1.0.0.1
	 */
	public String getRequestMethod() {
		return "SubscribeOrder.Create";
	}

	
	/**
	 * @description: 拼装请求数据 
	 * 
	 * @author Yangcl
	 * @date 2016年9月7日 下午2:58:26 
	 * @version 1.0.0.1
	 */
	public String setRequestDataJson() {
		return JSONObject.toJSONString(this.getSoRequest()); 
	}

	
	
	public SoRequest getSoRequest() {
		return soRequest;
	}
	public void setSoRequest(SoRequest soRequest) {
		this.soRequest = soRequest;
	}

	
	
	
}




























