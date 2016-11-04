package com.drwljrtv.service;

import com.drwljrtv.response.BaseResponse;

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
	 * 方法: getCategory <br>
	 * 描述: 获取视频分类 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月4日 下午5:55:29
	 * 
	 * @return
	 */
	public BaseResponse getCategory();

	/**
	 * 
	 * 方法: getVideo <br>
	 * 描述: 获取视频的详细信息参数 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月4日 下午5:55:32
	 * 
	 * @return
	 */
	public BaseResponse getVideo();

	/**
	 * 
	 * 方法: getVideos <br>
	 * 描述: 获取视频列表列表 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月4日 下午5:55:35
	 * 
	 * @return
	 */
	public BaseResponse getVideos();
}
