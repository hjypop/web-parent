package com.hjy.dao.order;

import com.hjy.dao.BaseDao;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.request.data.OrderInfoRequest;
import com.hjy.response.data.OrderInfoResponse;

/**
 * 
 * 类: IOcOrderinfoDao <br>
 * 描述: 订单信息表数据库访问接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午6:16:08
 */
public interface IOcOrderinfoDao extends BaseDao<OcOrderinfo, Integer>{

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

	public Integer countByOrderCode(OcOrderinfo entity); 
	
	public OrderInfoResponse getOpenApiOrderinfoList(OrderInfoRequest dto);
	
	
}

















