package com.drwljrtv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
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
		JSONArray array = service.getCategorys(request);
		System.out.println(array);
	}

	@Test
	public void getSubCategorys() {
		GetCategory request = new GetCategory();
		request.setTag(1);
		request.setCategoryId(58);
		JSONArray array = service.getSubCategorys(request);
		System.out.println(array);
	}
}
