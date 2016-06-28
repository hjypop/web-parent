package com.hjy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.ILcRsyncKjtLogDao;
import com.hjy.entity.LcRsyncKjtLog;
import com.hjy.service.ILcRsyncKjtLogService;

/**
 * 
 * 类: LcRsyncKjtLogServiceImpl <br>
 * 描述: 同步跨境通日志表业务逻辑处理实现接口实现类<br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午3:48:52
 */
@Service
public class LcRsyncKjtLogServiceImpl extends BaseServiceImpl<LcRsyncKjtLog, Integer> implements ILcRsyncKjtLogService {

	@Resource
	private ILcRsyncKjtLogDao dao;

	/**
	 * 
	 * 方法: findLatelyStatusData <br>
	 * 描述: TODO
	 * 
	 * @param rsyncTarget
	 * @return
	 * @see com.hjy.service.ILcRsyncKjtLogService#findLatelyStatusData(java.lang.String)
	 */
	@Override
	public String findLatelyStatusData(String rsyncTarget) {
		return dao.findLatelyStatusData(rsyncTarget);
	}

}
