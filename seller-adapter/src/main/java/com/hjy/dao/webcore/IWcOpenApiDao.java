package com.hjy.dao.webcore;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.entity.webcore.WcOpenApi;

/**
 * 
 * 类: IWcOpenApiDao <br>
 * 描述: openApi接口表数据库访问接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月25日 上午9:08:38
 */
public interface IWcOpenApiDao extends BaseDao<WcOpenApi, Integer>{

	/**
	 * 
	 * 方法: deleteByCode <br>
	 * 描述: 根据编号删除接口 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月25日 上午9:09:20
	 * @param code
	 * @return
	 */
	int deleteApiCode(String code);
	
	public List<WcOpenApi> selectAllInfo(WcOpenApi entity);
}
