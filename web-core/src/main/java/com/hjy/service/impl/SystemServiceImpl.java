package com.hjy.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.ILockDao;
import com.hjy.dao.ISysWebcodeDao;
import com.hjy.dto.SystemUtil;
import com.hjy.model.MObjMap;
import com.hjy.service.ISystemService;

@Service("systemService")
public class SystemServiceImpl extends BaseServiceImpl<SystemUtil, Integer> implements ISystemService {
	
	@Resource
	private ILockDao lockDao;
	@Resource
	private ISysWebcodeDao sysWebcodeDao; 
	
	public String addLock(String keycode, Integer timeoutSecond, String uuid) {
		MObjMap<String, Object> param = new MObjMap<String, Object>();
		param.put("somekey", keycode);
		param.put("keysplit", ",");
		param.put("timeoutsecond", timeoutSecond);
		param.put("lockflag", "1");
		param.put("uuid", uuid);
		return lockDao.addLock(param);
	}

	public String unLock(String uuid) {
		MObjMap<String, Object> param = new MObjMap<String, Object>();
		param.put("somekey", "");
		param.put("keysplit", ",");
		param.put("timeoutsecond", new Integer(0));
		param.put("lockflag", "2");
		param.put("uuid", uuid);
		return lockDao.addLock(param);
	}

	public String getUniqueCode(String codeStart) {
		MObjMap<String, Object> param = new MObjMap<String, Object>();
		param.put("code", codeStart);
		return sysWebcodeDao.callUniqueCode(param); 
	}

															  

}



































