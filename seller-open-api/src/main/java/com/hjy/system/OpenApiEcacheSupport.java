package com.hjy.system;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.Inject;
import com.hjy.cache.RootCache;
import com.hjy.dao.webcore.IWcOpenApiDao;
import com.hjy.dao.webcore.IWcSellerApiDao;
import com.hjy.dao.webcore.IWcSellerinfoDao;
import com.hjy.entity.webcore.WcOpenApi;
import com.hjy.entity.webcore.WcSellerApi;
import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.system.cmodel.CacheWcOpenapi;
import com.hjy.system.cmodel.CacheWcSellerInfo;

/**
 * @description: 对api信息的缓存化提供支持
 * 
 * @author Yangcl
 * @date 2016年12月22日 下午3:46:12 
 * @version 1.0.0
 */
public class OpenApiEcacheSupport  extends RootCache<String, String> {

	@Inject
	private IWcOpenApiDao wcOpenApiDao;
	@Inject
	private IWcSellerinfoDao wcSellerInfoDao;
	@Inject
	private IWcSellerApiDao wcSellerApiDao;
	
	
	/**
	 * @description: 首先加载 wc_openapi 表的信息到ecache
	 * 
	 * @author Yangcl 
	 * @date 2016年12月22日 下午5:08:38 
	 * @version 1.0.0.1
	 */
	public void refresh() {
		List<WcOpenApi> woaList = wcOpenApiDao.selectAllInfo(null);
		for(WcOpenApi i : woaList){
			String value = JSONObject.toJSONString(new CacheWcOpenapi(i.getMethod(), i.getApiName(), i.getApiCode(), i.getStatus(), i.getDescription(), i.getIsDeleted()));
			this.inElement(i.getApiCode(), value);
			this.inElement(i.getMethod(), value); // 分别以code 和 method为key进行存储    
		}
		
		List<WcSellerinfo> sinfoList = wcSellerInfoDao.queryPage(null);  
		List<WcSellerApi> apiList = wcSellerApiDao.queryPage(null);   
		for(WcSellerinfo s : sinfoList){
			CacheWcSellerInfo c = new CacheWcSellerInfo();
//			c.setSellerCode(sellerCode);
			for(WcSellerApi a : apiList){
				
			}
		}
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	

	public String getOne(String k) {
		
		return null;
	}

}
























































