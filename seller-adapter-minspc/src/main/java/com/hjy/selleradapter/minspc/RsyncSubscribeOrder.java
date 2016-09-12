package com.hjy.selleradapter.minspc;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.Inject;
import com.hjy.dao.IJobExectimerDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.dto.request.subscribeOrder.SoRequest;
import com.hjy.dto.response.product.Product;
import com.hjy.dto.response.subscribeOrder.SoResponse;
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
	
	@Inject
	private IOcOrderinfoDao orderinfoDao;
	
	@Inject
	private IJobExectimerDao jobExectimerDao;

	private SoRequest soRequest;    
	
	/**
	 * @description: TODO  
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月7日 下午2:58:26 
	 * @version 1.0.0.1
	 */
	public void doProcess(String responseJson) {
		
		SoResponse entity = null;
		try {
			entity = JSON.parseObject(responseJson, SoResponse.class); 
		} catch (Exception e) {
			e.printStackTrace();    
			// TODO	 插入错误日志信息
			return;
		} 
		if(entity.getCode().equals("0")){
			
			// 更新job_exectimer表
			Date currentTime = new Date();
			JobExectimer update = new JobExectimer();
			update.setBeginTime(currentTime); // TODO 这个时间在这里是不对的
//			update.setEndTime(currentTime);
//			update.setFlagSuccess(Integer.parseInt(iResult.getCode() == 1 ? "1" : "0"));
//			update.setRemark(je.getRemark() + GsonHelper.toJson(iResult));
//			update.setExecNumber(je.getExecNumber() + 1);
			jobExectimerDao.updateSelectiveByExecCode(update); 
		}else{
			// TODO	 插入错误日志信息
		}
		
	}

	/**
	 * @description: TODO  
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




























