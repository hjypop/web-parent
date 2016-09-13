package com.hjy.selleradapter.minspc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.Inject;
import com.hjy.dao.IJobExectimerDao;
import com.hjy.dao.order.IOcKjSellerSeparateOrderDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.dto.request.subscribeOrder.Item;
import com.hjy.dto.request.subscribeOrder.SeparateOrderDto;
import com.hjy.dto.request.subscribeOrder.SoRequest;
import com.hjy.dto.response.subscribeOrder.DataResponse;
import com.hjy.dto.response.subscribeOrder.SoResponse;
import com.hjy.entity.order.OcKjSellerSeparateOrder;
import com.hjy.helper.ExceptionHelper;
import com.hjy.pojo.entity.system.JobExectimer;


/**
 * @title: com.hjy.selleradapter.minspc.RsyncSubscribeOrder.java 
 * @description: 生成订阅订单（并发送海关）|完成接口请求等内容。
 *
 * @author Yangcl
 * @date 2016年9月6日 下午2:36:49 
 * @version 1.0.0
 */
public class RsyncSubscribeOrder extends RsyncMinspc{
	
	private static Logger logger = Logger.getLogger(RsyncSubscribeOrder.class);
	
	@Inject
	private IOcOrderinfoDao orderinfoDao;
	
	@Inject
	private IJobExectimerDao jobExectimerDao;
	
	@Inject 
	private IOcKjSellerSeparateOrderDao kjSellerSeparateOrderDao; // 拆单  

	private SoRequest soRequest;    
	private List<String> splitInfoList; // 在调用类中传递信息  productCode@#skuCode@#skuName
	

	public String doProcess(String responseJson) {
		Date currentTime = new Date();
		JobExectimer update = new JobExectimer();
		update.setBeginTime(currentTime); 
		
		SoResponse entity = null;
		try {
			entity = JSON.parseObject(responseJson, SoResponse.class); 
		} catch (Exception e) {
			String message = "响应消息体错误，响应数据解析异常，请联系民生品粹，异常信息如下：\n" + ExceptionHelper.allExceptionInformation(e); 
			logger.error(message);
			return message; 
		} 
		
		if(entity.getCode().equals("0")){
			List<SeparateOrderDto> list = new ArrayList<SeparateOrderDto>();
			List<DataResponse> dataList = entity.getData(); // 开始拆分跨境商户订单。
			for(DataResponse dr : dataList){
				SeparateOrderDto sor = new SeparateOrderDto();
				sor.setAuthenticationInfo(this.soRequest.getAuthenticationInfo());
				sor.setMerchantOrderID(this.soRequest.getMerchantOrderID());
				sor.setPayInfo(this.soRequest.getPayInfo());
				sor.setShippingInfo(this.soRequest.getShippingInfo());
				for(Item i : this.soRequest.getItemList()){
					sor.setItem(i); 
			
					
				}
				sor.setOrderType(dr.getOrderType());  // 订单类型，0为保税贸易订单，1为直邮贸易订单，2为一般贸易订单
				sor.setSOSysNo(dr.getSOSysNo());  // 民生品粹的系统订单号
				list.add(sor);
			}
			this.SeparateOrderInit(list); 
			
			
			update.setFlagSuccess(1);
		}else{
			update.setFlagSuccess(0);
		}
		update.setRemark(JSONObject.toJSONString(entity)); 
		
		// 更新job_exectimer表
		update.setEndTime(new Date());
		jobExectimerDao.updateSelectiveByOrderCode(update); 
		
		
		return "Rsync Subscribe Order Success "; 
	}

	/**
	 * @description: 准备分拆订单到数据库的 oc_kj_seller_separate_order 表|insert 操作
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月13日 下午6:26:08 
	 * @version 1.0.0.1
	 */
	public void SeparateOrderInit(List<SeparateOrderDto> list_){
		List<OcKjSellerSeparateOrder> list = new ArrayList<OcKjSellerSeparateOrder>();
		int count = 1;
		for(SeparateOrderDto d : list_){
			OcKjSellerSeparateOrder e = new OcKjSellerSeparateOrder();
			e.setOrderCode(d.getMerchantOrderID()); 
			e.setSellerOrderCode(d.getSOSysNo());
			e.setOrderCodeSeq(d.getMerchantOrderID() + "#" + count); 
			e.setStatus("4497153900010002");
			e.setSellerStatus("null-operat"); 
			e.setCreateTime(new Date());
			e.setSkuSellPrice(d.getItem().getSalePrice());
			e.setTaxPrice(d.getItem().getTaxPrice());
			e.setQuantity(d.getItem().getQuantity());
			e.setOrderType(d.getOrderType());
			e.setFreight(new BigDecimal(0.00)); 
			e.setUpdateTime(new Date());
			e.setProductCode("");  // TODO 
			e.setSkuCode(""); // TODO
			e.setSkuName(""); // TODO 
			e.setSellerCode("");  // TODO 
			e.setSellerProductCode(d.getItem().getProductID()); 
			e.setSellerSkuCode(e.getSellerProductCode()); // 商户sku编号|如果商户没有sku的概念，则默认为seller_product_code
			e.setRequestJson(JSON.toJSONString(d)); 
			e.setRemark("insert success"); 
			
			
			
			count ++;
			list.add(e);
		}
		
	}
	
	
	
	/**
	 * @description: 
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月7日 下午2:58:26 
	 * @version 1.0.0.1
	 */
	public String getRequestMethod() {
		return "SubscribeOrder.Create";
	}

	
	/**
	 * @description: 拼装请求数据 
	 * 
	 * @author Yangcl
	 * @date 2016年9月7日 下午2:58:26 
	 * @version 1.0.0.1
	 */
	public String setRequestDataJson() {
		return JSONObject.toJSONString(this.getSoRequest()); 
	}

	
	
	public SoRequest getSoRequest() {
		return soRequest;
	}
	public void setSoRequest(SoRequest soRequest) {
		this.soRequest = soRequest;
	}
	public List<String> getSplitInfoList() {
		return splitInfoList;
	}
	public void setSplitInfoList(List<String> splitInfoList) {
		this.splitInfoList = splitInfoList;
	}

	
	
	
}




























