package com.hjy.selleradapter.minspc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.hjy.annotation.Inject;
import com.hjy.dao.order.IOcKjSellerSeparateOrderDao;
import com.hjy.dao.order.IOcOrderShipmentsDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.dto.response.orderStatus.DataResponse;
import com.hjy.dto.response.orderStatus.SoResponse;
import com.hjy.entity.order.OcKjSellerSeparateOrder;
import com.hjy.entity.order.OcOrderShipments;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.helper.DateHelper;
import com.hjy.helper.ExceptionHelper;

public class RsyncOrderStatusList extends RsyncMinspc {
	
	private static Logger logger = Logger.getLogger(RsyncOrderStatusList.class);
	
	@Inject
	private IOcOrderinfoDao orderinfoDao;
	@Inject 
	private IOcKjSellerSeparateOrderDao kjSellerSeparateOrderDao; 
	@Inject
	private IOcOrderShipmentsDao ocOrderShipmentsDao;
	
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
		try {
			if(entity.getCode().equals("0")){
				this.updateOrderStatus(entity.getData()); 
				msg = "Rsync Order Status Success";
			}else{
				msg = entity.getDesc();
			}
		} catch (Exception e) {
			msg = "获取订单状态失败：\n";
			msg += ExceptionHelper.allExceptionInformation(e);
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
	public String getRequestDataJson() {
		OcKjSellerSeparateOrder e = new OcKjSellerSeparateOrder();
		e.setSellerCode(this.getSellerCode()); 
		e.setSellerStatus("0"); // 0: 还未出关
		list = kjSellerSeparateOrderDao.selectByTypes(e);
		List<String> list_ = new ArrayList<>();
		for(OcKjSellerSeparateOrder o : list){
			list_.add(o.getSellerOrderCode());
		}
		
		return "{\"OrderIds\": \"" +StringUtils.join(list_ , ",") + "\"}"; 
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
			OcKjSellerSeparateOrder k = new OcKjSellerSeparateOrder();
			if(e.getSoStatus().equals("1")){ // 已出关 则认为已经发货 
				OcOrderinfo info = new OcOrderinfo();
				for(OcKjSellerSeparateOrder o : list){    // 保存oc_orderInfo表中的订单编号，等待更新真正的订单状态
					if(e.getOrderID().equals(o.getSellerOrderCode())){
						info.setOrderCode(o.getOrderCode());
						info.setOrderStatus("4497153900010003");
						orderinfoList.add(info);
					}
				}
				k.setStatus("4497153900010003"); // order_status 订单状态 已发货
				// 更新拆单表
				k.setSellerStatus(e.getSoStatus()); 
				k.setUpdateTime(new Date());
				k.setSellerOrderCode(e.getOrderID()); 		// 更新依据
				k.setSellerCode(this.getSellerCode()); 		// 更新依据 
				kjSellerSeparateOrderDao.updateBySellerOrderCode(k);
				
				// 插入物流信息
				String [] arr = this.getLogisticse(e.getShipTypeID()).split("@");
				OcOrderShipments oos = new OcOrderShipments();
				oos.setOrderCode(info.getOrderCode());
				if(arr.length == 2){
					oos.setLogisticseCode(arr[0]); 
					oos.setLogisticseName(arr[1]); 
				}else{
					oos.setLogisticseCode("民生品粹未返回快递公司编号"); 
					oos.setLogisticseName("无"); 
				}
				oos.setWaybill(e.getExpressBill());
				oos.setCreator("minspc-job-system"); 
				oos.setCreateTime(DateHelper.formatDate(new Date())); 
				oos.setRemark("您的订单已出海关"); 
				oos.setUid(UUID.randomUUID().toString().replace("-", ""));
				ocOrderShipmentsDao.insertSelective(oos);
			}
		}
		
		// 批量更新oc_orderInfo表中的状态  
		if(orderinfoList.size() != 0){
			orderinfoDao.batchUpdateByOrderCode(orderinfoList); 
		}
	}
	
	
	/**
	 * @description: 根据民生品粹 shipTypeId 返回 logisticseCode@logisticseName 
	 *
	 * @param code
	 * 
	 * @author Yangcl
	 * @date 2016年9月14日 下午4:20:39
	 * @version 1.0.0.1
	 */
	private String getLogisticse(String code){
		String result = "";
		switch (code) {
			case "375":
				result = "yunda@韵达";
				break;
			case "84":
				result = "rufengda@如风达";
				break;
			case "1":
				result = "shunfeng@顺丰速运";
				break;
			case "2":
				result = "yuantong@圆通速递";
				break;
			case "93":
				result = "shentong@申通";
				break;
			case "264":
				result = "youzhengxb@邮政小包";
				break;
			case "393":
				result = "tiantian@天天速递";
				break; 
		}
		return result;
	}

}




















