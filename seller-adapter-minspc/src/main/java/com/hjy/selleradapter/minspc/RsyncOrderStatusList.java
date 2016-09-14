package com.hjy.selleradapter.minspc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.hjy.annotation.Inject;
import com.hjy.dao.order.IOcKjSellerSeparateOrderDao;
import com.hjy.dto.response.orderStatus.DataResponse;
import com.hjy.dto.response.orderStatus.SoResponse;
import com.hjy.entity.order.OcKjSellerSeparateOrder;
import com.hjy.helper.ExceptionHelper;

public class RsyncOrderStatusList extends RsyncMinspc {
	
	private static Logger logger = Logger.getLogger(RsyncOrderStatusList.class);
	
	
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
		e.setSellerStatus("minspc0003"); 
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
		Set<String> orderCodeList = new HashSet<>();
		for(DataResponse e : list_){
			OcKjSellerSeparateOrder k = new OcKjSellerSeparateOrder();
			if(e.getSoStatus().equals("minspc0003")){ // 已出关 则认为已经发货 
				for(OcKjSellerSeparateOrder o : list){    // 保存oc_orderInfo表中的订单编号，等待更新真正的订单状态
					if(e.getOrderID().equals(o.getSellerOrderCode())){
						orderCodeList.add(o.getOrderCode());
					}
				}
				k.setStatus("4497153900010003"); // order_status 订单状态 已发货
			}
			// TODO 更新拆单表
			k.setSellerStatus(e.getSoStatus()); 
			k.setSellerOrderCode(e.getOrderID()); 
			k.setUpdateTime(new Date());
			kjSellerSeparateOrderDao.updateBySellerOrderCode(k);
		}
		
		// TODO 批量更新oc_orderInfo表中的状态  
		
		
		
	}
	
	

}




















