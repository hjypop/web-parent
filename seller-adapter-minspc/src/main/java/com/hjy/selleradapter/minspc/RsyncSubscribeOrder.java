package com.hjy.selleradapter.minspc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.Inject;
import com.hjy.dao.IJobExectimerDao;
import com.hjy.dao.order.IOcKjSellerSeparateOrderDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.dto.minspc.MinspcOrderdetailOne;
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
	List<MinspcOrderdetailOne> mooList; // 在调用类中传递信息 
	

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
		
		String msg = "";
		if(entity.getCode().equals("0")){
			this.SeparateOrderInit(entity.getData()); // 开始拆分跨境商户订单: List<DataResponse>
			update.setFlagSuccess(1);
			msg = "Rsync Subscribe Order Success";
		}else{
			update.setFlagSuccess(0);
			msg = entity.getDesc();
		}
		update.setRemark(JSONObject.toJSONString(entity)); 
		
		// 更新job_exectimer表
		update.setEndTime(new Date());
		update.setExecInfo(this.getSoRequest().getMerchantOrderID()); // order code 
		jobExectimerDao.updateSelectiveByOrderCode(update); 
		
		return msg; 
	}

	/**
	 * @description: 准备分拆订单到数据库的 oc_kj_seller_separate_order 表|insert 操作
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月13日 下午6:26:08 
	 * @version 1.0.0.1
	 */
	public void SeparateOrderInit(List<DataResponse> list_){
		int count = 1;
		for(DataResponse d : list_){
			OcKjSellerSeparateOrder e = new OcKjSellerSeparateOrder();
			e.setOrderCode(d.getMerchantOrderID()); 
			e.setSellerOrderCode(d.getSOSysNo());
			e.setOrderCodeSeq(d.getMerchantOrderID() + "#" + count); 
			e.setStatus("4497153900010002");
			e.setSellerStatus("0");   // 拆单时候，默认设置为0，还未出关
			e.setCreateTime(new Date());
			e.setOrderType(d.getOrderType());
			e.setFreight(new BigDecimal(0.00)); 
			e.setUpdateTime(new Date());
			e.setSellerSkuCode(e.getSellerProductCode()); // 商户sku编号|如果商户没有sku的概念，则默认为seller_product_code
			e.setRequestJson(JSON.toJSONString(d)); 
			e.setRemark("insert success"); 
			
			if(d.getProductID() != null && d.getProductID().size() != 0){  // 直邮则无此赋值操作 - Yangcl
				List<String> sspList = new ArrayList<String>();  // SkuSellPrice
				List<String> pcList = new ArrayList<String>(); // ProductCode
				List<String> snList = new ArrayList<String>(); // setSkuName
				List<String> qList = new ArrayList<String>(); // quantity
				List<String> sList = new ArrayList<String>(); // sku code
				
				for(String pco : d.getProductID()){
					List<MinspcOrderdetailOne> spiList = this.getMooList();  // 获取JobForCreateSubscribeOrder.java中的信息
					for(MinspcOrderdetailOne s : spiList){
						if(pco.equals(s.getProductID())){
							pcList.add(s.getPcode());
							sList.add(s.getSkuCode());
							sspList.add(s.getSkuCode() + "@" + s.getSalePrice());
							snList.add(s.getSkuCode() + "@" + s.getSkuName());
							qList.add(s.getSkuCode() + "@" + s.getQuantity());
						}
					}
				}
				
				e.setSellerProductCode(StringUtils.join(d.getProductID(), ","));  
				e.setSkuSellPrice(StringUtils.join(sspList , ","));
				e.setQuantity(StringUtils.join(qList , ","));
				e.setProductCode(StringUtils.join(pcList , ","));  
				e.setSkuCode(StringUtils.join(sList , ","));  
				e.setSkuName(StringUtils.join(snList, ","));  
			}
			
			e.setSellerCode(this.getSellerCode());  
			count ++;
			kjSellerSeparateOrderDao.insertSelective(e);
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
	 * @description: 拼装请求数据 | TODO 此处需要执行强制判空操作，每个属性都不得为空
	 * 
	 * @author Yangcl
	 * @date 2016年9月7日 下午2:58:26 
	 * @version 1.0.0.1
	 */
	public String getRequestDataJson() {
		return JSONObject.toJSONString(this.getSoRequest()); 
	}

	
	
	public SoRequest getSoRequest() {
		return soRequest;
	}
	public void setSoRequest(SoRequest soRequest) {
		this.soRequest = soRequest;
	}

	public List<MinspcOrderdetailOne> getMooList() {
		return mooList;
	}
	public void setMooList(List<MinspcOrderdetailOne> mooList) {
		this.mooList = mooList;
	}


	
	
	
}




























