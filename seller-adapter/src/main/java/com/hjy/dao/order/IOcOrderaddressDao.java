package com.hjy.dao.order;

import com.hjy.dao.BaseDao;
import com.hjy.entity.order.OcOrderaddress;

/**
 * 
 * 类: IOcOrderadressDao <br>
 * 描述: 订单地址发票表数据库访问接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午5:48:07
 */
public interface IOcOrderaddressDao extends BaseDao<OcOrderaddress, Integer> {

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
