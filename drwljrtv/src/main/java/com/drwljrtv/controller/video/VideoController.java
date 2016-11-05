package com.drwljrtv.controller.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.drwljrtv.service.video.IVideoService;

@Controller
@RequestMapping("video")
public class VideoController {

	@Autowired
	private IVideoService service;

	/**
	 * 
	 * 方法: getVideo <br>
	 * 描述: 根据视频id获取视频详情 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月5日 下午3:59:17
	 * 
	 * @param videoId
	 * @param model
	 * @return
	 */
	@RequestMapping("detail")
	public String getVideo(Integer videoId, ModelMap model) {
		model.addAttribute("video", service.getVideo(videoId));
		return "jsp/video/detail";
	}
}
