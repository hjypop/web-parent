package com.hjy.dao.user;

import com.hjy.dao.BaseDao;
import com.hjy.entity.user.UcSellerinfo;

public interface IUcSellerinfoDao extends BaseDao<UcSellerinfo, Integer> {

	/**
	 * 
	 * 方法: selectBySmallSellerCode <br>
	 * 描述: 根据商户编码查询商户信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月2日 上午10:04:26
	 * 
	 * @param small_seller_info
	 * @return
	 */
	UcSellerinfo selectBySmallSellerCode(String small_seller_info);
}
