package com.drwljrtv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.drwljrtv.response.BaseResponse;

/**
 * 
 * 类: VideoCntroller <br>
 * 描述: 视频相关 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月4日 下午5:53:50
 */
@Controller
@RequestMapping("video")
public class VideoCntroller {

	@RequestMapping("category")
	@ResponseBody
	public BaseResponse getCategory() {
		return null;
	}

	@RequestMapping("video")
	@ResponseBody
	public BaseResponse getVideo() {
		return null;
	}

	@RequestMapping("videos")
	@ResponseBody
	public BaseResponse getVideos() {
		return null;
	}
}
