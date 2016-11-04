package com.drwljrtv.controller.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.drwljrtv.request.video.GetCategory;
import com.drwljrtv.service.video.ICategoryService;

@Controller
@RequestMapping("category")
public class CategoryController {

	@Autowired
	private ICategoryService service;

	@RequestMapping("index")
	@ResponseBody
	public JSONArray getCategory() {
		GetCategory request = new GetCategory();
		request.setTag(1);
		JSONArray array = service.getCategorys(request);
		return array;
	}
}
