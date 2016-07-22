package com.hjy.service;

import java.util.List;

import com.hjy.entity.OcOrderKjtList;

/**
 * 
 * 类: IOcOrderKjtListService <br>
 * 描述: 跨境通订单业务逻辑处理实现接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午4:42:21
 */
public interface IOcOrderKjtListService extends IBaseService<OcOrderKjtList, Integer> {
	/**
	 * 
	 * 方法: findOrderListByStatus <br>
	 * 描述: 根据订单状态查询跨境通订单列表 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 下午5:14:02
	 * 
	 * @param sostatus
	 *            订单状态
	 * @return
	 */
	List<OcOrderKjtList> findOrderListByStatus(String sostatus);

	/**
	 * 
	 * 方法: findOrderByOutCode <br>
	 * 描述: 根据外部订单号查询订单信息 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 下午5:25:49
	 * 
	 * @param orderCodeOut
	 * @return
	 */
	OcOrderKjtList findOrderByOutCode(String orderCodeOut);

	/**
	 * 
	 * 方法: findLocalStatusByOrderCode <br>
	 * 描述: 根据订单编号查询本地订单状态 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月30日 上午8:55:11
	 * 
	 * @param orderCode
	 * @return
	 */
	List<String> findLocalStatusByOrderCode(String orderCode);

	/**
	 * 根据序列订单号修改订单编号 2016-07-02 zhy
	 * 
	 * @param entity
	 * @return
	 */
	int updateCodeByCodeSeq(OcOrderKjtList entity);

	/**
	 * 根据序列订单号查询订单信息 2016-07-03 zhy
	 * 
	 * @param orderCodeSeq
	 * @return
	 */
	OcOrderKjtList findOrderListByCodeSeq(String orderCodeSeq);

	/**
	 * 根据订单编号查询订单集合 2016-07-03 zhy
	 * 
	 * @param orderCode
	 * @return
	 */
	List<OcOrderKjtList> findListByOrderCode(String orderCode);

	/**
	 * 根据外部订单号修改数据信息 2016-07-22 zhy
	 * 
	 * @param entity
	 * @return
	 */
	int updateSelectiveByCodeOut(OcOrderKjtList entity);
}
