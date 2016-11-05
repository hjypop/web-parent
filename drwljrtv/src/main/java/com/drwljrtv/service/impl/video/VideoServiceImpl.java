package com.drwljrtv.service.impl.video;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core.base.BaseClass;
import com.drwljrtv.request.video.GetVideos;
import com.drwljrtv.service.video.IVideoService;
import com.drwljrtv.util.ApiHelper;
import com.drwljrtv.util.Constant;

/**
 * 
 * 类: VideoServiceImpl <br>
 * 描述: 视频相关数据接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月4日 下午5:31:15
 */
@Service
public class VideoServiceImpl extends BaseClass implements IVideoService {

	/**
	 * 
	 * 方法: getVideos <br>
	 * 
	 * @param request
	 * @return
	 * @see com.drwljrtv.service.video.IVideoService#getVideos(com.drwljrtv.request.video.GetVideos)
	 */
	@Override
	public JSONArray getVideos(GetVideos request) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "get_videos");
		if (request != null) {
			if (request.getCategoryId() != null) {
				param.put("category_id", String.valueOf(request.getCategoryId()));
			}
			if (request.getTag() != null) {
				param.put("tag", String.valueOf(request.getTag()));
			}
			if (request.getUserId() != null) {
				param.put("userid", String.valueOf(request.getUserId()));
			}
			if (request.getOrder() != null) {
				param.put("order", String.valueOf(request.getOrder()));
			}
			if (request.getSource() != null) {
				param.put("source", String.valueOf(request.getSource()));
			}
			if (StringUtils.isNotBlank(request.getStatus())) {
				param.put("status", request.getStatus());
			}
			if (StringUtils.isNotBlank(request.getBroadcast())) {
				param.put("broadcast", request.getBroadcast());
			}
			if (StringUtils.isNotBlank(request.getActive())) {
				param.put("active", request.getActive());
			}
			if (StringUtils.isNotBlank(request.getExclude())) {
				param.put("exclude", request.getExclude());
			}
			if (StringUtils.isNotBlank(request.getShowRelated())) {
				param.put("show_related", request.getShowRelated());
			}
			if (request.getPageSize() != null) {
				param.put("page_size", String.valueOf(request.getPageSize()));
			}
			if (request.getPage() != null) {
				param.put("page", String.valueOf(request.getPage()));
			}

		}
		JSONArray array = ApiHelper.getInstance().getData(param);
		if (array != null && array.size() > 0) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				if (StringUtils.isNotBlank(obj.getString("thumb"))) {
					obj.put("thumb", Constant.URL + obj.getString("thumb"));
				} else {
					obj.put("thumb", Constant.NO_THUMB);
				}
				if (StringUtils.isNotBlank(obj.getString("big_thumb"))) {
					obj.put("big_thumb", Constant.URL + obj.getString("big_thumb"));
				} else {
					obj.put("big_thumb", Constant.NO_THUMB);
				}
			}
		}
		return array;
	}

	/**
	 * 
	 * 方法: getVideo <br>
	 * 
	 * @param request
	 * @return
	 * @see com.drwljrtv.service.video.IVideoService#getVideo(com.drwljrtv.request.video.GetVideo)
	 */
	@Override
	public JSONObject getVideo(Integer videoId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "get_video");
		param.put("videoid", String.valueOf(videoId));
		JSONObject obj = ApiHelper.getInstance().getObj(param);
		if (StringUtils.isNotBlank(obj.getString("thumb"))) {
			obj.put("thumb", Constant.URL + obj.getString("thumb"));
		} else {
			obj.put("thumb", Constant.NO_THUMB);
		}
		if (StringUtils.isNotBlank(obj.getString("big_thumb"))) {
			obj.put("big_thumb", Constant.URL + obj.getString("big_thumb"));
		} else {
			obj.put("big_thumb", Constant.NO_THUMB);
		}
		if (StringUtils.isNoneBlank(obj.getString("streams"))) {
			JSONObject streams = JSONObject.parseObject(obj.getString("streams"));
			if (StringUtils.isNotBlank(streams.getString("hd"))) {
				obj.put("video_href", streams.getString("hd"));
				obj.put("video_size", streams.getString("hd_size"));
			}
		}
		return obj;
	}
}