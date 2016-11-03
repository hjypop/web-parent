package com.core.cache;

import java.net.URL;

import org.apache.commons.lang.StringUtils;

import com.core.base.BaseClass;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;

/**
 * 缓存定义类
 * 
 * @author HJY
 * 
 */
public class CacheDefine extends BaseClass {

	private static CacheManager cManager;

	/**
	 * Cache名称如果以该值开始 则标记该cache继承自rootcache
	 */
	private final static String CACHE_TYPE_NO_ETERNAL = "rootcache:";

	/**
	 * 自定义cache 该cache自由设置配置 清除缓存时不清除该类cache
	 */
	private final static String CACHE_TYPE_CUSTOM = "customcache:";

	/**
	 * 缓存定义
	 */
	public CacheDefine() {
		if (cManager == null) {
			URL url = this.getClass().getResource(
					"/META-INF/ehcache/ehcache.xml");
			cManager = CacheManager.create(url);
		}
	}

	/**
	 * 添加缓存
	 * 
	 * @param sCacheName
	 * @return
	 */
	public synchronized Cache inCache(String sCacheName) {

		sCacheName = CACHE_TYPE_NO_ETERNAL + sCacheName;

		if (cManager.cacheExists(sCacheName)) {
			return upCache(sCacheName);
		} else {
			CacheConfiguration cacheConfiguration = new CacheConfiguration();

			cacheConfiguration.setName(sCacheName);

			cacheConfiguration.setEternal(true);

			cacheConfiguration.setTimeToIdleSeconds(0);
			//cacheConfiguration.setTimeToIdleSeconds(0);
			cacheConfiguration.setMaxEntriesLocalHeap(99999999);

			Cache memoryOnlyCache = new Cache(cacheConfiguration);

			cManager.addCache(memoryOnlyCache);

			return memoryOnlyCache;
		}
	}

	/**
	 * 添加自定义的缓存
	 * 
	 * @param sCacheName
	 * @param cacheConfiguration
	 * @return
	 */
	public synchronized Cache inCustomCache(String sCacheName,
			CacheConfiguration cacheConfiguration) {

		sCacheName = CACHE_TYPE_CUSTOM + sCacheName;

		if (cManager.cacheExists(sCacheName)) {
			return upCache(sCacheName);
		} else {

			Cache memoryOnlyCache = new Cache(cacheConfiguration);

			cManager.addCache(memoryOnlyCache);

			return memoryOnlyCache;
		}
	}

	/**
	 * 获取缓存
	 * 
	 * @param sCacheName
	 * @return
	 */
	public Cache upCache(String sCacheName) {
		return cManager.getCache(sCacheName);
	}

	/**
	 * 清除所有继承自rootcache的缓存
	 */
	public synchronized void removeAllCache() {

		for (String sKey : cManager.getCacheNames()) {

			if (StringUtils.startsWith(sKey, CACHE_TYPE_NO_ETERNAL)) {
				Cache cache = upCache(sKey);

				getLogger().logInfo(0, "remove cache:" + cache.getName());
				cache.removeAll();
			}

		}

	}

}
