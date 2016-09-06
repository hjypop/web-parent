package com.hjy.selleradapter.minspc;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hjy.base.BaseClass;
import com.hjy.helper.DateHelper;
import com.hjy.helper.SignHelper;

public abstract class RsyncMinspc extends BaseClass{
	
	/**
	 * @description: 拼装消息请求体  
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月6日 下午3:31:29 
	 * @version 1.0.0.1
	 */
	private Map<String, String> getSignMap(){
		Map<String, String> map = null;
		try { 
			map = new HashMap<String, String>();
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
	 * @description:   
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月6日 下午3:32:46 
	 * @version 1.0.0.1
	 */
	private String getHttps(String sUrl, String sRequestString) throws Exception {
		Map<String, String> request = getSignMap();
		String url = this.getRequestUrl();
		 
		
		String sResponseString = ""; // WebClientSupport.upPost(sUrl, mrequest);
		return sResponseString;
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
	
	
	
}





















