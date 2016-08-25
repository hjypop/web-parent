package com.hjy.service.impl.webcore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjy.dao.webcore.IWcOpenApiDao;
import com.hjy.entity.webcore.WcOpenApi;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.webcore.IWcOpenApiService;

/**
 * 
 * 类: WcOpenApiServiceImpl <br>
 * 描述: openApi接口表业务处理接口实现类 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月25日 上午9:43:05
 */
@Service
public class WcOpenApiServiceImpl extends BaseServiceImpl<WcOpenApi, Integer> implements IWcOpenApiService {

	@Autowired
	private IWcOpenApiDao dao;
	
	@Override
	public int deleteApiCode(String apiCode) {
		return dao.deleteApiCode(apiCode);
	}

}
