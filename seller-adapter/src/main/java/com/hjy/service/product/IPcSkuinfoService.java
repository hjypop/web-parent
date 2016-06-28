package com.hjy.service.product;

import com.hjy.entity.product.PcSkuinfo;
import com.hjy.service.IBaseService;

/**
 * 
 * 类: IPcSkuinfoService <br>
 * 描述: 产品表业务处理接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 上午11:13:32
 */
public interface IPcSkuinfoService extends IBaseService<PcSkuinfo, Integer> {

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
