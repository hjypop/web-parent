package com.hjy.service;

import com.hjy.entity.system.SysLock;

public interface ILockService extends IBaseService<SysLock, Integer> {
	
	/**
	 * 
	 * @param keycode
	 * @param timeoutSecond
	 * @param uuid
	 * @return
	 */
	public String addLock(String keycode, Integer timeoutSecond, String uuid);
	
	public String unLock(String uuid);
	
}
