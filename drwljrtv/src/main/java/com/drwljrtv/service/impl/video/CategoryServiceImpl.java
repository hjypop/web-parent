package com.drwljrtv.service.impl.video;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core.base.BaseClass;
import com.drwljrtv.request.video.GetCategory;
import com.drwljrtv.service.video.ICategoryService;
import com.drwljrtv.util.ApiHelper;
import com.drwljrtv.util.Constant;

/**
 * 
 * 类: CategoryServiceImpl <br>
 * 描述: 视频分类接口实现类 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月4日 下午9:50:19
 */
@Service
public class CategoryServiceImpl extends BaseClass implements ICategoryService {

	/**
	 * 
	 * 方法: getCategorys <br>
	 * 
	 * @return
	 * @see com.drwljrtv.service.video.ICategoryService#getCategorys()
	 */
	@Override
	public JSONArray getCategorys(GetCategory request) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "get_category");
		param.put("tag", String.valueOf(request.getTag()));
		JSONObject result = ApiHelper.getInstance().getResult(param);
		JSONArray array = null;
		if (StringUtils.equals(result.getString("state"), "ok")) {
			array = ApiHelper.getInstance().getResult(param).getJSONArray("data");
		}
		if (array != null && array.size() > 0) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				if (StringUtils.isNotBlank(obj.getString("thumb"))) {
					obj.put("thumb", Constant.URL + obj.get("thumb"));
				} else {
					obj.put("thumb", "assets/img/category/" + (i + 1) + ".png");
				}
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
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "get_category");
		param.put("tag", String.valueOf(request.getTag()));
		JSONObject result = ApiHelper.getInstance().getResult(param);
		JSONArray array = null;
		if (StringUtils.equals(result.getString("state"), "ok")) {
			array = ApiHelper.getInstance().getResult(param).getJSONArray("data");
		}
		if (array != null && array.size() > 0) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				if (StringUtils.isNotBlank(obj.getString("thumb"))) {
					obj.put("thumb", Constant.URL + obj.getString("thumb"));
				} else {
					obj.put("thumb", getDefaultImage());
				}
			}
		}
		return array;
	}

	/**
	 * 
	 * 方法: getDefaultImage <br>
	 * 描述: 随机获取图片 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月6日 下午7:26:17
	 * 
	 * @return
	 */
	private static String getDefaultImage() {
		int random = new Random().nextInt(37) + 1;
		return "assets/img/category/" + random + ".png";
	}

	@Override
	public JSONArray getSubscriptionCategorys(GetCategory request) {
		JSONArray data = new JSONArray();
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "get_category");
		param.put("tag", String.valueOf(request.getTag()));
		JSONObject result = ApiHelper.getInstance().getResult(param);
		JSONArray array = null;
		if (StringUtils.equals(result.getString("state"), "ok")) {
			array = ApiHelper.getInstance().getResult(param).getJSONArray("data");
		}
		if (array != null && array.size() > 0) {
			int total = array.size() <= 12 ? array.size() : 12;
			for (int i = 0; i < total; i++) {
				JSONObject obj = array.getJSONObject(i);
				if (StringUtils.isNotBlank(obj.getString("thumb"))) {
					obj.put("thumb", Constant.URL + obj.get("thumb"));
				} else {
					obj.put("thumb", "assets/img/category/" + (i + 1) + ".png");
				}
				data.add(obj);
			}
		}
		return data;
	}
}