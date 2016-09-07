package com.hjy.dao.order;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.request.data.MinspcOrderinfoRequest;
import com.hjy.request.data.OrderInfoRequestDto;
import com.hjy.request.data.OrderInfoStatusDto;
import com.hjy.response.MinspcOrderinfoResponse;
import com.hjy.response.OrderInfoResponse;

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
	
	/**
	 * @descriptions 根据small_seller_code返回订单详细信息列表。seller-open-api项目中使用
	 * 
	 * @param dto 
	 * @date 2016年8月4日下午3:12:57
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public List<OrderInfoResponse> getOpenApiOrderinfoList(OrderInfoRequestDto dto);
	
	
	/**
	 * @descriptions 根据small_seller_code、开始时间和结束时间返回订单详细信息列表。seller-adapter-minspc项目中使用
	 * 
	 * @param dto 
	 * @date 2016年8月4日下午3:12:57
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public List<MinspcOrderinfoResponse> getMinspcOrderinfoList(MinspcOrderinfoRequest dto);
	
	
	/**
	 * @descriptions 根据order_code  small_seller_code更新订单状态。seller-open-api项目中使用
	 * 	
	 * @param list 
	 * @date 2016年8月5日下午4:49:17
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public Integer apiUpdateOrderinfoStatus(OrderInfoStatusDto dto);
	
	/**
	 * @descriptions 根据order_code 和 small_seller_code 获取一条记录信息
	 * 
	 * @date 2016年8月10日下午2:01:52
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public OcOrderinfo getOrderInfoByCode(OcOrderinfo info);
	
	/**
	 * @descriptions 批量插入
	 * 
	 * @param list
	 * @return 
	 * @date 2016年8月29日下午3:59:16
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public Integer apiBatchInsert(List<OcOrderinfo> list);
}

















