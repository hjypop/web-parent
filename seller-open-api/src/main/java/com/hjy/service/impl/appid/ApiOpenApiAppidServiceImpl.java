package com.hjy.service.impl.appid;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.webcore.IOpenApiAppidDao;
import com.hjy.entity.webcore.OpenApiAppid;
import com.hjy.service.appid.IApiOpenApiAppidService;
import com.hjy.service.impl.BaseServiceImpl;

@Service("appidService")
public class ApiOpenApiAppidServiceImpl extends BaseServiceImpl<OpenApiAppid, Integer> implements IApiOpenApiAppidService{
	
	@Resource
	private IOpenApiAppidDao dao;

	/**
	 * @descriptions 根据 appid 、app_secret 和 flag=1 为条件，返回一条记录
	 * 
	 * @param appid 
	 * @date 2016年8月11日上午11:26:18
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public OpenApiAppid findByAppid(OpenApiAppid entity) {
		return dao.findByAppid(entity); 
	}
	
}
