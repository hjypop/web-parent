package com.hjy.job;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.w3c.dom.Document;

import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.Inject;
import com.hjy.dao.order.IOcKjSellerCustomsDeclarationDao;
import com.hjy.entity.order.OcKjSellerCustomsDeclaration;
import com.hjy.helper.PureNetUtil;
import com.hjy.helper.SignHelper;
import com.hjy.helper.XmlHelper;
import com.hjy.quartz.job.RootJob;
import com.hjy.request.customsDeclaration.RequestParam;

/**
 * @description: 跨境商户的订单进行报关处理|处理oc_kj_seller_customs_declaration表中
 * 	type=‘alipay’的所有记录，对其进行报关处理
 * 	支付宝报关处理 
 * @author Yangcl
 * @date 2016年10月26日 下午2:29:14 
 * @version 1.0.0
 */
public class JobForAlipayCustomsDeclaration extends RootJob{
	
	@Inject
	private IOcKjSellerCustomsDeclarationDao dao;

	private String startTime;
	private String endTime;
	
	public JobForAlipayCustomsDeclaration() {
	}

	public JobForAlipayCustomsDeclaration(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}


	public void doExecute(JobExecutionContext context) {
		if(StringUtils.isAnyBlank(this.startTime , this.endTime)){  // 非手动执行该定时任务 
			Date date = new Date(); 								 // 2016-09-18 16:26:08
			this.startTime = this.getHour(date , -1);  // 2016-09-18 15:00:00         带同步订单的开始时间
			this.endTime = this.getHour(date , 0);	   // 2016-09-18 16:00:00		 带同步订单的结束时间
		}
		if(this.compareDate(this.startTime , this.endTime)){  // 开始时间大于结束时间则返回
			return ;
		}
		List<String> sscList = new ArrayList<String>(Arrays.asList(this.getConfig("seller_adapter.kj_customs_declaration").split(","))); 
		Map<String , String> kjsellerMap = new HashMap<String , String>();
		for(String s : sscList){
			String [] arr = s.split("@");
			kjsellerMap.put(arr[0] , arr[1] + "@" + arr[2]);
		}
		
		Map<String , String> map = new HashMap<String , String>(3);
		map.put("type", "alipay");
		map.put("startTime" , this.startTime);
		map.put("endTime" , this.endTime); 
		List<OcKjSellerCustomsDeclaration> list = dao.getRequestList(map);
		if(list != null && list.size() > 0){
			for(OcKjSellerCustomsDeclaration e : list){ 
				RequestParam req = new RequestParam();
//				AlipayResponse ar = this.getResponse(this.sendRequest(req));
				
				
				
				
				OcKjSellerCustomsDeclaration u = new OcKjSellerCustomsDeclaration();
				u.setUid(e.getUid()); 
				u.setUpdateTime(new Date()); 
//				if(ar.getIsSuccess().equals("T") && ar.getResultCode().equals("SUCCESS")){ // 请求处理成功
//					u.setFlag(1);
//					u.setRemark("报关成功"); 
//					u.setTradeNo(ar.getTradeNo()); 
//				}else{
//					u.setFlag(2); 
//					u.setRemark(ar.getDetailErrorDes());
//				}
				dao.updateSelective(u);
			}
		}
	}
	
	
	
	/**
	 * @description: 发送报关请求 
	 * 
	 * @param req
	 * @return
	 * @author Yangcl 
	 * @date 2016年10月26日 下午4:49:40 
	 * @version 1.0.0.1
	 */
	private String sendRequest(RequestParam req){
		String response = PureNetUtil.post(this.getRequestUrl() , this.initRequestParam(req));
		return response;
	}
	
	
	private String getRequestUrl(){
		return this.getConfig("seller_adapter.pay_ichsy_com");
	}
	
	private Map<String , String> initRequestParam(RequestParam req){
		Map<String , String> param = new HashMap<String , String>();
		param.put("data", JSONObject.toJSONString(req));
        param.put("sign", this.getSign(param));
		return param;
	}
	
	private String getSign(Map<String, String> map){
		String sign = "";
		try { 
			List<String> list = new ArrayList<String>();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (entry.getValue() != "") {
					list.add(entry.getKey() + "=" + entry.getValue() + "&");
				}
			}
			Collections.sort(list); // 对List内容进行排序
			StringBuffer str = new StringBuffer();
			for (String nameString : list) {
				str.append(nameString);
			}
			str.append(this.getConfig("seller_adapter.pay_ichsy_pass")); // 支付宝报关的秘钥
			sign = SignHelper.md5Sign(str.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;
	}
	
	private String getHour(Date date , int flag){
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.HOUR , flag); 
		 date=calendar.getTime();  
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		 
		 return formatter.format(date);
	}
	
	private boolean compareDate(String a , String b){
		if(StringUtils.isAnyBlank(a , b)){
			return false;
		}
		return a.compareTo(b) > 0;
	}

}






























