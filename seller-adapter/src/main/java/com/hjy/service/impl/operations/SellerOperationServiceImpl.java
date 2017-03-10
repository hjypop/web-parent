package com.hjy.service.impl.operations;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.product.IPcProductAuthorityLogoDao;
import com.hjy.dao.product.IPcProductinfoDao;
import com.hjy.dao.product.IPcSkuinfoDao;
import com.hjy.entity.product.PcProductAuthorityLogo;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.helper.RedisHelper;
import com.hjy.service.operations.ISellerOperationService;


@Service("sellerOperationService")
public class SellerOperationServiceImpl implements ISellerOperationService {

	@Resource
	private IPcProductinfoDao pcProductinfoDao;
	@Resource
	private IPcSkuinfoDao pcSkuinfoDao;
	@Resource
	private IPcProductAuthorityLogoDao palDao;
	
	
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

	
	public JSONObject funcTwo(String json, HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("seller-operation-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
//			List<PcProductAuthorityLogo> list = palDao.getAllInformation();
//			if(list != null){
//				for(PcProductAuthorityLogo l : list){
//					new RedisHelper().deleteProductAuthorityLogo(l.getProductCode());
//				}
//			
//				return null;
//			}
			List<String> pcodeList = JSONObject.parseArray(json, String.class);
			if(pcodeList != null){
				for(String s : pcodeList){
					new RedisHelper().deleteProductAuthorityLogo(s);
				} 
			}
			
			for(String s : pcodeList){
				new RedisHelper().deleteProductAuthorityLogo(s);
				
				PcProductAuthorityLogo e = new PcProductAuthorityLogo();
				e.setUid(UUID.randomUUID().toString().replace("-", ""));
				e.setProductCode(s);
				e.setAuthorityLogoUid("abcdefghijklmn0002");   // 不支持7天退换货
				e.setCreateTime(sdf.format(new Date()));
				palDao.insertSelective(e);
			}
			
			/*List<PcProductinfo> zyList = pcProductinfoDao.findZyProductList(pcodeList);
			for(PcProductinfo i : zyList){ 
				PcProductAuthorityLogo e = new PcProductAuthorityLogo();
				e.setUid(UUID.randomUUID().toString().replace("-", ""));
				e.setProductCode(i.getProductCode());
				e.setAuthorityLogoUid("abcdefghijklmn0002");  // 支持7天退换货  
				e.setCreateTime(sdf.format(new Date()));
				palDao.insertSelective(e);
			}*/
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
	
	

	public JSONObject funcTwo222222222(String json, HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("seller-operation-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			List<String> pcodeList = JSONObject.parseArray(json, String.class);
			for(String s : pcodeList){
				PcProductAuthorityLogo e = new PcProductAuthorityLogo();
				e.setUid(UUID.randomUUID().toString().replace("-", ""));
				e.setProductCode(s);
				e.setAuthorityLogoUid("abcdefghijklmn0001");
				e.setCreateTime(sdf.format(new Date()));
				palDao.insertSelective(e);
			}
			
			List<PcProductinfo> list = pcProductinfoDao.findKjProductList();
			for(PcProductinfo i : list){
				PcProductAuthorityLogo e = new PcProductAuthorityLogo();
				e.setUid(UUID.randomUUID().toString().replace("-", ""));
				e.setProductCode(i.getProductCode());
				e.setAuthorityLogoUid("abcdefghijklmn0001");  // 不支持7天退换货
				e.setCreateTime(sdf.format(new Date()));
				palDao.insertSelective(e);
			}
			
			List<PcProductinfo> ldList = pcProductinfoDao.findLdProductList(pcodeList);
			for(PcProductinfo i : ldList){ 
				PcProductAuthorityLogo e = new PcProductAuthorityLogo();
				e.setUid(UUID.randomUUID().toString().replace("-", ""));
				e.setProductCode(i.getProductCode());
				e.setAuthorityLogoUid("abcdefghijklmn0002");  // 支持7天退换货  
				e.setCreateTime(sdf.format(new Date()));
				palDao.insertSelective(e);
			}
			
			
			
			System.out.println("");  
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		return result;
	}

}



















