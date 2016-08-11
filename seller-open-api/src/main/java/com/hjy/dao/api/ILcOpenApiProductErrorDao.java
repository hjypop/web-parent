package com.hjy.dao.api;

import com.hjy.entity.log.LcOpenApiProductError;

/**
 * 
 * 类: LcOpenApiProductErrorMapper <br>
 * 描述: openapi产品错误日志表数据库访问接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月11日 上午11:09:00
 */
public interface ILcOpenApiProductErrorDao {

	/**
	 * 
	 * 方法: insertSelective <br>
	 * 描述: 添加错误日志  <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月11日 上午11:09:18
	 * @param record
	 * @return
	 */
	int insertSelective(LcOpenApiProductError record);
}