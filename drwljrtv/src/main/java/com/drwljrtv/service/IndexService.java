package com.drwljrtv.service;

import com.alibaba.fastjson.JSONArray;

/**
 * 
 * 类: IndexService <br>
 * 描述: 首页接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月5日 上午9:47:22
 */
public interface IndexService {

	/**
	 * 
	 * 方法: getShufflingImages <br>
	 * 描述: 获取首页轮播图 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月5日 上午9:47:35
	 * 
	 * @return
	 */
	JSONArray getShufflingImages();
}
