package com.hjy.service.system;

import java.util.List;

import com.hjy.entity.system.ScDefine;

/**
 * 
 * 类: IScDefineService <br>
 * 描述: 系统定义表业务逻辑处理接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月18日 上午9:12:48
 */
public interface IScDefineService {
	/**
	 * 
	 * 方法: selectByDefineCode <br>
	 * 描述: 根据定义编号查询系统定义 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月18日 上午9:11:26
	 * 
	 * @param defineCode
	 * @return
	 */
	ScDefine selectByDefineCode(String defineCode);

	/**
	 * 
	 * 方法: findDefineByParentCode <br>
	 * 描述: 根据父编号查询系统定义集合 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月18日 上午9:11:44
	 * 
	 * @param parentCode
	 * @return
	 */
	List<ScDefine> findDefineByParentCode(String parentCode);
}
