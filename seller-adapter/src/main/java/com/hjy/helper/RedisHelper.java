package com.hjy.helper;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hjy.annotation.Inject;
import com.hjy.base.BaseClass;
import com.hjy.dao.product.IPcSkuinfoDao;
import com.hjy.dao.system.IScEventItemProductDao;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.entity.system.ScEventItemProduct;
import com.hjy.redis.core.RedisLaunch;
import com.hjy.redis.srnpr.ERedisSchema;


/**
 * @descriptions 操作缓存的相关类
 *
 * @author Yangcl 
 * @date 2017年1月8日 下午8:29:39
 * @version 1.0.1
 */
public class RedisHelper extends BaseClass {

	@Inject
	private IPcSkuinfoDao pcSkuinfoDao;
	@Inject
	private IScEventItemProductDao scEventItemProductDao;
	
	/**
	 * @descriptions 刷新Redis |即：删除Redis中的商品信息、sku信息、促销的Sku信息、销量缓存、库存信息
	 *
	 * @param productCode_ 商品编号 
	 * @date 2017年1月8日 下午8:26:19
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public boolean reloadProductInRedis(String productCode_){
		// 循环删除所有商品下关联的子活动
		for(String key : RedisLaunch.setFactory(ERedisSchema.ProductIcChildren).hgetAll(productCode_).keySet()){
			RedisLaunch.setFactory(ERedisSchema.IcSku).del(key);
		}
		// 删除所有Sku相关信息
		List<PcSkuinfo> skuList = pcSkuinfoDao.findList(new PcSkuinfo(productCode_)); 
		for(PcSkuinfo i : skuList){
			RedisLaunch.setFactory(ERedisSchema.Sku).del(i.getSkuCode()); 
			RedisLaunch.setFactory(ERedisSchema.Stock).del(i.getSkuCode());
			RedisLaunch.setFactory(ERedisSchema.SkuStoreStock).del(i.getSkuCode());
		}
		//  删除促销的Sku信息 
//		for (MDataMap mDataMap : DbUp.upTable("sc_event_item_product").queryAll(
//				"item_code", "", "", new MDataMap("product_code", sProductCode))) {
//			String itemCode = mDataMap.get("item_code");
//			XmasKv.upFactory(EKvSchema.IcSku).del(itemCode);
//			
//		}
		List<ScEventItemProduct> itemList =   scEventItemProductDao.findEntityListByProduct(productCode_);
		if(itemList != null && itemList.size() > 0){
			for(ScEventItemProduct item : itemList){
				RedisLaunch.setFactory(ERedisSchema.IcSku).del(item.getItemCode());   
			}
		}
		
		RedisLaunch.setFactory(ERedisSchema.Product).del(productCode_);
		RedisLaunch.setFactory(ERedisSchema.ProductSku).del(productCode_);
		RedisLaunch.setFactory(ERedisSchema.ProductSales).del(productCode_);		//刷新销量缓存
		return true;
	}
	
 
	/**
	 * @descriptions 当修改SKU库存时调用  
	 *
	 * @param skuCode
	 * @date 2017年1月8日 下午8:28:35
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public  boolean reloadSkuStockInRedis(String skuCode) { 
		RedisLaunch.setFactory(ERedisSchema.Stock).del(skuCode);
		RedisLaunch.setFactory(ERedisSchema.SkuStoreStock).del(skuCode);	 
		return true;
	}
	
	/**
	 * @description: 根据商品编号删除非通路权威标识  xs-ProductAuthorityLogo-code
	 * 
	 * @param pcode
	 * @return
	 * @author Yangcl 
	 * @date 2017年3月9日 下午6:51:45 
	 * @version 1.0.0.1
	 */
	public boolean deleteProductAuthorityLogo(String pcode){
		RedisLaunch.setFactory(ERedisSchema.ProductAuthorityLogo).del(pcode);
		return true;
	}

}























