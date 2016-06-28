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
public class LcRsyncKjtLogServiceImpl implements ILcRsyncKjtLogService {

	@Resource
	private ILcRsyncKjtLogDao dao;

	@Override
	public Integer insertSelective(LcRsyncKjtLog entity) {
		return dao.insertSelective(entity);
	}

	@Override
	public Integer insertGotEntityId(LcRsyncKjtLog entity) {
		return null;
	}

	@Override
	public Integer insertGotEntityUuid(LcRsyncKjtLog entity) {
		return null;
	}

	@Override
	public Integer batchInsert(List<LcRsyncKjtLog> list) {
		return null;
	}

	@Override
	public Integer updateSelective(LcRsyncKjtLog entity) {
		return null;
	}

	@Override
	public Integer batchUpdate(List<LcRsyncKjtLog> list) {
		return null;
	}

	@Override
	public Integer deleteById(Integer id) {
		return null;
	}

	@Override
	public Integer batchDelete(List<Integer> list) {
		return null;
	}

	@Override
	public <DTO> void deleteByCondition(DTO dto) {
	}

	@Override
	public LcRsyncKjtLog find(Integer id) {
		return null;
	}

	@Override
	public List<LcRsyncKjtLog> findList(LcRsyncKjtLog entity) {
		return null;
	}

	@Override
	public JSONObject jsonList(LcRsyncKjtLog entity) {
		return null;
	}

	@Override
	public <DTO> List<LcRsyncKjtLog> findGroupList(DTO dto) {
		return null;
	}

	@Override
	public int count(LcRsyncKjtLog entity) {
		return 0;
	}

	@Override
	public List<LcRsyncKjtLog> queryPage(LcRsyncKjtLog entity) {
		return null;
	}

	@Override
	public List<LcRsyncKjtLog> like(LcRsyncKjtLog entity) {
		return null;
	}

	@Override
	public Integer selectMaxId() {
		return null;
	}

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
