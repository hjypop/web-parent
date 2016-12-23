package com.hjy.util;

import com.hjy.system.OpenApiEcacheSupport;

/**
 * @description: 获取open-api在ecache中的缓存信息 
 * 
 * @author Yangcl
 * @date 2016年12月23日 上午11:48:44 
 * @version 1.0.0
 */
public class ApiVisitor {
	
	public static String getDictConfig(String key) {
		return OpenApiEcacheSupport.Instance.getValue(key);
	}
}
