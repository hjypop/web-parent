package com.hjy.service.webcore;

import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.service.IBaseService;

/**
 * 
 * 类: IWcSellerinfoService <br>
 * 描述: 商户信息业务处理接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月17日 上午11:38:45
 */
public interface IWcSellerinfoService extends IBaseService<WcSellerinfo, Integer> {
	/**
	 * 
	 * 方法: selectBySellerCode <br>
	 * 描述: 根据商家编号查询商家信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月17日 下午1:51:18
	 * 
	 * @param sellerCode
	 * @return
	 */
	WcSellerinfo selectBySellerCode(String sellerCode);

	/**
	 * 
	 * 方法: deleteBySellerCode <br>
	 * 描述: 根据商家编号删除商家信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月17日 下午1:56:03
	 * 
	 * @param sellerCode
	 * @return
	 */
	int deleteBySellerCode(String sellerCode);

}