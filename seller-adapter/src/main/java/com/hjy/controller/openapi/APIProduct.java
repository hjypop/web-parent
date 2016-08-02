package com.hjy.controller.openapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hjy.service.openapi.IAPIProductService;

@Controller
@RequestMapping("/api/product/")
public class APIProduct {

	@Autowired
	private IAPIProductService product;

	@RequestMapping("test")
	public String testIndex() {
		return "api_test";
	}

	@RequestMapping("add_product")
	@ResponseBody
	public String addProduct(String request) {
		System.out.println("---->"+request);
		System.out.println(JSON.toJSONString(product.addProduct(request)));
		return JSON.toJSONString(product.addProduct(request));
	}
}
