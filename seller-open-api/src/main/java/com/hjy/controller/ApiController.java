package com.hjy.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hjy.entity.log.LcOpenApiOperation;
import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.helper.PureNetUtil;
import com.hjy.helper.SignHelper;
import com.hjy.request.Request;
import com.hjy.service.operation.IApiLcOpenApiOperationService;
import com.hjy.service.order.IApiOcOrderInfoService;
import com.hjy.service.product.IApiProductService;
import com.hjy.service.shipment.IApiOcOrderShipmentsService;
import com.hjy.service.webcore.IWcSellerinfoService;
import com.hjy.system.ApiCacheVisitor;
import com.hjy.system.cmodel.CacheWcOpenapi;
import com.hjy.system.cmodel.CacheWcSellerInfo;

/**
 * 
 * 类: ApiBaseController <br>
 * 描述: api基类 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月10日 上午11:33:59
 */
@Controller
public class ApiController {
	private static Logger logger=Logger.getLogger(ApiController.class);
	
	@Autowired
	private IApiProductService productService;
	@Autowired
	private IApiOcOrderInfoService service;
	@Autowired
	private IApiLcOpenApiOperationService logService;
	@Autowired
	private IApiOcOrderShipmentsService ocOrderShipmentsService;


	@RequestMapping("openapi")
	@ResponseBody
	public JSONObject requestApi(Request request) {
		JSONObject result = new JSONObject();

//		request = DataInit.pushProduct();
//		System.out.println(JSONObject.toJSONString(request));
//		logger.info(JSONObject.toJSONString(request));

		
		CacheWcSellerInfo seller = JSONObject.parseObject(ApiCacheVisitor.find(request.getAppid()) , CacheWcSellerInfo.class); // 取出缓存中的商户信息
		if (null == seller) {
			result.put("code", 3);
			result.put("desc", "无API访问权限，错误的appid");
			return result;
		}
		boolean flag = true;
		String apiCode = "";
		String target = request.getMethod();
		for(int i = 0 ; i < seller.getApis().size() ; i ++){
			if(target.equals(seller.getApis().get(i).getMethod())){
				flag = false;
				apiCode = seller.getApis().get(i).getApiCode();
				break;
			}
		}
		if(flag){
			result.put("code", 3);
			result.put("desc", "无API访问权限，您所请求的方法未授权! 请联系惠家有平台接口管理人员。接口名称：【" + target + "】");
			return result;
		}
		// 该接口还必须可用状态，否则提示信息  
		CacheWcOpenapi api = JSONObject.parseObject(ApiCacheVisitor.find(apiCode) , CacheWcOpenapi.class); 
		if(api == null || api.getStatus() != 1){
			result.put("code", 3);
			result.put("desc", "api尚未开通或已禁用! 请联系惠家有平台接口管理人员。接口名称：【" + target + "】");
			return result;
		}
		
		
		request.setAppSecret(seller.getUid());
		
		if (isSign(request)) { // 如果签名正确，根据method调用不同的service
			String sellerCode = seller.getSellerCode();
			LcOpenApiOperation log = new LcOpenApiOperation();
			log.setApiName(request.getMethod());
			log.setCreateTime(new Date());
			log.setRequestJson(JSONObject.toJSONString(request));
			log.setRequestTime(new Date());
			log.setSellerCode(sellerCode);
			
			try {
				String[] methods = request.getMethod().split("\\.");
				String type = methods[0];
				String method = methods[1];
				if ("Product".equals(type)) {
					
					// TODO 批量同步商品 syncSellerProductList  &&  syncPlatformProductList   
					
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
					} else if ("pushProduct".equals(method)) {
						log.setClassUrl("com.hjy.service.product.IApiProductService.pushProduct");
						log.setRemark(request.getMethod());
						// 推送产品到第三方
						if (StringUtils.isNoneBlank(request.getData())) {
							JSONObject obj = JSONObject.parseObject(request.getData());
							// 判断开始日期和结束日期是否为空
							if (obj != null && StringUtils.isNotBlank(obj.getString("startDate")) && StringUtils.isNotBlank(obj.getString("endDate"))) {
								// 获取产品列表
								result = productService.pushProduct(seller, obj.getString("startDate") , obj.getString("endDate"));
							} else {
								result.put("code", 3);
								result.put("desc", "接口参数错误");
								log.setRemark(result.toJSONString());
							}
						} else {
							result.put("code", 3);
							result.put("desc", "接口参数错误");
							log.setRemark(result.toJSONString());
						}
						return result;

					} else if ("pushSkuStock".equals(method)) {
						log.setClassUrl("com.hjy.service.product.IApiProductService.pushSkuStock");
						log.setRemark(request.getMethod());
						/**
						 * 根据productcode数组集合查询sku库存，推送sku库存到第三方
						 */
						JSONObject obj = JSONObject.parseObject(request.getData());
						result = productService.pushSkuStock(seller, obj.getString("productCodes"));
					} else if ("pushProductPrice".equals(method)) {
						log.setClassUrl("com.hjy.service.product.IApiProductService.pushProductPrice");
						log.setRemark(request.getMethod());
						// 推送商品和sku价格到第三方
						JSONObject obj = JSONObject.parseObject(request.getData());
						result = productService.pushProductPrice(seller, obj.getString("productCodes"));
					} else if ("RsyncProductStatus".equals(method)) {
						// 同步商品上下架状态 - Yangcl
						log.setClassUrl("com.hjy.service.impl.product.ApiProductServiceImpl.rsyncProductStatus");
						log.setRemark(request.getMethod());
						result = productService.rsyncProductStatus(request.getData(), seller);
					}
					//开发根据商品编号数组获取商品信息
					else if("pushProductByCodes".equals(method)){
						//根据商品编码数组查询商品信息
						log.setClassUrl("com.hjy.service.impl.product.ApiProductServiceImpl.findProductByProductCodes");
						log.setRemark(request.getMethod());
						result = productService.findProductByProductCodes(seller, request.getData());						
					}
				} else if ("Order".equals(type)) {
					if ("List".equals(method)) { // 根据传入的json串查询订单信息 - Yangcl
						log.setClassUrl("ApiOcOrderInfoServiceImpl.getOrderInfoByJson");
						log.setRemark(request.getMethod());
						result = service.getOrderInfoByJson(request.getData(), sellerCode);
					} else if ("UpdateOrderStatus".equals(method)) {
						// 订单变更：更新订单状态信息 - Yangcl
						log.setClassUrl("ApiOcOrderInfoServiceImpl.updateOrderStatus");
						log.setRemark(request.getMethod());
						result = service.updateOrderStatus(request.getData(), sellerCode);
					} else if ("Shipments".equals(method)) {
						// 订单物流变更：根据传入的json串插入物流信息 - Yangcl
						log.setClassUrl("ApiOcOrderShipmentsServiceImpl.apiInsertShipments");
						log.setRemark(request.getMethod());
						result = ocOrderShipmentsService.apiInsertShipments(request.getData(), sellerCode);
						return result;
					} else if ("Insert".equals(method)) {
						// 批量插入订单状态信息，第三方将订单信息发送给惠家有，惠家有插入订单 - Yangcl
						log.setClassUrl("ApiOcOrderInfoServiceImpl.insertOrder");
						log.setRemark(request.getMethod());
						result = service.insertOrder(request.getData(), sellerCode);
						return result;
					} else if ("ShipmentQuery".equals(method)) {
						// 查询物流信息，惠家有将物流信息发送给第三方，惠家有查询物流 - Yangcl
						log.setClassUrl("ApiOcOrderShipmentsServiceImpl.apiSelectShipments");
						log.setRemark(request.getMethod());
						result = ocOrderShipmentsService.apiSelectShipments(request.getData(), sellerCode);
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
