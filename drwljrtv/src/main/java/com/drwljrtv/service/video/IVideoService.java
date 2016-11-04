package com.drwljrtv.service.video;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.drwljrtv.request.video.GetVideo;
import com.drwljrtv.request.video.GetVideos;

/**
 * 
 * 类: IVideoService <br>
 * 描述: 视频相关数据接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月4日 下午5:30:59
 */
public interface IVideoService {

	/**
	 * 
	 * 方法: getVideos <br>
	 * 描述: 获取视频列表。 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月4日 下午11:10:13
	 * 
	 * @param request
	 * @return
	 */
	JSONArray getVideos(GetVideos request);

	/**
	 * 
	 * 方法: getVideo <br>
	 * 描述: 获取视频的详细信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月4日 下午11:11:17
	 * 
	 * @param request
	 * @return
	 */
	JSONObject getVideo(GetVideo request);
}
