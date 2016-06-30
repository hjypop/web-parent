package com.hjy.dao;

import java.util.List;

import com.hjy.entity.OcOrderKjtDetail;

/**
 * 
 * 类: IOcOrderKjtDetailDao <br>
 * 描述: 跨境通订单明细表数据库访问接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午5:20:30
 */
public interface IOcOrderKjtDetailDao extends BaseDao<OcOrderKjtDetail, Integer> {

	/**
	 * 
	 * 方法: findOrderDetailByCodeSeq <br>
	 * 描述: 根据发货单号查询订单明细 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月30日 上午10:00:36
	 * 
	 * @param orderCodeSeq
	 * @return
	 */
	List<OcOrderKjtDetail> findOrderDetailByCodeSeq(String orderCodeSeq);
}
