package com.hjy.dao.product;

import com.hjy.dao.BaseDao;
import com.hjy.entity.product.PcSkuinfo;

/**
 * @descriptions PcSkuinfo xml配置文件中有
 *               "com.cmall.dborm.txmodel.PcSkuinfoWithBLOBs" 请注意修改配置文件再抄
 * 
 * @date 2016年6月27日下午6:05:29
 * @author Yangcl
 * @version 1.0.1
 */
public interface IPcSkuinfoDao extends BaseDao<PcSkuinfo, Integer> {

	/**
	 * 
	 * 方法: findSkuCodeByProductCode <br>
	 * 描述: 根据商品编号查询产品编号 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 上午11:10:27
	 * 
	 * @param productCode
	 * @return
	 */
	String findSkuCodeByProductCode(String productCode);
}
