package com.core.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.core.dao.BaseDao;
import com.core.service.IBaseService;

/**
 * @descriptions 底层实现类
 * 
 * @param <T>
 * @param <Pk>
 * @date 2016年5月19日下午4:48:21
 * @author Yangcl
 * @version 1.0.1
 */
//@Service("baseService")  abstract
public class BaseServiceImpl<T, PK extends Serializable> implements IBaseService<T, PK> {
	
	public static Logger logger = Logger.getLogger(BaseServiceImpl.class);
	
	@Autowired
	public BaseDao<T,PK> baseDao;

	@Override
	public Integer insertSelective(T entity) {
		// TODO Auto-generated method stub
		return baseDao.insertSelective(entity);
	}

	@Override
	public Integer insertGotEntityId(T entity) {
		// TODO Auto-generated method stub
		return baseDao.insertGotEntityId(entity);
	}

	@Override
	public Integer insertGotEntityUuid(T entity) {
		// TODO Auto-generated method stub
		return baseDao.insertGotEntityUuid(entity);
	}

	@Override
	public Integer batchInsert(List<T> list) {
		// TODO Auto-generated method stub
		return baseDao.batchInsert(list); 
	}

	@Override
	public Integer updateSelective(T entity) {
		// TODO Auto-generated method stub
		return baseDao.updateSelective(entity);
	}

	@Override
	public Integer batchUpdate(List<T> list) {
		// TODO Auto-generated method stub
		return baseDao.batchUpdate(list);
	}

	@Override
	public Integer deleteById(PK id) {
		// TODO Auto-generated method stub
		return baseDao.deleteById(id);
	}

	@Override
	public Integer batchDelete(List<Integer> list) {
		// TODO Auto-generated method stub
		return baseDao.batchDelete(list); 
	}

	@Override
	public <DTO> void deleteByCondition(DTO dto) {
		// TODO Auto-generated method stub
		baseDao.deleteByCondition(dto);
	}

	@Override
	public T find(PK id) {
		// TODO Auto-generated method stub
		return baseDao.find(id);
	}

	@Override
	public List<T> findList(T entity) {
		// TODO Auto-generated method stub
		return baseDao.findList(entity);
	}

	@Override
	public JSONObject jsonList(T entity) { 
		JSONObject result = new JSONObject();
		List<T> list = baseDao.findList(entity);
		if(list != null && list.size() > 0){
			result.put("status" , "success");
			result.put("list" , list);
			return result;
		}else{
			result.put("status", "error");
			result.put("msg", "结果集为空");
			return result;
		}
	}

	@Override
	public <DTO> List<T> findGroupList(DTO dto) {
		// TODO Auto-generated method stub
		return baseDao.findGroupList(dto);
	}

	@Override
	public int count(T entity) {
		// TODO Auto-generated method stub
		return baseDao.count(entity);
	}

	@Override
	public List<T> queryPage(T entity) {
		// TODO Auto-generated method stub
		return baseDao.queryPage(entity);
	}

	@Override
	public List<T> like(T entity) {
		// TODO Auto-generated method stub
		return baseDao.like(entity);
	}

	@Override
	public Integer selectMaxId() {
		// TODO Auto-generated method stub
		return baseDao.selectMaxId();
	}
}












 