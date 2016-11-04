package com.drwljrtv.service.video.impl.video;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core.base.BaseClass;
import com.core.system.PureNetUtil;
import com.drwljrtv.request.video.GetVideo;
import com.drwljrtv.request.video.GetVideos;
import com.drwljrtv.service.video.IVideoService;

/**
 * 
 * 类: VideoServiceImpl <br>
 * 描述: 视频相关数据接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月4日 下午5:31:15
 */
@Service
public class VideoServiceImpl extends BaseClass implements IVideoService {

	private static final String URL = "http://www.bdysgz.net/actions/mobile_api.php";

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
		JSONArray videos = null;
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "get_videos");
		if (request != null) {
			if(request.getCategoryId()!=null){
				param.put("category_id", String.valueOf(request.getCategoryId()));				
			}
			if(request.getTag() != null){
				param.put("tag", String.valueOf(request.getTag()));
			}
			if(request.getUserId() != null){
				param.put("userid", String.valueOf(request.getUserId()));	
			}
			if(request.getOrder() !=  null){
				param.put("order", String.valueOf(request.getOrder()));
			}
			if(request.getSource() != null){
				param.put("source", String.valueOf(request.getSource()));				
			}
			if(StringUtils.isNotBlank(request.getStatus())){
				param.put("status", request.getStatus());				
			}
			if(StringUtils.isNotBlank(request.getBroadcast())){
				param.put("broadcast", request.getBroadcast());	
			}
			if(StringUtils.isNotBlank(request.getActive())){
				param.put("active", request.getActive());	
			}
			if(StringUtils.isNotBlank(request.getExclude())){
				param.put("exclude", request.getExclude());	
			}
			if(StringUtils.isNotBlank(request.getShowRelated())){
				param.put("show_related", request.getShowRelated());				
			}
			if(request.getPageSize() != null){
				param.put("page_size", String.valueOf(request.getPageSize()));				
			}
			if(request.getPage() != null){
				param.put("page", String.valueOf(request.getPage()));
			}
			
		}
		// 调用接口
		String response = PureNetUtil.post(URL, param);
		if (StringUtils.isNoneBlank(response)) {
			JSONObject result = JSON.parseObject(response);
			if (result.getInteger("ret") != null && result.getInteger("ret") == 0) {
				JSONObject obj = result.getJSONObject("data");
				if (StringUtils.equals(obj.getString("state"), "ok")) {
					// 获取数据
					videos = JSONArray.parseArray(obj.getString("data"));
				}
			}
		}
		return videos;
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
	public JSONObject getVideo(GetVideo request) {
		JSONObject video = null;
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "get_video");
		param.put("videoid", String.valueOf(request.getVideoId()));
		param.put("video_password", request.getVideoPassword());
		// 调用接口
		String response = PureNetUtil.post(URL, param);
		if (StringUtils.isNoneBlank(response)) {
			JSONObject result = JSON.parseObject(response);
			if (result.getInteger("ret") == 0) {
				JSONObject obj = result.getJSONObject("data");
				if (StringUtils.equals(obj.getString("state"), "ok")) {
					// 获取数据
					video = JSONObject.parseObject(obj.getString("data"));
				}
			}
		}
		return video;
	}
}