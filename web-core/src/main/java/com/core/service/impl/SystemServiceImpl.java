package com.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.dao.ILockDao;
import com.core.dao.ISysErrorDao;
import com.core.dao.ISysWebcodeDao;
import com.core.model.MObjMap;
import com.core.pojo.dto.SystemUtil;
import com.core.pojo.entity.system.SysError;
import com.core.service.ISystemService;

@Service("systemService")
public class SystemServiceImpl extends BaseServiceImpl<SystemUtil, Integer> implements ISystemService {

	@Autowired
	private ILockDao lockDao;
	@Autowired
	private ISysWebcodeDao sysWebcodeDao;
	@Autowired
	private ISysErrorDao sysErrorDao;

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

	public Integer addSystemError(SysError entity) {
		return sysErrorDao.insertSelective(entity);
	}

}
