package com.hjy.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.base.BaseClass;
import com.hjy.dao.BaseDao;
import com.hjy.service.IBaseService;

/**
 * @descriptions 底层实现类
 * 
 * @param <T>
 * @param <Pk>
 * @date 2016年5月19日下午4:48:21
 * @author Yangcl
 * @version 1.0.1
 */
// @Service("baseService") abstract
public class BaseServiceImpl<T, PK extends Serializable> extends BaseClass implements IBaseService<T, PK> {

	public static Logger logger = Logger.getLogger(BaseServiceImpl.class);

	@Autowired
	public BaseDao<T, PK> baseDao;

	@Override
	public Integer insertSelective(T entity) {
		return baseDao.insertSelective(entity);
	}

	@Override
	public Integer insertGotEntityId(T entity) {
		return baseDao.insertGotEntityId(entity);
	}

	@Override
	public Integer insertGotEntityUuid(T entity) {
		return baseDao.insertGotEntityUuid(entity);
	}

	@Override
	public Integer batchInsert(List<T> list) {
		return baseDao.batchInsert(list);
	}

	@Override
	public Integer updateSelective(T entity) {
		// TODO Auto-generated method stub
		return baseDao.updateSelective(entity);
	}

	@Override
	public Integer batchUpdate(List<T> list) {
		return baseDao.batchUpdate(list);
	}

	@Override
	public Integer deleteById(PK id) {
		return baseDao.deleteById(id);
	}

	@Override
	public Integer batchDelete(List<Integer> list) {
		return baseDao.batchDelete(list);
	}

	@Override
	public <DTO> void deleteByCondition(DTO dto) {
		baseDao.deleteByCondition(dto);
	}

	@Override
	public T find(PK id) {
		return baseDao.find(id);
	}

	@Override
	public List<T> findList(T entity) {
		return baseDao.findList(entity);
	}

	@Override
	public JSONObject jsonList(T entity) {
		JSONObject result = new JSONObject();
		List<T> list = baseDao.findList(entity);
		if (list != null && list.size() > 0) {
			result.put("status", "success");
			result.put("list", list);
			return result;
		} else {
			result.put("status", "error");
			result.put("msg", "结果集为空");
			return result;
		}
	}

	@Override
	public <DTO> List<T> findGroupList(DTO dto) {
		return baseDao.findGroupList(dto);
	}

	@Override
	public int count(T entity) {
		return baseDao.count(entity);
	}

	@Override
	public List<T> queryPage(T entity) {
		return baseDao.queryPage(entity);
	}

	@Override
	public List<T> like(T entity) {
		return baseDao.like(entity);
	}

	@Override
	public Integer selectMaxId() {
		return baseDao.selectMaxId();
	}

	@Override
	public JSONObject ajaxPageData(T entity, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String pageNum = request.getParameter("pageNum"); // 当前第几页
		String pageSize = request.getParameter("pageSize"); // 当前页所显示记录条数
		int num = 1;
		int size = 10;
		if (StringUtils.isNotBlank(pageNum)) {
			num = Integer.parseInt(pageNum);
		}
		if (StringUtils.isNotBlank(pageSize)) {
			size = Integer.parseInt(pageSize);
		}

		/*
		 * 如果分页参数当前页为空，默认为0，页面最大显示数为空，默认为10
		 */
//		String sortString = "create_time.desc";
//		Order.formString(sortString); 
		PageHelper.startPage(num, size);
		List<T> list = baseDao.queryPage(entity);
		if (list != null && list.size() > 0) {
			result.put("status", "success");
		} else {
			result.put("status", "error");
			result.put("msg", "没有查询到可以显示的数据");
		}
		PageInfo<T> pageList = new PageInfo<T>(list);
		result.put("data", pageList);
		result.put("entity", entity);
		return result;
	}

}
