package com.hjy.system.init;

import com.hjy.system.OpenApiEcacheSupport;

/**
 * @description: 将wc_sellerinfo|wc_openapi|wc_seller_api 三个表中的信息缓存化
 * 
 * @author Yangcl
 * @date 2016年12月22日 下午3:48:15 
 * @version 1.0.0
 */
public class OpenApiSystemInit extends RootInit{

	@Override
	public boolean onInit() {
		return openApiInit();
	}

	@Override
	public boolean onDestory() {
		return false;
	}

	/**
	 * @description: 将wc_sellerinfo|wc_openapi|wc_seller_api 三个表中的信息缓存化 
	 * 
	 * @author Yangcl 
	 * @date 2016年12月22日 下午4:30:46 
	 * @version 1.0.0.1
	 */
	private boolean openApiInit(){
		boolean flag = true;
		this.topInitCache(new OpenApiEcacheSupport());  
		return flag;
	}
	
	/**
	 * @description: 将缓存信息从ecache中删除
	 * 
	 * @return
	 * @author Yangcl 
	 * @date 2016年12月22日 下午4:31:05 
	 * @version 1.0.0.1
	 */
	private boolean openApiDelete(){
		boolean flag = true;
		return flag;
	}
}



















