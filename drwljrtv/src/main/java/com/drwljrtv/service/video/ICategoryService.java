package com.drwljrtv.service.video;

import com.alibaba.fastjson.JSONArray;
import com.drwljrtv.request.video.GetCategory;

/**
 * 
 * 类: ICategoryService <br>
 * 描述: 视频分类接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月4日 下午9:50:24
 */
public interface ICategoryService {

	/**
	 * 
	 * 方法: getSubscriptionCategorys <br>
	 * 描述: 订阅视频分类 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月6日 下午8:38:35
	 * 
	 * @param request
	 * @return
	 */
	JSONArray getSubscriptionCategorys(GetCategory request);

	/**
	 * 
	 * 方法: getCategorys <br>
	 * 描述: 获取视频分类 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月4日 下午9:48:14
	 * 
	 * @return
	 */
	JSONArray getCategorys(GetCategory request);

	/**
	 * 
	 * 方法: getSubCategorys <br>
	 * 描述: 根据视频分类id查询子分类 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月4日 下午9:49:20
	 * 
	 * @param categoryId
	 * @return
	 */
	JSONArray getSubCategorys(GetCategory request);
}
