package com.hjy.dao.order;

import java.util.List;
import java.util.Map;

import com.hjy.dao.BaseDao;
import com.hjy.dto.KjtProductInfo;
import com.hjy.dto.minspc.MinspcOrderdetailOne;
import com.hjy.model.order.OrderDetail;

public interface IOcOrderdetailDao extends BaseDao<OrderDetail , Integer> {

	/**
	 * @descriptions 批量插入
	 * 
	 * @param list
	 * @return 
	 * @date 2016年8月29日下午3:59:16
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public Integer apiBatchInsert(List<OrderDetail> list);
	
	
	public List<KjtProductInfo>  findKjtProductInfo(Map<String , String> map);
	
	/**
	 * @description: com.hjy.job.JobForCreateSubscribeOrder.java 使用
	 * 	|拼装请求数据的商品列表部分|seller-adapter-minspc(民生品粹项目)
	 * 
	 * @author Yangcl
	 * @date 2016年9月6日 下午2:24:05 
	 */
	public List<MinspcOrderdetailOne> getMinspcOrderdetailOneList(String orderCode);
}













