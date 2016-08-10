package com.hjy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.hjy.request.Request;
import com.hjy.service.product.IApiProductService;

/**
 * 
 * 类: ApiBaseController <br>
 * 描述: api基类 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月10日 上午11:33:59
 */
@Controller
public class ApiController {
	@Autowired
	private IApiProductService productService;

	@RequestMapping("openapi")
	public JSONObject requestApi(Request request) {
		JSONObject result = new JSONObject();
		boolean flag = isSign(request);
		/*
		 * 如果签名正确，根据method调用不同的service
		 */
		if (flag) {
			try {
				String[] methods = request.getMethod().split(".");
				String type = methods[0];
				String method = methods[1];
				if ("Product".equals(type)) {
					if ("addProduct".equals(method)) {
						result = productService.addProduct(request.getData());
					} else if ("editProduct".equals(method)) {
						result = productService.editProduct(request.getData());
					} else if ("batchProducts".equals(method)) {
						result = productService.batchProducts(request.getData());
					} else if ("batchProductsPrice".equals(method)) {
						result = productService.batchProductsPrice(request.getData());
					} else if ("batchProductsSkuStore".equals(method)) {
						result = productService.batchProductsSkuStore(request.getData());
					}
				} else if ("Order".equals(type)) {

				}
			} catch (Exception e) {
				e.printStackTrace();
				/*
				 * 返回方法名错误信息
				 */
			}
		} else {

		}
		return result;
	}

	/**
	 * 
	 * 方法: isSign <br>
	 * 描述: 验证签名是否正确 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月10日 下午5:43:01
	 * 
	 * @param request
	 * @return
	 */
	private static boolean isSign(Request request) {
		boolean flag = false;
		return flag;
	}
}
