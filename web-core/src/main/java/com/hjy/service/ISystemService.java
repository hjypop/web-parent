package com.hjy.service;

import com.hjy.dto.SystemUtil;

public interface ISystemService extends IBaseService<SystemUtil , Integer>{

	public String addLock(String keycode, Integer timeoutSecond, String uuid);
	
	public String unLock(String uuid);
	
	public String getUniqueCode(String codeStart);
}
