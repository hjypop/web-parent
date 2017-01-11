package com.hjy.service.system;

import com.alibaba.fastjson.JSONObject;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.system.ScFlowMain;
import com.hjy.service.IBaseService;
import com.hjy.system.cmodel.CacheWcSellerInfo;

public interface IScFlowMainService extends IBaseService<ScFlowMain, Integer> {

	public JSONObject createFlowMain(PcProductinfo product , CacheWcSellerInfo seller , String platform); 
}
