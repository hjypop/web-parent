package com.hjy.service;

import com.hjy.entity.system.SysLock;

public interface ILockService extends IBaseService<SysLock, Integer> {
	
	public String addLock(String keycode, Integer timeoutSecond, String uuid);
	
}
