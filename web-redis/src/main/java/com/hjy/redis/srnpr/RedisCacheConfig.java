package com.hjy.redis.srnpr;

import com.hjy.cache.RootCache;
import com.hjy.redis.srnpr.iface.IRedisCall;

/**
 * @descriptions KV操作的缓存类 目前默认只支持一个实例
 * @alias KvCache
 * 
 * @date 2016年7月29日下午5:41:40
 * @author Yangcl
 * @version 1.0.1
 */
public class RedisCacheConfig extends RootCache<String, IRedisCall> {

	@Override
	public void refresh() {
	}

	@Override
	public IRedisCall getOne(String k) {
		String redisUrls = getConfig("web-redis.kv_url_" + k);
		return new RedisCall(redisUrls);
	}

}
