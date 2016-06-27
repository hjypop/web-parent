package com.hjy.jms;

import com.hjy.model.MDataMap;

//import com.cmall.systemcenter.enumer.JmsNameEnumer;
//import com.srnpr.zapcom.basemodel.MDataMap;
//import com.srnpr.zapcom.basesupport.WebClientSupport;
//import com.srnpr.zapcom.topdo.TopUp;
//import com.srnpr.zapzero.enumer.EJmsMessageType;
//import com.srnpr.zapzero.support.JmsSupport;

public class ProductJmsSupport {
	
	/**
	 * 改变商品的
	 */
	public final static String ProductJmsTypeName = "ProductChange";
	/**
	 * 改变sku库存的时候，生成静态页面
	 */
	public final static String SkuJmsTypeName = "SkuChange";
	/**
	 * 输入的时候，替换非法字符
	 */
	public final static String IllegalWordsJmsTypeName = "IllegalWords";
	
	/**
	 * 生成静态页面
	 */
	public final static String CmallCache = "CmallCache";
	
	public void onChangeForProductChangePrice(String productCode){
//		JmsSupport.getInstance().sendMessage(ProductJmsSupport.ProductJmsTypeName, productCode, null, EJmsMessageType.Toplic);
		JmsNoticeSupport.INSTANCE.sendToplic(JmsNameEnumer.OnProductChange, productCode, new MDataMap());
	}

	public void onChangeForProductChangeStock(String productCode){
//		JmsSupport.getInstance().sendMessage(ProductJmsSupport.ProductJmsTypeName, productCode, null, EJmsMessageType.Toplic);
		JmsNoticeSupport.INSTANCE.sendToplic(JmsNameEnumer.OnProductChange, productCode, new MDataMap());
	}
	
	public void onChangeForProductChangeAll(String productCode){
//		JmsSupport.getInstance().sendMessage(ProductJmsSupport.ProductJmsTypeName, productCode, null, EJmsMessageType.Toplic);
		/********zhough 20160530 消息队列换成队列模型不在使用订阅模式*********/
		JmsNoticeSupport.INSTANCE.sendQueue(JmsNameEnumer.OnProductUpdate,null,new MDataMap("productCode",productCode));
		//JmsNoticeSupport.INSTANCE.sendToplic(JmsNameEnumer.OnProductChange, productCode, new MDataMap());
		JmsNoticeSupport.INSTANCE.sendToplic(JmsNameEnumer.OnProductStatusChange, productCode, new MDataMap());
		
	}
	
	public void onChangeForSkuChangePrice(String productCode){
//		JmsSupport.getInstance().sendMessage(ProductJmsSupport.SkuJmsTypeName, skuCode, null, EJmsMessageType.Toplic);
		JmsNoticeSupport.INSTANCE.sendToplic(JmsNameEnumer.OnProductChange, productCode, new MDataMap());
	}

	public void onChangeForSkuChangeStock(String productCode){
//		JmsSupport.getInstance().sendMessage(ProductJmsSupport.SkuJmsTypeName, skuCode, null, EJmsMessageType.Toplic);
		JmsNoticeSupport.INSTANCE.sendToplic(JmsNameEnumer.OnProductChange, productCode, new MDataMap());
	}
	
	public void onChangeForSkuChangeAll(String productCode){
//		JmsSupport.getInstance().sendMessage(ProductJmsSupport.SkuJmsTypeName, skuCode, null, EJmsMessageType.Toplic);
		JmsNoticeSupport.INSTANCE.sendToplic(JmsNameEnumer.OnProductChange, productCode, new MDataMap());
	}
	
	public void onChangeProductText(String productCode){
//		JmsSupport.getInstance().sendMessage(ProductJmsSupport.IllegalWordsJmsTypeName, productCode, null, EJmsMessageType.Toplic);
		JmsNoticeSupport.INSTANCE.sendToplic(JmsNameEnumer.OnProductChange, productCode, new MDataMap());
	}
	
	public void OnChangeSku(String jsonData){
		JmsSupport.getInstance().sendMessage(ProductJmsSupport.CmallCache, jsonData, null, EJmsMessageType.Toplic);
	}
	
}
