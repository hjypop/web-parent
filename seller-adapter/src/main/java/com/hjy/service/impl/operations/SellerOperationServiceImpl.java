package com.hjy.service.impl.operations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.product.IPcProductinfoDao;
import com.hjy.dao.product.IPcSkuinfoDao;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.redis.core.RedisLaunch;
import com.hjy.redis.srnpr.ERedisSchema;
import com.hjy.service.operations.ISellerOperationService;


@Service("sellerOperationService")
public class SellerOperationServiceImpl implements ISellerOperationService {

	@Resource
	private IPcProductinfoDao pcProductinfoDao;
	
	@Resource
	private IPcSkuinfoDao pcSkuinfoDao;
	
	
	public JSONObject funcOne(String json , HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("seller-operation-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		try {
			Map<String , List<String>> map = JSONObject.parseObject(json, Map.class);
			for(String key : map.keySet()){              // key 为 smaller_seller_code  
				List<String> vlist = map.get(key);
				for(String str : vlist){
					String pcode = str.split("@")[0];
					BigDecimal taxRate = new BigDecimal(str.split("@")[1]);
					PcProductinfo e = new PcProductinfo();
					e.setSmallSellerCode(key);
					e.setProductCode(pcode);
					e.setTaxRate(taxRate); 
					pcProductinfoDao.updateProductTaxRate(e);
					this.redisDeleteProductInfo(pcode);  // 删除redis中的缓存信息 
				}
			}
			
			result.put("status", "success");
			result.put("desc", "请求执行完成");
		} catch (Exception e) {
			result.put("status", "success");
			result.put("desc", "非法的Json数据");
		}
		return result;
	}

	/**
	 * @descriptions 刷新Redis 
	 * 
	 * @param productCode_ 
	 * @date 2016年8月16日下午1:37:21
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	private boolean redisDeleteProductInfo(String productCode_){
		// 循环删除所有商品下关联的子活动
		for(String key : RedisLaunch.setFactory(ERedisSchema.ProductIcChildren).hgetAll(productCode_).keySet()){
			RedisLaunch.setFactory(ERedisSchema.IcSku).del(key);
		}
		// 删除所有Sku相关信息
		List<PcSkuinfo> skuList = pcSkuinfoDao.findList(new PcSkuinfo(productCode_)); 
		for(PcSkuinfo i : skuList){
			RedisLaunch.setFactory(ERedisSchema.IcSku).del(i.getSkuCode()); 
			RedisLaunch.setFactory(ERedisSchema.Stock).del(i.getSkuCode());
			RedisLaunch.setFactory(ERedisSchema.SkuStoreStock).del(i.getSkuCode());
		}
		RedisLaunch.setFactory(ERedisSchema.Product).del(productCode_);
		RedisLaunch.setFactory(ERedisSchema.ProductSku).del(productCode_);
		RedisLaunch.setFactory(ERedisSchema.ProductSales).del(productCode_);		//刷新销量缓存
		return true;
	}
}
