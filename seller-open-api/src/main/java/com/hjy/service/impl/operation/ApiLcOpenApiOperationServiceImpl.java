package com.hjy.service.impl.operation;

import org.springframework.stereotype.Service;

import com.hjy.entity.log.LcOpenApiOperation;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.operation.IApiLcOpenApiOperationService;

/**
 * @descriptions 所有接口调用信息，包括请求与返回值均保存在 lc_open_api_operation 表
 * 
 * @date 2016年8月7日上午10:19:11
 * @author Yangcl
 * @version 1.0.1
 */
@Service("apiLcOpenApiOperationService")
public class ApiLcOpenApiOperationServiceImpl  extends BaseServiceImpl<LcOpenApiOperation, Integer> implements IApiLcOpenApiOperationService{

}
