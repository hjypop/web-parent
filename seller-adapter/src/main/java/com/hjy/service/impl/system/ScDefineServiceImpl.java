package com.hjy.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjy.dao.system.IScDefineDao;
import com.hjy.entity.system.ScDefine;
import com.hjy.service.system.IScDefineService;

/**
 * 
 * 类: ScDefineServiceImpl <br>
 * 描述: 系统定义表业务逻辑处理接口实现类 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月18日 上午9:13:05
 */
@Service
public class ScDefineServiceImpl implements IScDefineService {

	@Autowired
	private IScDefineDao dao;

	/**
	 * 
	 * 方法: selectByDefineCode <br>
	 * 描述: TODO
	 * 
	 * @param defineCode
	 * @return
	 * @see com.hjy.service.system.IScDefineService#selectByDefineCode(java.lang.String)
	 */
	@Override
	public ScDefine selectByDefineCode(String defineCode) {
		return dao.selectByDefineCode(defineCode);
	}

	/**
	 * 
	 * 方法: findDefineByParentCode <br>
	 * 描述: TODO
	 * 
	 * @param parentCode
	 * @return
	 * @see com.hjy.service.system.IScDefineService#findDefineByParentCode(java.lang.String)
	 */
	@Override
	public List<ScDefine> findDefineByParentCode(String parentCode) {
		return dao.findDefineByParentCode(parentCode);
	}

}
