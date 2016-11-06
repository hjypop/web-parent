package com.drwljrtv.controller.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.drwljrtv.request.video.GetCategory;
import com.drwljrtv.request.video.GetVideos;
import com.drwljrtv.service.video.ICategoryService;
import com.drwljrtv.service.video.IVideoService;

@Controller
@RequestMapping("category")
public class CategoryController {

	@Autowired
	private ICategoryService service;
	@Autowired
	private IVideoService videoService;

	@RequestMapping("index")
	public String index(ModelMap model) {
		GetCategory request = new GetCategory();
		request.setTag(3);
		model.addAttribute("category", service.getCategorys(request));
		return "/jsp/category/index";
	}

	@RequestMapping("subindex")
	public String subIndex(Integer categoryId, ModelMap model) {
		GetCategory request = new GetCategory();
		request.setTag(3);
		request.setCategoryId(categoryId);
		model.addAttribute("category", service.getSubCategorys(request));
		GetVideos videos = new GetVideos();
		videos.setCategoryId(categoryId);
		model.addAttribute("vidoes", videoService.getVideos(videos));
		return "/jsp/category/subindex";
	}

	@RequestMapping("subscription")
	public String subscription(ModelMap model) {
		GetCategory request = new GetCategory();
		request.setTag(3);
		model.addAttribute("category", service.getSubscriptionCategorys(request));
		return "/jsp/category/subscription";
	}

}
