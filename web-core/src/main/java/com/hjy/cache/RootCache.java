package com.hjy.cache;

import java.util.List;

import com.hjy.base.BaseClass;
import com.hjy.iface.IBaseCache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * 缓存基类
 * 
 * @author HJY
 * 
 * @param <K>
 * @param <V>
 */
public abstract class RootCache<K, V> extends BaseClass implements IBaseCache {

	private Cache cache;

	/**
	 * 构造函数
	 */
	public RootCache() {
		CacheDefine cDefine = new CacheDefine();
		String sClassNameString = this.getClass().getName();
		cache = cDefine.inCache(sClassNameString);
	}

	/**
	 * 添加缓存
	 * 
	 * @param k
	 * @param v
	 */
	public void inElement(K k, V v) {

		cache.put(new Element(k, v));
	}

	/**
	 * 判断是否存在
	 * 
	 * @param k
	 * @return
	 */
	public boolean containsKey(K k) {
		// return cache.getKeys().contains(k);
		return cache.isKeyInCache(k);
	}

	/**
	 * alias upKeys
	 * 获取所有缓存的Key
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<K> getKeys() {
		return cache.getKeys();
	}

	/**
	 * alias upOne
	 * 获取一个 默认返回null <br>
	 * 客户端如果继承该类需要自行封装业务逻辑 该方法通用用于客户端覆盖 延迟加载判断<br>
	 * 通常重写该方法以支撑返回缓存信息的情况
	 * 
	 * @param k
	 * @return
	 */
	public abstract V getOne(K k);

	/**
	 * alias upValue
	 * 获取缓存的值
	 * 
	 * @param k
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public V getValue(K k) {

		if (!containsKey(k)) {
			synchronized (this) {
				bLogInfo(0, "reload cache " + k.toString());
				refresh();
			}
		}

		if (containsKey(k)) {
			return (V) cache.get(k).getObjectValue();
		} else {
			V v = null;
			synchronized (this) {
				if (containsKey(k)) {
					v = (V) cache.get(k).getObjectValue();
				} else {
					v = getOne(k);
					bLogInfo(0, "upOne cache " + k.toString());
					if (v != null && !containsKey(k)) {
						inElement(k, v);
					}
					else if(v==null)
					{
						bLogWarn(0, "error load cache name "+k.toString());
					}
				}

			}

			return v;
		}
	}

	public void removeAll() {
		cache.removeAll();
	}
	
	public void removeByKey(K k) {
		synchronized (this) {
			cache.remove(k);
		}
	}

}
