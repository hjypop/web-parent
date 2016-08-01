package com.hjy.redis.srnpr;

import com.hjy.base.BaseClass;
import com.hjy.redis.srnpr.iface.IRedisCall;

/**
 * @descriptions 
 * @alias KvUp
 * 
 * @date 2016年8月1日上午10:36:30
 * @author Yangcl
 * @version 1.0.1
 */
public class RedisInit extends BaseClass{
	private static RedisCacheConfig SET_CONFIG = new RedisCacheConfig();
	
	/**
	 * @descriptions 读取 config.web-redis.properties 中的Redis默认配置路径 kv_url_default
	 * @alias upDefault
	 * 
	 * @date 2016年8月1日上午10:29:53
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public static IRedisCall getDefault() {
		return SET_CONFIG.getValue("default");
	}
	
	/**
	 * @descriptions 读取 config.web-redis.properties 中的Redis其他自定义的配置路径 示例如右：kv_url_other
	 * 此方法项目中暂无应用，作为示例扩展。
	 * 如果您是第一个使用了此方法的人，请备注为已使用。
	 * 
	 * @date 2016年8月1日上午10:29:53
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public static IRedisCall getOtherConfig() {
		return SET_CONFIG.getValue("other");
	}
}















