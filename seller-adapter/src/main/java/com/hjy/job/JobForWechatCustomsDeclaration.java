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

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.w3c.dom.Document;

import com.hjy.annotation.Inject;
import com.hjy.dao.order.IOcKjSellerCustomsDeclarationDao;
import com.hjy.entity.order.OcKjSellerCustomsDeclaration;
import com.hjy.helper.PureNetUtil;
import com.hjy.helper.SignHelper;
import com.hjy.quartz.job.RootJob;
import com.hjy.helper.XmlHelper;
/**
 * @description: 跨境商户的订单进行报关处理|处理oc_kj_seller_customs_declaration表中
 * 	type=‘wechat’的所有记录，对其进行报关处理
 * 	微信报关处理
 * @author Yangcl
 * @date 2016年10月26日 下午2:29:14 
 * @version 1.0.0
 */
public class JobForWechatCustomsDeclaration extends RootJob{

	@Inject
	private IOcKjSellerCustomsDeclarationDao dao;
	
	private String startTime;
	private String endTime;
	
	public JobForWechatCustomsDeclaration() {
	}

	public JobForWechatCustomsDeclaration(String startTime, String endTime) {
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
		map.put("type", "wechat");
		map.put("startTime" , this.startTime);
		map.put("endTime" , this.endTime); 
		List<OcKjSellerCustomsDeclaration> list = dao.getRequestList(map);
		if(list != null && list.size() > 0){
			for(OcKjSellerCustomsDeclaration e : list){
				WechatParamRequest r = new WechatParamRequest();
				r.setPayGate(e.getPayGate());
				r.setTransNum(e.getTransNum());
				r.setLocation(kjsellerMap.get(e.getSellerCode()).split("@")[1]);
				r.setMchCustomsNo(kjsellerMap.get(e.getSellerCode()).split("@")[0]); 
				r.setBankOrderId(e.getBankOrderId());
				r.setDueMoney(String.valueOf(e.getDueMoney()));
				r.setTransportMoney(String.valueOf(e.getTransportMoney()));
				r.setOrderCode(e.getOrderCode());
				r.setIdcard(e.getIdcard());
				r.setAuthName(e.getAuthName()); 
				WechatResponse wr = this.getResponse(this.sendRequest(r));
				OcKjSellerCustomsDeclaration u = new OcKjSellerCustomsDeclaration();
				u.setUid(e.getUid()); 
				u.setUpdateTime(new Date()); 
				if("SUCCESS".equals(wr.getReturnCode()) && "SUCCESS".equals(wr.getResultCode())){ // 请求处理成功
					u.setFlag(1);
					u.setRemark("报关成功"); 
					u.setTradeNo(wr.getTradeNo()); 
				}else{
					u.setFlag(2); 
					u.setRemark(wr.getDetailErrorDes());
				}
				
				dao.updateSelective(u);
			}
		}
	}
	
	
	private WechatResponse getResponse(String responseXml){
		WechatResponse e = new WechatResponse();
		Document doc = XmlHelper.buildDocument(responseXml);
		String returnCode = XmlHelper.getValueFormXPath(doc, "//xml/return_code/text()");
		String returnMsg = XmlHelper.getValueFormXPath(doc, "//xml/return_msg/text()");
		String resultCode = XmlHelper.getValueFormXPath(doc, "//xml/result_code/text()");
		String detailErrorDes = XmlHelper.getValueFormXPath(doc, "//xml/err_code_des/text()");
		String tradeNo = XmlHelper.getValueFormXPath(doc, "//xml/transaction_id/text()");
		String subTradeNo = XmlHelper.getValueFormXPath(doc, "//xml/sub_order_id/text()");
		
		// 如果微信支付未返回子支付单号，则使用原支付单号
		if(StringUtils.isBlank(subTradeNo)){
			subTradeNo = tradeNo;
		}
		
		e.setReturnCode(returnCode); 
		e.setReturnMsg(returnMsg);
		e.setResultCode(resultCode);
		e.setDetailErrorDes(detailErrorDes);
		e.setTradeNo(tradeNo);
		e.setSubTradeNo(subTradeNo);
		
		return e;
	}
	
	
	private String sendRequest(WechatParamRequest r){
		String response = PureNetUtil.post(this.getRequestUrl() , this.initRequestParam(r));
		return response;
	}
	
	
	private Map<String , String> initRequestParam(WechatParamRequest r){
		Map<String , String> param = new HashMap<String , String>();
		String md5key = "";
		if(r.getPayGate().equals("762") || r.getPayGate().equals("76")){
			// 家有购的微信商户号
			param.put("appid", this.getConfig("seller_adapter.jyg_wechat_appid"));
			param.put("mch_id", this.getConfig("seller_adapter.jyg_wechat_mch_id"));
			md5key = this.getConfig("seller_adapter.jyg_wechat_md5key");
		}else{
			// 惠家有的微信商户号
			param.put("appid", this.getConfig("seller_adapter.hjy_wechat_appid"));
			param.put("mch_id", this.getConfig("seller_adapter.hjy_wechat_mch_id"));
			md5key = this.getConfig("seller_adapter.hjy_wechat_md5key");
		}
		param.put("out_trade_no", r.getTransNum());		// oc_payment_paygate -> c_transnum
		param.put("customs", r.getLocation());
		param.put("mch_customs_no", r.getMchCustomsNo());
		param.put("transaction_id", r.getBankOrderId());
		
		Double dueMoney = Double.valueOf(r.getDueMoney());
		Double transportMoney = Double.valueOf(r.getTransportMoney());
		Double productFee = dueMoney - transportMoney ; 
		param.put("order_fee", String.valueOf(dueMoney)); // 订单交易金额  oc_orderinfo->  due_money
		param.put("product_fee", String.valueOf(productFee)); // 交易金额减去运费 = 产品金额|数据库记录可能不准
		param.put("transport_fee", String.valueOf(transportMoney)); // 运费 oc_orderinfo-> transport_money
		param.put("sub_order_no", r.getOrderCode());		// 惠家有 order_code  代表拆单 - 所有的订单都做拆单处理
		param.put("fee_type", "CNY"); 	// 支付币种类型  人民币
		param.put("cert_id", r.getIdcard());		// 支付人的身份证编号 
		param.put("name", r.getAuthName());		// 支付人姓名
		param.put("cert_type", "IDCARD");
		param.put("sign", this.getSign(param, md5key)); 
		return param ;
	}
	
	private String getRequestUrl(){
		return this.getConfig("seller_adapter.wechat_url");
	}
	
	private String getSign(Map<String, String> map , String md5key){
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
			str.append(md5key); // 微信 
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


class WechatParamRequest{
	private String payGate;
	private String transNum;		// 支付网关订单号  oc_payment_paygate -> c_transnum
	private String location; 		// 报关地点
	private String mchCustomsNo;   // 每个商户自己的报关编号，需要商户提供给我们，表里没有
	private String bankOrderId;
	
	private String dueMoney;
	private String transportMoney;
	private String orderCode;
	private String idcard;
	private String authName;
	
	
	public String getPayGate() {
		return payGate;
	}
	public void setPayGate(String payGate) {
		this.payGate = payGate;
	}
	public String getTransNum() {
		return transNum;
	}
	public void setTransNum(String transNum) {
		this.transNum = transNum;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMchCustomsNo() {
		return mchCustomsNo;
	}
	public void setMchCustomsNo(String mchCustomsNo) {
		this.mchCustomsNo = mchCustomsNo;
	}
	public String getBankOrderId() {
		return bankOrderId;
	}
	public void setBankOrderId(String bankOrderId) {
		this.bankOrderId = bankOrderId;
	}
	public String getDueMoney() {
		return dueMoney;
	}
	public void setDueMoney(String dueMoney) {
		this.dueMoney = dueMoney;
	}
	public String getTransportMoney() {
		return transportMoney;
	}
	public void setTransportMoney(String transportMoney) {
		this.transportMoney = transportMoney;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	
}

class WechatResponse{
	private String returnCode ;
	private String returnMsg ;
	private String resultCode ;
	private String detailErrorDes ;
	private String tradeNo ;
	private String subTradeNo ;
	
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getDetailErrorDes() {
		return detailErrorDes;
	}
	public void setDetailErrorDes(String detailErrorDes) {
		this.detailErrorDes = detailErrorDes;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getSubTradeNo() {
		return subTradeNo;
	}
	public void setSubTradeNo(String subTradeNo) {
		this.subTradeNo = subTradeNo;
	}
}























