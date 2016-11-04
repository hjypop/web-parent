package com.drwljrtv.controller.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.drwljrtv.request.video.GetVideos;
import com.drwljrtv.service.video.IVideoService;

@Controller
@RequestMapping("video")
public class VideoController {

	@Autowired
	private IVideoService service;

	@RequestMapping("index")
	@ResponseBody
	public JSONArray getVideos() {
		GetVideos request = new GetVideos();
		request.setTag(1);
		return service.getVideos(request);
	}
}
