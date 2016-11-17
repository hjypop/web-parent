package com.drwljrtv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.drwljrtv.request.video.GetCategory;
import com.drwljrtv.service.IndexService;
import com.drwljrtv.service.video.ICategoryService;

@Controller
@RequestMapping("index")
public class IndexController {

	@Autowired
	private IndexService service;
	@Autowired
	private ICategoryService categoryService;

	@RequestMapping("")
	public String index(ModelMap model) {
		model.addAttribute("shuffling", service.getShufflingImages());
		GetCategory categoryRequest = new GetCategory();
		categoryRequest.setTag(1);
		model.addAttribute("category", categoryService.getCategorys(categoryRequest));
		Integer videoTag = 3;
		model.addAttribute("categoryVideos", categoryService.getCategorysAndVideos(categoryRequest, videoTag));
		return "jsp/index";
	}

	@RequestMapping("shuffling")
	@ResponseBody
	public JSONArray getShufflingImages() {
		return service.getShufflingImages();
	}

}
