package com.hjy.service.operations;

import com.alibaba.fastjson.JSONObject;
import com.hjy.service.IBaseService;

/**
 * @descriptions 执行运营人员的线上临时需求，如：一批商品下架、一批商品上架等等。
 * 这个类中的所有接口全部用于处理运营人员的线上临时需求，不对外开放。
 * 
 * @date 2016年8月15日下午5:08:01
 * @author Yangcl
 * @version 1.0.1
 */
public interface IOperationsManagerService extends IBaseService<Object, Integer> {

	public JSONObject upStorage(String excelPath);
}
