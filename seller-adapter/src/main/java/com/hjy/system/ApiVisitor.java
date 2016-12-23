package com.hjy.system;

/**
 * @description: 获取open-api在ecache中的缓存信息 | 增删改查
 * 
 * @author Yangcl
 * @date 2016年12月23日 上午11:48:44 
 * @version 1.0.0
 */
public class ApiVisitor {
	
	/**
	 * @description: 
	 * 
	 * @param key
	 * @param value
	 * @author Yangcl 
	 * @date 2016年12月23日 下午2:24:32 
	 * @version 1.0.0.1
	 */
	public static String find(String key) {
		return OpenApiEcacheSupport.Instance.getValue(key);
	}
	public static boolean delete(String key){
		return OpenApiEcacheSupport.Instance.delete(key); 
	}
	

	public static boolean add(String key , String value) {
		return OpenApiEcacheSupport.Instance.add(key);
	}
	public static boolean update(String key){
		return OpenApiEcacheSupport.Instance.update(key); 
	}
	
	
	public static boolean addapi(String key , String value) {
		return OpenApiEcacheSupport.Instance.addapi(key);  
	}
	public static boolean updateapi(String key){
		return OpenApiEcacheSupport.Instance.updateapi(key); 
	}
}















