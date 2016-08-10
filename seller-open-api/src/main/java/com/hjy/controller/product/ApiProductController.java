package com.hjy.controller.product;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hjy.controller.ApiController;
import com.hjy.entity.log.LcOpenApiOperation;
import com.hjy.response.product.ResponseProduct;
import com.hjy.service.operation.IApiLcOpenApiOperationService;
import com.hjy.service.product.IApiProductService;

@Controller
@RequestMapping("openapi/product/")
public class ApiProductController extends ApiController {

	@Autowired
	private IApiProductService service;

	@Autowired
	private IApiLcOpenApiOperationService logService;

	@RequestMapping("test_index")
	public String testTest() {
		return "jsp/openapi/api_product_test";
	}

	/**
	 * 
	 * 方法: addProduct <br>
	 * 描述: 添加单一商品 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月8日 下午5:07:30
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addProduct")
	@ResponseBody
	public ResponseProduct addProduct(String request) {
		LcOpenApiOperation log = new LcOpenApiOperation();
		log.setUid(UUID.randomUUID().toString().replace("-", ""));
		log.setApiName("Product.addproduct");
		log.setClassUrl("com.hjy.controller.product.ApiProductController.addProduct");
		log.setCreateTime(new Date());
		log.setRequestTime(new Date());
		log.setRequestJson(request);
		ResponseProduct response = service.addProduct(request);
		log.setResponseJson(JSON.toJSONString(response));
		log.setResponseTime(new Date());
		logService.insertSelective(log);
		return response;
	}

	/**
	 * 
	 * 方法: editProduct <br>
	 * 描述: 编辑单一商品 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月8日 下午5:07:43
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("editProduct")
	@ResponseBody
	public ResponseProduct editProduct(String request) {
		LcOpenApiOperation log = new LcOpenApiOperation();
		log.setUid(UUID.randomUUID().toString().replace("-", ""));
		log.setApiName("Product.addproduct");
		log.setClassUrl("com.hjy.controller.product.ApiProductController.editProduct");
		log.setCreateTime(new Date());
		log.setRequestTime(new Date());
		log.setRequestJson(request);
		ResponseProduct response = service.editProduct(request);
		log.setResponseJson(JSON.toJSONString(response));
		log.setResponseTime(new Date());
		logService.insertSelective(log);
		return response;
	}

	/**
	 * 
	 * 方法: syncProductList <br>
	 * 描述: 批量同步商品 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月8日 下午5:07:53
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("batchProducts")
	@ResponseBody
	public ResponseProduct syncProductList(String request) {
		LcOpenApiOperation log = new LcOpenApiOperation();
		log.setUid(UUID.randomUUID().toString().replace("-", ""));
		log.setApiName("Product.addproduct");
		log.setClassUrl("com.hjy.controller.product.ApiProductController.syncProductList");
		log.setCreateTime(new Date());
		log.setRequestTime(new Date());
		log.setResponseJson(request);
		ResponseProduct response = service.syncProductList(request);
		log.setRequestJson(JSON.toJSONString(response));
		log.setResponseTime(new Date());
		logService.insertSelective(log);
		return response;
	}

	/**
	 * 
	 * 方法: syncProductPrice <br>
	 * 描述: 批量同步商品价格 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月8日 下午5:08:03
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("batchProductsPrice")
	@ResponseBody
	public ResponseProduct syncProductPrice(String request) {
		LcOpenApiOperation log = new LcOpenApiOperation();
		log.setUid(UUID.randomUUID().toString().replace("-", ""));
		log.setApiName("Product.addproduct");
		log.setClassUrl("com.hjy.controller.product.ApiProductController.syncProductPrice");
		log.setCreateTime(new Date());
		log.setRequestTime(new Date());
		log.setRequestJson(request);
		ResponseProduct response = service.syncProductPrice(request);
		log.setResponseJson(JSON.toJSONString(response));
		log.setResponseTime(new Date());
		logService.insertSelective(log);
		return response;
	}

	/**
	 * 
	 * 方法: syncProductSkuStore <br>
	 * 描述: 批量同步商品SKU库存 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月8日 下午5:08:19
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("batchProductsSkuStore")
	@ResponseBody
	public ResponseProduct syncProductSkuStore(String request) {
		LcOpenApiOperation log = new LcOpenApiOperation();
		log.setUid(UUID.randomUUID().toString().replace("-", ""));
		log.setApiName("Product.addproduct");
		log.setClassUrl("com.hjy.controller.product.ApiProductController.syncProductSkuStore");
		log.setCreateTime(new Date());
		log.setRequestTime(new Date());
		log.setRequestJson(request);
		ResponseProduct response = service.syncSkuStore(request);
		log.setResponseJson(JSON.toJSONString(response));
		log.setResponseTime(new Date());
		logService.insertSelective(log);
		return response;
	}
}
