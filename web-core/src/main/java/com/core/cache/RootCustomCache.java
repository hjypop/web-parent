package com.core.cache;

import java.util.List;

import com.core.base.BaseClass;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

public class RootCustomCache<K, V> extends BaseClass {

	private Cache cache;

	/**
	 * 构造函数
	 */
	public RootCustomCache() {
		CacheDefine cDefine = new CacheDefine();
		String sCacheName = this.getClass().getName();
		CacheConfiguration cacheConfiguration = new CacheConfiguration();

		cacheConfiguration.setName(sCacheName);

		// 设置最大数量
		cacheConfiguration.setMaxEntriesLocalHeap(9999999);
		// 设置最长存活时间
		cacheConfiguration.setTimeToIdleSeconds(6000);

		cacheConfiguration.setMemoryStoreEvictionPolicy("FIFO");

		cache = cDefine.inCustomCache(sCacheName, cacheConfiguration);
	}

	public void inElement(K k, V v) {

		cache.put(new Element(k, v));
	}

	
	public void inElement(Element element)
	{
		cache.put(element);
	}
	
	//alias upKeys
	@SuppressWarnings("unchecked")
	public List<K> getKeys() {
		return cache.getKeys();
	}

	/**
	 * alias upValueAndRemove
	 * @param k
	 * @return
	 */
	public V getValueAndRemove(K k) {

		Object oReturnObject = null;
		if (cache.isKeyInCache(k)) {
			Element eCachElement = cache.get(k);
			if (eCachElement != null) {
				oReturnObject = eCachElement.getObjectValue();
			}

		}

		cache.remove(k);
		return (V) oReturnObject;

	}
	
	
	
	

	/**
	 * alias upValue
	 * 获取指定key对应的值
	 * 
	 * @param k
	 * @return
	 */
	public V getValue(K k) {
		Object oReturnObject = null;
		if (cache.isKeyInCache(k)) {
			Element eCachElement = cache.get(k);
			if (eCachElement != null) {
				oReturnObject = eCachElement.getObjectValue();
			}

		}

		return (V) oReturnObject;
	}

}
