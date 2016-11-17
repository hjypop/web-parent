package com.drwljrtv;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.drwljrtv.model.Category;
import com.drwljrtv.request.video.GetCategory;
import com.drwljrtv.service.video.ICategoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class CategoryTest {

	@Autowired
	private ICategoryService service;

	public void getCategorys() {
		GetCategory request = new GetCategory();
		request.setTag(1);
		List<Category> list = service.getCategorys(request);
		System.out.println(JSON.toJSON(list));
	}

	public void getSubCategorys() {
		GetCategory request = new GetCategory();
		request.setTag(1);
		request.setCategoryId(149);
		List<Category> list = service.getSubCategorys(request);
		System.out.println(JSON.toJSON(list));
	}

	@Test
	public void getCategorysAndVideos() {
		GetCategory request = new GetCategory();
		request.setTag(1);
		List<Category> list = service.getCategorysAndVideos(request,3);
		System.out.println(JSON.toJSON(list));
	}
}
