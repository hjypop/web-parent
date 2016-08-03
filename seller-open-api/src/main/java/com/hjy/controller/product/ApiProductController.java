package com.hjy.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjy.response.ResponseAddProduct;
import com.hjy.service.product.IApiProductService;

@Controller
@RequestMapping("/openapi/product/")
public class ApiProductController {

	@Autowired
	private IApiProductService service;

	@RequestMapping("test_index")
	public String testTest() {
		return "jsp/openapi/api_test";
	}

	@RequestMapping("addProduct")
	@ResponseBody
	public ResponseAddProduct addProduct(String product) {
		return service.addProduct(product);
	}
}
