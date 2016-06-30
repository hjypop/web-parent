package com.hjy.service;

import java.util.List;

import com.hjy.entity.OcOrderKjtDetail;

/**
 * 
 * 类: IOcOrderKjtDetailService <br>
 * 描述: 跨境通订单明细表业务处理接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午5:20:51
 */
public interface IOcOrderKjtDetailService extends IBaseService<OcOrderKjtDetail, Integer> {
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
