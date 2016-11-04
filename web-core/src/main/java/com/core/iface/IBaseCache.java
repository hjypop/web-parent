package com.core.iface;

/**
 * 缓存操作接口
 * @author HJY
 *
 */
public interface IBaseCache {

	
	/**
	 * 刷新缓存 该方法一向定义为synchronized
	 */
	public void refresh();
	
	
	
	/**
	 * 删除所有缓存
	 */
	public void removeAll();
	
	
}
