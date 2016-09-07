package com.hjy.selleradapter.minspc;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.Inject;
import com.hjy.base.BaseClass;
import com.hjy.dao.ILcRsyncMinspcLogDao;
import com.hjy.entity.LcRsyncMinspcLog;
import com.hjy.helper.DateHelper;
import com.hjy.helper.SignHelper;
import com.hjy.model.MDataMap;
import com.hjy.support.WebClientSupport;

public abstract class RsyncMinspc extends BaseClass{
	
	@Inject
	private ILcRsyncMinspcLogDao logDao;   // 同步日志记录
	
	/**
	 * @description: minspc 项目中定时任务的核心方法。发送请求数据，获取响应数据并处理  
	 *								 所有的定时任务的【处理类】需继承此类。
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月6日 下午5:53:41 
	 * @version 1.0.0.1
	 */
	public JSONObject doRsync(){
		JSONObject result  = null;
		LcRsyncMinspcLog log = new LcRsyncMinspcLog();
		log.setUid(UUID.randomUUID().toString().replace("-", ""));
		log.setTarget(this.getRequestMethod());
		log.setRsyncUrl(this.getRequestUrl()); 
		log.setRequestTime(new Date());
		log.setRequestData(this.setRequestDataJson()); 
		String responseJson = this.getHttps();
		log.setResponseData(responseJson); 
		log.setResponseTime(new Date());
		logDao.insertSelective(log);
		
		result = this.doProcess(responseJson);
		
		// TODO logDao update 
		
		return result;
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
	
	/**
	 * @description: 拼装消息请求体  
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月6日 下午3:31:29 
	 * @version 1.0.0.1
	 */
	private MDataMap getSignMap(){
		MDataMap map = null;
		try { 
			map = new MDataMap();
			map.put("appid", getConfig("seller_adapter_minspc.rsync_minspc_appid"));
			map.put("data", URLEncoder.encode(this.setRequestDataJson(), "UTF-8"));
			map.put("method", this.getRequestMethod());
			map.put("timestamp", DateHelper.formatDate(new Date()));
			map.put("nonce", this.getNonce());
			map.put("format", "json");
			map.put("version", getConfig("seller_adapter_minspc.rsync_minspc_version"));
			
			List<String> list = new ArrayList<String>();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (entry.getValue() != "") {
					list.add(entry.getKey() + "=" + entry.getValue() + "&");
				}
			}
			Collections.sort(list); // 对List内容进行排序
			StringBuffer str = new StringBuffer();
			for (String nameString : list) {
				str.append(nameString);
			}
			String appSecret = getConfig("seller_adapter_minspc.rsync_minspc_secretkey");
			str.append(appSecret);
			String sign = SignHelper.md5Sign(str.toString());
			map.put("sign", sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	// 获取4位随机数 
	private String getNonce(){
		int a = (int) (Math.random()*9000+1000); 
		return String.valueOf(a);
	}
	/**
	 * 获取请求的url
	 */
	private String getRequestUrl() {
		return getConfig("seller_adapter_minspc.rsync_minspc_url");
	}

	
	
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
	public abstract String setRequestDataJson();
	
	/**
	 * @description: 由具体子类实现|处理响应数据报文的逻辑|在子类中进行增删改查之类的操作
	 * 
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月6日 下午5:47:10 
	 * @version 1.0.0.1
	 */
	public abstract JSONObject doProcess(String responseJson);
	
}





















