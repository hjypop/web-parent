package com.hjy.system.init;

import com.hjy.base.BaseClass;
import com.hjy.iface.IBaseCache;

/**
 * 各种初始化调用类
 * @author HJY
 *
 */
public abstract class RootInit extends BaseClass {

	public RootInit() {
		getLogger().logInfo(0, "init class : " + this.getClass().getName());
	}

	/**
	 * 初始化缓存
	 * 
	 * @param baseCaches
	 */
	public void topInitCache(IBaseCache... baseCaches) {
		for (IBaseCache iCache : baseCaches) {

			iCache.refresh();
		}
	}

	public boolean init() {
		return onInit();
	}

	/**
	 * 当系统初始化时调用
	 * @return
	 */
	public abstract boolean onInit();

	public boolean destory() {
		return onDestory();
	}

	/**
	 * 当容器关闭时调用
	 * @return
	 */
	public abstract boolean onDestory();

}
