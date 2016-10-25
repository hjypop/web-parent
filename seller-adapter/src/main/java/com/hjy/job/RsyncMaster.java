package com.hjy.job;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.hjy.annotation.Inject;
import com.hjy.base.BaseClass;
import com.hjy.common.DateUtil;
import com.hjy.helper.ExceptionHelper;
import com.hjy.helper.FormatHelper;
import com.hjy.helper.SignHelper;
import com.hjy.helper.WebHelper;
import com.hjy.model.MDataMap;
import com.hjy.model.RsyncResult;
import com.hjy.support.WebClientSupport;

public abstract class RsyncMaster extends BaseClass{
	
//	@Inject
//	private ILcRsyncKjtLogDao logDao;
	
	/**
	 * @description: 跨境通 项目中定时任务的核心方法。发送请求数据，获取响应数据并处理  
	 *								 所有的定时任务的【处理类】需继承此类。
	 *								 
	 *								 所有自2016年10月1日以后，因跨境通业务调整而添加的新的定时任务
	 * 							 都不在使用原来的RsyncKjt.java 作为同步类。改为使用这个类。- Yangcl 
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月6日 下午5:53:41 
	 * @version 1.0.0.1
	 */
	public void doRsync(){
//		String scode = WebHelper.getInstance().genUniqueCode("KCRL");
//		LcRsyncKjtLog log = new LcRsyncKjtLog();
//		try {
//			log.setUid(UUID.randomUUID().toString().replace("-", ""));
//			log.setCode(scode);
//			log.setRsyncTarget(this.getRequestMethod());
//			log.setRsyncUrl(this.getRequestUrl());
//			log.setRequestData(this.getRequestDataJson());
//			log.setRequestTime(FormatHelper.upDateTime());
//			
//			String responseJson = this.getHttps();
//			log.setResponseTime(FormatHelper.upDateTime());
//			log.setResponseData(responseJson);
//			
//			RsyncResult r = this.doProcess(responseJson);
//			
//			log.setProcessTime(FormatHelper.upDateTime());
//			log.setProcessData(r.getProcessData());
//			log.setStatusData("success");
//			log.setFlagSuccess(1);
//			log.setProcessNum(r.getProcessNum());
//			log.setSuccessNum(r.getSuccessNum()); 
//		} catch (Exception e) {
//			log.setCode(scode);
//			log.setFlagSuccess(0);
//			log.setStatusData("error");
//			log.setProcessTime(FormatHelper.upDateTime());
//			log.setErrorExpection(ExceptionHelper.allExceptionInformation(e)); 
//		}
//		
//		logDao.insertSelective(log);
	}
	
	/**
	 * @description: 获取响应数据  
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月6日 下午3:32:46 
	 * @version 1.0.0.1
	 */
	private String getHttps()  {
		MDataMap request = getSignMap();
		String url = this.getRequestUrl();
		String response = null;
		try {
			response = WebClientSupport.upPost(url, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	// 获取4位随机数 
	private String getNonce(){
		int a = (int) (Math.random()*9000+1000); 
		return String.valueOf(a);
	}
	
	/**
	 * @description: 拼装消息请求体  
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月6日 下午3:31:29 
	 * @version 1.0.0.1
	 */
	public abstract MDataMap getSignMap();  
	
	
	/**
	 * 获取请求的url
	 */
	public abstract String getRequestUrl();

	public abstract String getSellerCode();
	
	
	/**
	 * @description: 由具体子类实现|返回所请求的Api的方法标识 method
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月6日 下午2:59:46 
	 * @version 1.0.0.1
	 */
	public abstract String getRequestMethod(); 
	
	/**
	 * @description: 由具体子类实现|设置请求对象Json串   
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月6日 下午4:20:04 
	 * @version 1.0.0.1
	 */
	public abstract String getRequestDataJson();
	
	/**
	 * @description: 由具体子类实现|处理响应数据报文的逻辑|在子类中进行增删改查之类的操作
	 * 
	 * @return 返回子类处理结果，协助记录日志
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月6日 下午5:47:10 
	 * @version 1.0.0.1
	 */
	public abstract RsyncResult doProcess(String responseJson);
	
}





















