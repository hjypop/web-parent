package com.hjy.dao.product;

import com.hjy.dao.BaseDao;
import com.hjy.entity.product.PcCategoryinfo;

/**
 * 
 * 类: IPcCategoryinfoDao <br>
 * 描述: 商品分类表数据库访问接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月2日 上午9:04:14
 */
public interface IPcCategoryinfoDao extends BaseDao<PcCategoryinfo, Integer> {

	/**
	 * 
	 * 方法: getPcCategoryinfoByCode <br>
	 * 描述: 根据分类编号查询分类信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月2日 上午9:04:47
	 * 
	 * @param code
	 * @return
	 */
	PcCategoryinfo getPcCategoryinfoByCode(String code);
}
