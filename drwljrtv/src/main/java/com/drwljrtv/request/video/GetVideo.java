package com.drwljrtv.request.video;

import com.drwljrtv.request.BaseRequest;

/**
 * 
 * 类: GetVideo <br>
 * 描述: 获取视频的详细信息参数 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月4日 下午5:50:26
 */
public class GetVideo extends BaseRequest {

	/**
	 * 视频id
	 */
	private Integer videoId;
	/**
	 * 视频访问密码，对于需要密码访问的视频，必须要输入这个参数，用户才可以播放（才会输出播放地址）
	 */
	private String videoPassword;

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public String getVideoPassword() {
		return videoPassword;
	}

	public void setVideoPassword(String videoPassword) {
		this.videoPassword = videoPassword;
	}

}
