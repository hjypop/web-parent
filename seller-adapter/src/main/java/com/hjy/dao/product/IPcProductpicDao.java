package com.hjy.dao.product;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.entity.product.PcProductpic;
import com.hjy.entity.product.PcProductpicExample;

public interface IPcProductpicDao extends BaseDao<PcProductpic , Integer> {
	/**
	 * @descriptions query("", "", "product_code=:product_code  and (sku_code='' or sku_code is null)", pcPicListMapParam, -1, -1);
	 * 
	 * @param entity
	 * @date 2016年6月28日下午2:51:32
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	 public List<PcProductpic> findListBySkuNull(PcProductpic entity); 
	 
	 int deleteByExample(PcProductpicExample example);
	 
	 /**
	  * @description: 根据productCode删除数据 
	  *
	  * @throws 
	  * @author Yangcl
	  * @date 2016年9月9日 下午6:10:45 
	  * @version 1.0.0.1
	  */
	 public Integer deleteByProductCode(String productCode);
}
