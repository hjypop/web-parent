package com.hjy.dao.api;

import java.util.List;

import com.hjy.entity.product.PcProductinfo;

/**
 * 
 * 类: IProductDao <br>
 * 描述: openapi单独使用的商品数据库访问接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月4日 上午7:07:22
 */
public interface IApiProductInfoDao {

	/**
	 * 
	 * 方法: batchInsert <br>
	 * 描述: 批量添加商品 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 上午7:38:49
	 * 
	 * @param list
	 * @return
	 */
	int batchInsert(List<PcProductinfo> list);

	/**
	 * 
	 * 方法: updateProductByProductCodeOld <br>
	 * 描述: 根据外部商品编号修改商品信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 上午7:08:21
	 * 
	 * @param entity
	 * @return
	 */
	int updateProductByProductCodeOld(PcProductinfo entity);

	/**
	 * 
	 * 方法: updateProductPrice <br>
	 * 描述: 修改商品价格 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月8日 下午1:41:50
	 * 
	 * @param entity
	 * @return
	 */
	int updateProductPrice(PcProductinfo entity);

	/**
	 * 
	 * 方法: findProductCodeByOutCode <br>
	 * 描述: 根据外部商品编号查询惠家有商品编号 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月8日 上午10:07:09
	 * 
	 * @param productCodeOld
	 * @return
	 */
	String findProductCodeByOutCode(String productCodeOld);

}
