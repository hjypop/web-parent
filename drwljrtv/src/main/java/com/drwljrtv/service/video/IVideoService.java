package com.drwljrtv.service.video;

import java.util.List;

import com.drwljrtv.model.Video;
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
	List<Video> getVideos(GetVideos request);

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
	Video getVideo(Integer videoId);
}
