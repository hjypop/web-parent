package com.hjy.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.ILockDao;
import com.hjy.entity.system.SysLock;
import com.hjy.model.MObjMap;
import com.hjy.service.ILockService;

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
		String uid = "";
		MObjMap<String, Object> param = new MObjMap<String, Object>();
		param.put("somekey", "");
		param.put("keysplit", ",");
		param.put("timeoutsecond", new Integer(0));
		param.put("lockflag", "2");
		param.put("uuid", uid);
		return lockDao.addLock(param);
	}
}
