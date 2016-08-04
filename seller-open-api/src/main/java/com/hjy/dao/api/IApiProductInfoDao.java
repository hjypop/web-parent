package com.hjy.dao.api;

import java.util.List;

import com.hjy.entity.product.PcProductinfo;

/**
 * 
 * 类: IProductDao <br>
 * 描述: openapi商品专用数据库访问接口 <br>
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

}
