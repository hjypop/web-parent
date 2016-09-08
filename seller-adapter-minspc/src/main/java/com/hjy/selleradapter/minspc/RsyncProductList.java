package com.hjy.selleradapter.minspc;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.Inject;
import com.hjy.constant.MemberConst;
import com.hjy.dto.response.product.Product;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.service.IMinspcProductService;

public class RsyncProductList extends RsyncMinspc {

	@Inject
	private IMinspcProductService productService;
	
	public JSONObject doProcess(String responseJson) {
		JSONObject result = new JSONObject();
		// 解析请求数据
		List<Product> productList = null;
		try {
			productList = JSON.parseArray(responseJson, Product.class);
		} catch (Exception e) {
			result.put("code", 1);
			result.put("desc", "响应参数错误，请求数据解析异常");
			return result; 
		}
		if(productList == null || productList.size() == 0){
			result.put("code", 1);
			result.put("desc", "响应数据为空");
			return result; 
		}
		
		// 开始清洗数据  响应数据报文与惠家有表实体报文转换。
		List<PcProductinfo> list = productService.productConvertion(productList);
		for(PcProductinfo e : list){
			PcProductinfo i = new PcProductinfo();
			i.setProductCodeOld("minspc-" + e.getProductCode());
			i.setSellerCode(MemberConst.MANAGE_CODE_HOMEHAS); 
			List<PcProductinfo> pList = productService.getListBySellerCode(i); 
			if (pList == null || pList.size() < 1) { // 若果不存在，就添加
				productService.insertProductToTables(e);
			}else{
				productService.updateProductInTables(e);
			}
		}  // 循环同步数据结束
		
		return result;
	}
	
	
	
	public String getRequestMethod() {
		return "Subscribe.ProductList";
	}

	
	//	Status：-1 （当前有库存的全部订阅产品列表）
	//	Status：0 （当前有库存的保税贸易订阅产品列表）
	//	Status：1 （当前有库存的海外直邮订阅产品列表）
	//	Status：2 （当前有库存的一般贸易订阅产品列表）
	public String setRequestDataJson() {
		return "{\"status\":\"-1\"}";
	}



}

























