package com.hjy.iface;
/**
 * @descriptions 商户提供的同步接口信息
 * 
 * @date 2016年6月24日下午2:52:15
 * @author Yangcl
 * @version 1.0.1
 */
public interface IRsyncMerchantApi {
	/**
	 * @descriptions 返回商户提供的 同步接口 名称
	 * 例如："Product.ProductIDGetQuery"
	 * @return 
	 * @date 2016年6月24日下午2:53:33
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public String getRsyncApiName();
}
