package com.hjy.redis.core;

import com.hjy.redis.srnpr.ERedisSchema;
import com.hjy.redis.srnpr.RedisFactory;

/**
 * @descriptions 
 * @alias XmasKv
 * 
 * @date 2016年8月1日上午10:53:05
 * @author Yangcl
 * @version 1.0.1
 */
public class RedisLaunch {
	
	public static RedisFactory setFactory(ERedisSchema enum_) {
		return new RedisFactory("xs-" + enum_.toString() + "-");
	}
	
}
