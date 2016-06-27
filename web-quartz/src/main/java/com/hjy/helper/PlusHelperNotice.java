package com.hjy.helper;

import java.util.List;

import org.apache.commons.lang.StringUtils;



/**
 * 通知相关调用类
 * 
 * @author srnpr
 * 
 */
public class PlusHelperNotice {

	/**
	 * 当修改商品或者SKU的信息时调用此方法逻辑                         TODO  此处需要等待Redis工程完成，然后打开
	 * 
	 * @param sProductCode
	 * @return
	 */
	public static boolean onChangeProductInfo(String sProductCode) {

		// 循环删除所有商品下关联的子活动
//		for (String sKey : XmasKv.upFactory(EKvSchema.ProductIcChildren)
//				.hgetAll(sProductCode).keySet()) {
//			XmasKv.upFactory(EKvSchema.IcSku).del(sKey);
//		}
//
//		for (MDataMap mDataMap : DbUp.upTable("pc_skuinfo").queryAll(
//				"sku_code", "", "", new MDataMap("product_code", sProductCode))) {
//			String sSkuCode = mDataMap.get("sku_code");
//			new LoadSkuInfo().deleteInfoByCode(sSkuCode);
//			onChangeSkuStock(sSkuCode);
//
//		}
//
//		XmasKv.upFactory(EKvSchema.Product).del(sProductCode);
//		XmasKv.upFactory(EKvSchema.ProductSku).del(sProductCode);
//		XmasKv.upFactory(EKvSchema.ProductSales).del(sProductCode);		//刷新销量缓存

		return true;
	}

}
