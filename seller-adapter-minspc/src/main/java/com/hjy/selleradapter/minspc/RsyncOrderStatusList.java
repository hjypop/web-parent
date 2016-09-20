package com.hjy.selleradapter.minspc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.hjy.annotation.Inject;
import com.hjy.dao.order.IOcKjSellerSeparateOrderDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.dto.response.orderStatus.DataResponse;
import com.hjy.dto.response.orderStatus.SoResponse;
import com.hjy.entity.order.OcKjSellerSeparateOrder;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.helper.ExceptionHelper;

public class RsyncOrderStatusList extends RsyncMinspc {
	
	private static Logger logger = Logger.getLogger(RsyncOrderStatusList.class);
	
	@Inject
	private IOcOrderinfoDao orderinfoDao;
	@Inject 
	private IOcKjSellerSeparateOrderDao kjSellerSeparateOrderDao; 
	
	private List<OcKjSellerSeparateOrder> list;
	
	public String doProcess(String responseJson) {
		SoResponse entity = null;
		try {
			entity = JSON.parseObject(responseJson, SoResponse.class); 
		} catch (Exception e) {
			String message = "响应消息体错误，响应数据解析异常，请联系民生品粹，异常信息如下：\n" + ExceptionHelper.allExceptionInformation(e); 
			logger.error(message);
			return message; 
		} 
		String msg = "";
		if(entity.getCode().equals("0")){
			this.updateOrderStatus(entity.getData()); 
			msg = "Rsync Order Status Success";
		}else{
			msg = entity.getDesc();
		}
		return msg;
	}


	public String getRequestMethod() {
		return "Order.SOTrace";
	}

	/**
	 * @description:多个订单号用英文逗号“,”分隔。此订单号为民生品粹的订单号 
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月14日 下午2:57:32 
	 * @version 1.0.0.1
	 */
	public String setRequestDataJson() {
		OcKjSellerSeparateOrder e = new OcKjSellerSeparateOrder();
		e.setSellerCode(this.getSellerCode()); 
		e.setSellerStatus("1"); 
		list = kjSellerSeparateOrderDao.selectByTypes(e);
		List<String> list_ = new ArrayList<>();
		for(OcKjSellerSeparateOrder o : list){
			list_.add(o.getSellerOrderCode());
		}
		
		return StringUtils.join(list_ , ","); 
	}

	
	/**
	 * @description: 逐条进行更新操作，同时更新 oc_orderinfo 表  
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月14日 下午4:54:37 
	 * @version 1.0.0.1
	 */
	private void updateOrderStatus(List<DataResponse> list_){
		List<OcOrderinfo> orderinfoList = new ArrayList<>(); // 保存已发货的订单信息 
		for(DataResponse e : list_){
			OcOrderinfo info = new OcOrderinfo();
			OcKjSellerSeparateOrder k = new OcKjSellerSeparateOrder();
			if(e.getSoStatus().equals("1")){ // 已出关 则认为已经发货 
				for(OcKjSellerSeparateOrder o : list){    // 保存oc_orderInfo表中的订单编号，等待更新真正的订单状态
					if(e.getOrderID().equals(o.getSellerOrderCode())){
						info.setOrderCode(o.getOrderCode());
						info.setOrderStatus("4497153900010003");
						orderinfoList.add(info);
					}
				}
				k.setStatus("4497153900010003"); // order_status 订单状态 已发货
				
				// TODO 插入物流信息
				String expressBill = e.getExpressBill(); // 民生品粹返回的快递单号
				
			}
			// 更新拆单表
			k.setSellerStatus(e.getSoStatus()); 
			k.setSellerOrderCode(e.getOrderID()); 
			k.setUpdateTime(new Date());
			k.setSellerCode(this.getSellerCode()); 
			kjSellerSeparateOrderDao.updateBySellerOrderCode(k);
		}
		
		// 批量更新oc_orderInfo表中的状态  
		if(orderinfoList.size() != 0){
			orderinfoDao.batchUpdate(orderinfoList); 
		}
	}
	
	

}




















