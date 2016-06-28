package com.hjy.dao.product;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.entity.product.PcProductinfo;

public interface IPcProductinfoDao extends BaseDao<PcProductinfo , Integer> {

	/**
	 * 
	 * 方法: findProductCodeOld <br>
	 * 描述: 根据卖家编号和第三方商户编号,查询旧的商品编号 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 上午7:46:44
	 * @param info
	 * @return
	 */
	List<String> findProductCodeOld(PcProductinfo info);
	
}
