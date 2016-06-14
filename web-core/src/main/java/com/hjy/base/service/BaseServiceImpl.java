package com.hjy.base.service;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.hjy.base.dao.BaseDao;

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
public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {
	
	public static Logger logger = Logger.getLogger(BaseServiceImpl.class);
	
	@Autowired
	public BaseDao<T,PK> baseDao;

	@Override
	public PK insertSelective(T entity) {
		// TODO Auto-generated method stub
		return baseDao.insertSelective(entity);
	}

	@Override
	public PK insertGotEntityId(T entity) {
		// TODO Auto-generated method stub
		return baseDao.insertGotEntityId(entity);
	}

	@Override
	public PK insertGotEntityUuid(T entity) {
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
		// TODO Auto-generated method stub
		return null;
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












 