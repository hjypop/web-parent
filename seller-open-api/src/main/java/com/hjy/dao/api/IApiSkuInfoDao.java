package com.hjy.dao.api;

import java.util.List;

import com.hjy.entity.product.PcSkuinfo;

/**
 * 
 * 类: IApiSkuInfoDao <br>
 * 描述: openapi单独使用的sku数据库访问接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月4日 上午9:19:14
 */
public interface IApiSkuInfoDao {
	/**
	 * 
	 * 方法: batchInsert <br>
	 * 描述: 批量添加sku <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 上午7:38:49
	 * 
	 * @param list
	 * @return
	 */
	int batchInsert(List<PcSkuinfo> list);

	/**
	 * 
	 * 方法: updateSkuBySkuCodeOld <br>
	 * 描述: 根据外部sku编号修改sku信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 上午7:11:46
	 * 
	 * @param entity
	 * @return
	 */
	int updateSkuBySkuCodeOld(PcSkuinfo entity);
}
