package com.hjy.selleradapter.minspc;

import java.util.Date;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.hjy.annotation.Inject;
import com.hjy.dao.order.IOcKjSellerSeparateOrderDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.dto.response.orderVoid.DataResponse;
import com.hjy.dto.response.orderVoid.SoResponse;
import com.hjy.entity.order.OcKjSellerSeparateOrder;
import com.hjy.helper.ExceptionHelper;

public class RsyncVoidOrder extends RsyncMinspc {

private static Logger logger = Logger.getLogger(RsyncVoidOrder.class);
	
	@Inject
	private IOcOrderinfoDao orderinfoDao;
	@Inject 
	private IOcKjSellerSeparateOrderDao kjSellerSeparateOrderDao; 
	
	private OcKjSellerSeparateOrder updateInfo;
	
	
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
			DataResponse dr = entity.getData().get(0);
			if(dr.getStatus() == "0"){  // 表示作废成功
				if(this.getUpdateInfo().getSellerOrderCode().equals(dr.getOrderID())){
					OcKjSellerSeparateOrder osr = new OcKjSellerSeparateOrder();
					osr.setZid(this.getUpdateInfo().getZid());
					osr.setSellerStatus("void-order"); 
					osr.setUpdateTime(new Date());
					kjSellerSeparateOrderDao.updateSelective(osr);
					msg = "Order Void Success";
				}else{
					msg = "Order Void False - Response Seller Order Code Is : " + dr.getOrderID() + 
							" - But Our Seller Order Code Is : " + this.getUpdateInfo().getSellerOrderCode();
				}
			}else{
				msg = dr.getMessage();  // 订单已出库，不允许取消！
			}
		}else{
			msg = entity.getDesc(); // 可能存在的异常消息提示
		}
		return msg;
	}
	
	
	public String getRequestMethod() {
		return "Order.SOVoid";
	}
	
	public String setRequestDataJson() {   // 拼接请求Json 
		return "{\"Orderlds\":\"" + this.getUpdateInfo().getSellerOrderCode() + "\",\"OrderType\":\"" + this.getUpdateInfo().getSellerStatus() + "\"}";
	}


	public OcKjSellerSeparateOrder getUpdateInfo() {
		return updateInfo;
	}
	public void setUpdateInfo(OcKjSellerSeparateOrder updateInfo) {
		this.updateInfo = updateInfo;
	}

	

}























