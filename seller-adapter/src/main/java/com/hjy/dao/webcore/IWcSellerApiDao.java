package com.hjy.dao.webcore;

import com.hjy.dao.BaseDao;
import com.hjy.entity.webcore.WcSellerApi;

/**
 * 
 * 类: IWcSellerApiDao <br>
 * 描述: 商户接口关系表数据库访问类 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月25日 上午9:14:53
 */
public interface IWcSellerApiDao extends BaseDao<WcSellerApi, Integer> {

	/**
	 * 
	 * 方法: deleteBySellerCode <br>
	 * 描述: 根据商户编号删除已开通的接口 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月25日 上午9:38:46
	 * 
	 * @param sellerCode
	 * @return
	 */
	int deleteBySellerCode(String sellerCode);
}
