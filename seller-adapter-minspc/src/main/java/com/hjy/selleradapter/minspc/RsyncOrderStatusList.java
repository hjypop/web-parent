package com.hjy.selleradapter.minspc;

import java.util.ArrayList;
import java.util.List;

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
		List<OcKjSellerSeparateOrder> list = kjSellerSeparateOrderDao.selectByTypes(e);
		List<String> list_ = new ArrayList<>();
		for(OcKjSellerSeparateOrder o : list ){
			list_.add(o.getSellerOrderCode());
		}
		
		return StringUtils.join(list_, ","); 
	}

	
	private void updateOrderStatus(List<DataResponse> list){
		for(DataResponse e : list){
			
		}
	}
	
	

}




















