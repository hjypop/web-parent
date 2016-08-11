package com.hjy.service.appid;

import com.hjy.entity.webcore.OpenApiAppid;
import com.hjy.service.IBaseService;

public interface IApiOpenApiAppidService extends IBaseService<OpenApiAppid, Integer> {

	/**
	 * @descriptions 根据 appid 、app_secret 和 flag=1 为条件，返回一条记录
	 * 
	 * @param appid 
	 * @date 2016年8月11日上午11:26:18
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	 public OpenApiAppid findByAppid(OpenApiAppid entity);
}
