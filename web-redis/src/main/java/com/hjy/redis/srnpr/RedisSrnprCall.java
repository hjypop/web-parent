package com.hjy.redis.srnpr;

import com.hjy.model.MDataMap;
import com.hjy.redis.srnpr.iface.IRedisSrnprCall;
import redis.clients.jedis.JedisCommands;

/**
 * @descriptions  kv操作的二次封装类 提供了默认所有方法的部分实现
 * @alias KvCall 复制自此接口
 * 
 * @date 2016年7月29日下午4:47:28
 * @author Yangcl
 * @version 1.0.1
 */
public class RedisSrnprCall implements IRedisSrnprCall {
	
	private JedisCommands commands = null;

	public JedisCommands getCommonds() {
		return commands;
	}

	public void setCommonds(JedisCommands jedisCommands) {
		commands = jedisCommands;
	}

	
	
	public String hget(final String key, final String field) {
		return getCommonds().hget(key, field);
	}

	public Long hdel(final String key, final String field) {
		return getCommonds().hdel(key, field);
	}

	public Long hset(String key, String field, String value) {
		return getCommonds().hset(key, field, value);
	}

	public String set(String key, String value) {
		return getCommonds().set(key, value);
	}

	public String get(String key) {
		return getCommonds().get(key);
	}

	public Long incrBy(String key, long integer) {
		return getCommonds().incrBy(key, integer);
	}

	public String setex(String key, int seconds, String value) {
		return getCommonds().setex(key, seconds, value);
	}

	public Long setnx(String key, String value) {
		return getCommonds().setnx(key, value);
	}

	public Long del(String key) {
		return getCommonds().del(key);
	}

	public Boolean exists(String key) {
		return getCommonds().exists(key);
	}

	public MDataMap hgetAll(String key) {
		MDataMap mDataMap = new MDataMap();
		mDataMap.putAll(getCommonds().hgetAll(key));
		return mDataMap;
	}

	public Long hincrBy(String key, String field, long value) {
		return getCommonds().hincrBy(key, field, value);
	}

	public Long expire(String key, int seconds) {
		return getCommonds().expire(key, seconds);
	}

	public Long ttl(String key) {
		return getCommonds().ttl(key);
	}
}
