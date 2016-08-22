package com.hjy.helper;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hjy.annotation.Inject;
import com.hjy.base.BaseClass;
import com.hjy.dao.product.IPcSkuinfoDao;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.redis.core.RedisLaunch;
import com.hjy.redis.srnpr.ERedisSchema;



/**
 * 通知相关调用类
 * 
 * @author srnpr
 * 
 */
public class PlusHelperNotice extends BaseClass {

	@Inject
	private IPcSkuinfoDao pcSkuinfoDao;
	
	
	/**
	 * 当修改商品或者SKU的信息时调用此方法逻辑                         TODO  此处需要等待Redis工程完成，然后打开
	 * 
	 * @param sProductCode
	 * @return
	 */
	public boolean onChangeProductInfo(String productCode_) {
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
	/**
	 * 当修改SKU库存时调用
	 * 
	 * @param sSkuCode
	 * @return
	 */
	public  boolean onChangeSkuStock(String sSkuCode) {
//		XmasKv.upFactory(EKvSchema.Stock).del(sSkuCode);
//		XmasKv.upFactory(EKvSchema.SkuStoreStock).del(sSkuCode);
		RedisLaunch.setFactory(ERedisSchema.Stock).del(sSkuCode);
		RedisLaunch.setFactory(ERedisSchema.SkuStoreStock).del(sSkuCode);	 
		return true;
	}

}
