package com.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.core.dao.ILockDao;
import com.core.model.MObjMap;
import com.core.pojo.entity.system.SysLock;
import com.core.service.ILockService;

@Service("lockService")
public class LockServiceImpl extends BaseServiceImpl<SysLock, Integer> implements ILockService {
	@Resource
	private ILockDao lockDao;

	@Override
	public String addLock(String keycode, Integer timeoutSecond, String uuid) {
		MObjMap<String, Object> param = new MObjMap<String, Object>();
		param.put("somekey", keycode);
		param.put("keysplit", ",");
		param.put("timeoutsecond", timeoutSecond);
		param.put("lockflag", "1");
		param.put("uuid", uuid);
		return lockDao.addLock(param);
	}
	
	@Override
	public String unLock(String uuid) {
		MObjMap<String, Object> param = new MObjMap<String, Object>();
		param.put("somekey", "");
		param.put("keysplit", ",");
		param.put("timeoutsecond", new Integer(0));
		param.put("lockflag", "2");
		param.put("uuid", uuid);
		return lockDao.addLock(param);
	}
}
