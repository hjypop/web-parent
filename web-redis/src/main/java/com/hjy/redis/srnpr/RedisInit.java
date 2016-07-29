package com.hjy.redis.srnpr;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * @descriptions 
 * @alias RedisCall
 * 
 * @date 2016年7月29日下午4:50:48
 * @author Yangcl
 * @version 1.0.1
 */
public class RedisInit extends RedisSrnprCall {
	
	public RedisInit(String sUrl){
		if (StringUtils.contains(sUrl, ",")) {
			// Jedis Cluster will attempt to discover cluster nodes automatically
			Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
			for (String s : StringUtils.split(sUrl, ",")) {
				String[] sHost = StringUtils.split(s, ":");
				jedisClusterNodes.add(new HostAndPort(sHost[0], Integer.valueOf(sHost[1])));
			}

			JedisCluster jc = new JedisCluster(jedisClusterNodes);
			setCommonds(jc);
		}
	}
	
}






