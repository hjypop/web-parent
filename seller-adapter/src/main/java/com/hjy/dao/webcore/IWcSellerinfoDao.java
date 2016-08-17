package com.hjy.dao.webcore;


import com.hjy.dao.BaseDao;
import com.hjy.entity.webcore.WcSellerinfo;

/**
 * 
 * 类: IWcSellerinfoDao <br>
 * 描述: 商家信息表数据库访问接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月17日 上午11:37:49
 */
public interface IWcSellerinfoDao extends BaseDao<WcSellerinfo, Integer> {

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
