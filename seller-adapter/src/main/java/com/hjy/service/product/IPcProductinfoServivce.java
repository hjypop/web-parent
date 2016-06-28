package com.hjy.service.product;

import java.util.List;

import com.hjy.entity.product.PcProductinfo;

/**
 * 
 * 类: IPcProductinfoServivce <br>
 * 描述: 商品信息业务逻辑处理接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 上午10:05:39
 */
public interface IPcProductinfoServivce {

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
