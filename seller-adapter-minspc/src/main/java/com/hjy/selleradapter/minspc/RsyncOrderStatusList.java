package com.hjy.selleradapter.minspc;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hjy.annotation.Inject;
import com.hjy.dao.order.IOcKjSellerSeparateOrderDao;
import com.hjy.entity.order.OcKjSellerSeparateOrder;

public class RsyncOrderStatusList extends RsyncMinspc {
	
	@Inject 
	private IOcKjSellerSeparateOrderDao kjSellerSeparateOrderDao; 
	
	
	public String doProcess(String responseJson) {
		
		
		return "";
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
		List<String> list = kjSellerSeparateOrderDao.selectByStatus(e);
		
		return StringUtils.join(list, ","); 
	}

	
	
	

}




















