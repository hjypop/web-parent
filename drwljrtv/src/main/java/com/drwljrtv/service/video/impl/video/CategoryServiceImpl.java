package com.drwljrtv.service.video.impl.video;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core.base.BaseClass;
import com.core.system.PureNetUtil;
import com.drwljrtv.request.video.GetCategory;
import com.drwljrtv.service.video.ICategoryService;

/**
 * 
 * 类: CategoryServiceImpl <br>
 * 描述: 视频分类接口实现类 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月4日 下午9:50:19
 */
@Service
public class CategoryServiceImpl extends BaseClass implements ICategoryService {

	private static final String URL = "http://www.bdysgz.net/actions/mobile_api.php";

	/**
	 * 
	 * 方法: getCategorys <br>
	 * 
	 * @return
	 * @see com.drwljrtv.service.video.ICategoryService#getCategorys()
	 */
	@Override
	public JSONArray getCategorys(GetCategory request) {
		JSONArray array = null;
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "get_category");
		param.put("tag", String.valueOf(request.getTag()));
		String response = PureNetUtil.post(URL, param);
		JSONObject result = JSONObject.parseObject(response);
		if (result.getInteger("ret") == 0) {
			JSONObject obj = result.getJSONObject("data");
			if (StringUtils.equals(obj.getString("state"), "ok")) {
				array = JSONArray.parseArray(obj.getString("data"));
			}
		}
		return array;
	}

	/**
	 * 
	 * 方法: getSubCategorys <br>
	 * 
	 * @param categoryId
	 * @return
	 * @see com.drwljrtv.service.video.ICategoryService#getSubCategorys(java.lang.Integer)
	 */
	@Override
	public JSONArray getSubCategorys(GetCategory request) {
		JSONArray array = null;
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "get_category");
		param.put("tag", String.valueOf(request.getTag()));
		String response = PureNetUtil.post(URL, param);
		JSONObject result = JSONObject.parseObject(response);
		if (result.getInteger("ret") == 0) {
			JSONObject obj = result.getJSONObject("data");
			if (StringUtils.equals(obj.getString("state"), "ok")) {
				array = JSONArray.parseArray(obj.getString("data"));
				for (int i = 0; i < array.size(); i++) {
					JSONObject sub = array.getJSONObject(i);
					if (sub.getInteger("category_id") == request.getCategoryId()) {
						array = JSONArray.parseArray(sub.getString("sunclass"));
					}
				}
			}
		}
		return array;
	}
}