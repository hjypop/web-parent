package com.hjy.service.webcore;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.hjy.entity.webcore.WcOpenApi;
import com.hjy.service.IBaseService;

/**
 * 
 * 类: IWcOpenApiService <br>
 * 描述: openApi接口表业务处理接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月25日 上午9:41:14
 */
public interface IWcOpenApiService extends IBaseService<WcOpenApi, Integer>{

	public JSONObject deleteApiCode(String apiCode);
	
	public JSONObject insertWcOpenApi(WcOpenApi entity , HttpSession session);
	
	public JSONObject updateWcOpenApi(WcOpenApi entity , HttpSession session);
}
