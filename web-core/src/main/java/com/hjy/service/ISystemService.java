package com.hjy.service;

import com.hjy.pojo.dto.SystemUtil;
import com.hjy.pojo.entity.system.SysError;

public interface ISystemService extends IBaseService<SystemUtil , Integer>{

	public String addLock(String keycode, Integer timeoutSecond, String uuid);
	
	public String unLock(String uuid);
	
	public String getUniqueCode(String codeStart);
	
	public Integer addSystemError(SysError entity);
}
