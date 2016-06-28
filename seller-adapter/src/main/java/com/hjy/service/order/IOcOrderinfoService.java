package com.hjy.service.order;

import com.hjy.entity.order.OcOrderinfo;
import com.hjy.service.IBaseService;

/**
 * 
 * 类: IOcOrderinfoService <br>
 * 描述: 订单信息表业务逻辑处理接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午6:15:42
 */
public interface IOcOrderinfoService extends IBaseService<OcOrderinfo, Integer>{
	/**
	 * 
	 * 方法: findOrderInfoByOrderCode <br>
	 * 描述: 根据订单编号查询订单信息 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 下午6:22:18
	 * 
	 * @param orderCode
	 * @return
	 */
	OcOrderinfo findOrderInfoByOrderCode(String orderCode);
}
