package com.hjy.service.product;

import com.hjy.entity.product.PcProductinfoExt;

/**
 * 
 * 类: IPcProductinfoExtService <br>
 * 描述: 商品属性表业务处理接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午2:42:26
 */
public interface IPcProductinfoExtService {

	/**
	 * 
	 * 方法: updatePcProductinfoExt <br>
	 * 描述: 编辑商品属性 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 下午2:42:23
	 * 
	 * @param entity
	 * @return
	 */
	Integer updatePcProductinfoExt(PcProductinfoExt entity);
}
