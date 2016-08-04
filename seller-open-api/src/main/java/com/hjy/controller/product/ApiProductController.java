package com.hjy.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjy.response.product.ResponseProduct;
import com.hjy.service.product.IApiProductService;

@Controller
@RequestMapping("openapi/product/")
public class ApiProductController {

	@Autowired
	private IApiProductService service;

	@RequestMapping("test_index")
	public String testTest() {
		return "jsp/openapi/api_test";
	}

	@RequestMapping("addProduct")
	@ResponseBody
	public ResponseProduct addProduct(String request) {
		return service.addProduct(request);
	}
}
