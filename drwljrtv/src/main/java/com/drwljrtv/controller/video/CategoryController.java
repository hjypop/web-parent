package com.drwljrtv.controller.video;

import java.io.UnsupportedEncodingException;

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
		request.setTag(1);
		model.addAttribute("category", service.getCategorys(request));
		return "/jsp/category/index";
	}

	/**
	 * 
	 * 方法: subIndex <br>
	 * 描述: 查询分类信息及视频列表 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月17日 下午4:34:04
	 * 
	 * @param categoryId
	 * @param model
	 * @return
	 */
	@RequestMapping("subindex")
	public String subIndex(GetCategory request, ModelMap model) {
		try {
			model.addAttribute("categoryName", new String(request.getCategoryName().getBytes("ISO-8859-1"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			model.addAttribute("categoryName", "频道");
		}
		model.addAttribute("category", service.getSubCategorys(request));
		GetVideos videos = new GetVideos();
		videos.setCategoryId(request.getCategoryId());
		model.addAttribute("vidoes", videoService.getVideos(videos));
		return "/jsp/category/subindex";
	}

	/**
	 * 
	 * 方法: subscription <br>
	 * 描述: 订阅频道列表 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月17日 下午4:34:42
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("subscription")
	public String subscription(ModelMap model) {
		GetCategory request = new GetCategory();
		request.setTag(3);
		model.addAttribute("category", service.getSubscriptionCategorys(request));
		return "/jsp/category/subscription";
	}

}
