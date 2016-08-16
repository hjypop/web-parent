package com.hjy.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hjy.entity.log.LcOpenApiOperation;
import com.hjy.entity.webcore.OpenApiAppid;
import com.hjy.helper.SignHelper;
import com.hjy.request.Request;
import com.hjy.service.appid.IApiOpenApiAppidService;
import com.hjy.service.operation.IApiLcOpenApiOperationService;
import com.hjy.service.order.IApiOcOrderInfoService;
import com.hjy.service.product.IApiProductService;
import com.hjy.service.shipment.IApiOcOrderShipmentsService;

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
	@Autowired
	private IApiOcOrderInfoService service;
	@Autowired
	private IApiLcOpenApiOperationService logService;
	@Autowired
	private IApiOcOrderShipmentsService ocOrderShipmentsService;

	@Autowired
	private IApiOpenApiAppidService appidService;

	@RequestMapping("openapi")
	@ResponseBody
	public JSONObject requestApi(Request request) {

		// request = DataInit.apiInsertShipmentsTest();

		JSONObject result = new JSONObject();
		OpenApiAppid info = appidService.findByAppid(new OpenApiAppid(request.getAppid(), request.getAppSecret()));
		if (null == info) {
			result.put("code", 3);
			result.put("desc", "无API访问权限，错误的appid或appSecret");
			return result;
		}

		if (isSign(request)) { // 如果签名正确，根据method调用不同的service
			String sellerCode = info.getSellerCode();
			LcOpenApiOperation log = new LcOpenApiOperation();
			log.setApiName(request.getMethod());
			log.setCreateTime(new Date());
			log.setRequestJson(request.getData());
			log.setRequestTime(new Date());
			log.setSellerCode(info.getSellerCode());
			try {
				String[] methods = request.getMethod().split("\\.");
				String type = methods[0];
				String method = methods[1];
				if ("Product".equals(type)) {
					if ("addProduct".equals(method)) {
						log.setClassUrl("com.hjy.service.product.IApiProductService.addProduct");
						log.setRemark(request.getMethod());
						result = productService.addProduct(request.getData(), sellerCode);
					} else if ("editProduct".equals(method)) {
						log.setClassUrl("com.hjy.service.product.IApiProductService.editProduct");
						log.setRemark(request.getMethod());
						result = productService.editProduct(request.getData(), sellerCode);
					} else if ("batchProducts".equals(method)) {
						log.setClassUrl("com.hjy.service.product.IApiProductService.batchProducts");
						log.setRemark(request.getMethod());
						result = productService.batchProducts(request.getData(), sellerCode);
					} else if ("batchProductsPrice".equals(method)) {
						log.setClassUrl("com.hjy.service.product.IApiProductService.batchProductsPrice");
						log.setRemark(request.getMethod());
						result = productService.batchProductsPrice(request.getData(), sellerCode);
					} else if ("batchProductsSkuStore".equals(method)) {
						log.setClassUrl("com.hjy.service.product.IApiProductService.batchProductsSkuStore");
						log.setRemark(request.getMethod());
						result = productService.batchProductsSkuStore(request.getData(), sellerCode);
					}
				} else if ("Order".equals(type)) {
					if ("List".equals(method)) { // 根据传入的json串查询订单信息 - Yangcl
						log.setClassUrl("ApiOcOrderInfoServiceImpl.getOrderInfoByJson");
						log.setRemark("remark");
						result = service.getOrderInfoByJson(request.getData(), sellerCode);
					} else if ("UpdateOrderStatus".equals(method)) {
						// 订单变更：更新订单状态信息 - Yangcl
						log.setClassUrl("ApiOcOrderInfoServiceImpl.updateOrderStatus");
						log.setRemark("remark");
						result = service.updateOrderStatus(request.getData(), sellerCode);
					} else if ("Shipments".equals(method)) {
						// 订单物流变更：根据传入的json串插入物流信息 - Yangcl
						log.setClassUrl("ApiOcOrderShipmentsServiceImpl.apiInsertShipments");
						log.setRemark("remark");
						result = ocOrderShipmentsService.apiInsertShipments(request.getData(), sellerCode);
						return result;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				/*
				 * 返回方法名错误信息
				 */
				result.put("code", "99");
				result.put("desc", "调用接口失败，请联系开发人员");
				log.setRemark(e.getMessage());
			} finally {
				log.setResponseJson(result.toJSONString());
				log.setResponseTime(new Date());
				logService.insertSelective(log);
			}
		} else {
			result.put("code", "-1");
			result.put("desc", "数字签名错误");
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
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("appid", request.getAppid());
			map.put("data", URLEncoder.encode(request.getData(), "UTF-8"));
			map.put("method", request.getMethod());
			map.put("timestamp", request.getTimestamp());
			map.put("nonce", request.getNonce());
			List<String> list = new ArrayList<String>();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (entry.getValue() != "") {
					list.add(entry.getKey() + "=" + entry.getValue() + "&");
				}
			}
			Collections.sort(list); // 对List内容进行排序
			StringBuffer str = new StringBuffer();
			for (String nameString : list) {
				str.append(nameString);
			}
			str.append(request.getAppSecret());
			System.out.println(str.toString());
			String sign = SignHelper.md5Sign(str.toString());
			if (sign.equals(request.getSign())) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
