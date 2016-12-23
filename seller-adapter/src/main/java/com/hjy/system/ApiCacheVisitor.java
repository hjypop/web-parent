package com.hjy.system;

import com.hjy.entity.webcore.WcOpenApi;

/**
 * @description: 获取open-api在ecache中的缓存信息 | 增删改查
 * 
 * @author Yangcl
 * @date 2016年12月23日 上午11:48:44 
 * @version 1.0.0
 */
public class ApiCacheVisitor {
 
	public static String find(String key) {
		return OpenApiEcacheSupport.Instance.getValue(key);
	}
	public static boolean delete(String key){
		return OpenApiEcacheSupport.Instance.delete(key); 
	}
	

	public static boolean add(String key) {
		return OpenApiEcacheSupport.Instance.add(key);
	}
	public static boolean update(String key){
		return OpenApiEcacheSupport.Instance.update(key); 
	}
	
	
	public static boolean addapi(WcOpenApi entity) {
		return OpenApiEcacheSupport.Instance.addapi(entity);  
	}
	public static boolean updateapi(WcOpenApi entity){
		return OpenApiEcacheSupport.Instance.updateapi(entity); 
	}
}















