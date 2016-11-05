package com.drwljrtv.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.drwljrtv.service.IndexService;
import com.drwljrtv.util.ApiHelper;

/**
 * 
 * 类: IndexServiceImpl <br>
 * 描述: 首页接口实现类 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月5日 上午9:49:41
 */
@Service
public class IndexServiceImpl implements IndexService {

	@Override
	public JSONArray getShufflingImages() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "get_home_banner");
		return ApiHelper.getInstance().getData(param);
	}
}
