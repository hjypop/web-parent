package com.hjy.service.order;

import com.hjy.entity.order.OcOrderaddress;
import com.hjy.service.IBaseService;

/**
 * 
 * 类: IOcOrderadressService <br>
 * 描述: 订单地址发票表业务处理接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午5:48:02
 */
public interface IOcOrderadressService extends IBaseService<OcOrderaddress, Integer> {
	/**
	 * 
	 * 方法: findOrderAddressByOrderCode <br>
	 * 描述: 根据order_code查询 订单地址发票表 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 下午5:46:47
	 * 
	 * @param orderCode
	 * @return
	 */
	OcOrderaddress findOrderAddressByOrderCode(String orderCode);
}
