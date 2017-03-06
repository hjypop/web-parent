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
import com.hjy.helper.RedisHelper;
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
					boolean flag = new RedisHelper().reloadProductInRedis(pcode);  // 删除redis中的缓存信息 
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

}
