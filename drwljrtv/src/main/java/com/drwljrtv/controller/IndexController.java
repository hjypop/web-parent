package com.drwljrtv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.drwljrtv.request.video.GetCategory;
import com.drwljrtv.request.video.GetVideos;
import com.drwljrtv.service.IndexService;
import com.drwljrtv.service.video.ICategoryService;
import com.drwljrtv.service.video.IVideoService;

@Controller
@RequestMapping("index")
public class IndexController {

	@Autowired
	private IndexService service;
	@Autowired
	private IVideoService videoService;
	@Autowired
	private ICategoryService categoryService;

	@RequestMapping("")
	public String index(ModelMap model) {
		model.addAttribute("shuffling", service.getShufflingImages());
		GetVideos vidoeRequest = new GetVideos();
		vidoeRequest.setTag(5);
		model.addAttribute("video", videoService.getVideos(vidoeRequest));
		GetCategory categoryRequest = new GetCategory();
		categoryRequest.setTag(2);
		model.addAttribute("category", categoryService.getCategorys(categoryRequest));
		return "jsp/index";
	}

	@RequestMapping("shuffling")
	@ResponseBody
	public JSONArray getShufflingImages() {
		return service.getShufflingImages();
	}

}
