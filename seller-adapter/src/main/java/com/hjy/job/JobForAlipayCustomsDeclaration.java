package com.hjy.job;

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

import com.hjy.annotation.Inject;
import com.hjy.dao.order.IOcKjSellerCustomsDeclarationDao;
import com.hjy.entity.order.OcKjSellerCustomsDeclaration;
import com.hjy.helper.PureNetUtil;
import com.hjy.helper.SignHelper;
import com.hjy.helper.XmlHelper;
import com.hjy.quartz.job.RootJob;

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
				AlipayParamRequest req = new AlipayParamRequest();
				req.setBankOrderId(e.getBankOrderId());
				req.setDueMoney(String.valueOf(e.getDueMoney()));	
				req.setSubOrderCode(e.getOrderCode());
				if(e.getOrderAmount() != e.getDueMoney()){  // 订单应付金额和订单交易单号实付金额不一致则是有多单支付
					req.setIsSplit("T"); // 如果需要拆单，则为true 
				}else{
					req.setIsSplit("F"); 
				}
				req.setMerchantCustomsCode(kjsellerMap.get(e.getSellerCode()).split("@")[0]);
				req.setMerchantCustomsName(e.getSellerName());
				req.setLocation(kjsellerMap.get(e.getSellerCode()).split("@")[1]);   
				AlipayResponse ar = this.getResponse(this.sendRequest(req));
				OcKjSellerCustomsDeclaration u = new OcKjSellerCustomsDeclaration();
				u.setUid(e.getUid()); 
				u.setUpdateTime(new Date()); 
				if(ar.getIsSuccess().equals("T") && ar.getResultCode().equals("SUCCESS")){ // 请求处理成功
					u.setFlag(1);
					u.setRemark("报关成功"); 
					u.setTradeNo(ar.getTradeNo()); 
				}else{
					u.setFlag(2); 
					u.setRemark(ar.getDetailErrorDes());
				}
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
	private String sendRequest(AlipayParamRequest req){
		String response = PureNetUtil.post(this.getRequestUrl() , this.initRequestParam(req));
		return response;
	}
	
	/**
	 * @description: 获取处理结果 
	 * 
	 * @param responseXml
	 * @return
	 * @author Yangcl 
	 * @date 2016年10月26日 下午6:23:34 
	 * @version 1.0.0.1
	 */
	private AlipayResponse getResponse(String responseXml){
		Document doc = XmlHelper.buildDocument(responseXml);
		String isSuccess = XmlHelper.getValueFormXPath(doc, "//alipay/is_success/text()");
        String error = XmlHelper.getValueFormXPath(doc, "//alipay/error/text()");
        String resultCode = XmlHelper.getValueFormXPath(doc, "//alipay/response/alipay/result_code/text()");
        String detailErrorDes = XmlHelper.getValueFormXPath(doc, "//alipay/response/alipay/detail_error_des/text()");
        String tradeNo = XmlHelper.getValueFormXPath(doc, "//alipay/response/alipay/trade_no/text()");
		
		return new AlipayResponse(isSuccess, error, resultCode, detailErrorDes, tradeNo);
	}
	
	
	
	
	/**
	 * @description: 构造请求数据
	 * 
	 * @param req 请求参数封装体 
	 * @return
	 * @author Yangcl 
	 * @date 2016年10月26日 下午3:53:16 
	 * @version 1.0.0.1
	 */
	private Map<String , String> initRequestParam(AlipayParamRequest req){
		Map<String , String> param = new HashMap<String , String>();
		param.put("service" , this.getConfig("seller_adapter.alipay_api")); // 支付宝接口名称
        param.put("partner",this.getConfig("seller_adapter.alipay_partner"));  // 合作者身份ID
        param.put("_input_charset","UTF-8");
        param.put("out_request_no" , new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + new Random().nextInt(1000)  );
        param.put("trade_no" , req.getBankOrderId());  // bank-order-id 支付流水号
        param.put("amount" , req.getDueMoney());   
        param.put("merchant_customs_code" , req.getMerchantCustomsCode());  // 每个商户自己的报关编号，需要商户提供给我们，表里没有
        param.put("merchant_customs_name" , req.getMerchantCustomsName());  // 商户名称 
        param.put("customs_place" , req.getLocation());   // 报关地点，可能每个商户报关地点不一样    "NINGBO"
        param.put("is_split",req.getIsSplit()); // 是否拆单的标识，默认是否(F)/(T)
        param.put("sub_out_biz_no",req.getSubOrderCode());  // 子订单号，拆单会用，可选
        param.put("sign", this.getSign(param));
        param.put("sign_type","MD5");
		
		return param;
	}
	
	private String getRequestUrl(){
		return this.getConfig("seller_adapter.alipay_url");
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
			str.append(this.getConfig("seller_adapter.alipay_customs_declaration_key")); // 支付宝报关的秘钥
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

class AlipayParamRequest{
	private String bankOrderId;  // 支付流水号
	private String dueMoney; // 订单实付金额 oc_orderinfo 表中的due_money
	private String isSplit;  // 是否拆单的标识，默认是否
	private String subOrderCode; // 子订单号，拆单会用，可选
	private String merchantCustomsCode;   // 每个商户自己的报关编号，需要商户提供给我们，表里没有
	private String merchantCustomsName;  // 商户名称 
	private String location; // 报关地点


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
	public String getIsSplit() {
		return isSplit;
	}
	public void setIsSplit(String isSplit) {
		this.isSplit = isSplit;
	}
	public String getSubOrderCode() {
		return subOrderCode;
	}
	public void setSubOrderCode(String subOrderCode) {
		this.subOrderCode = subOrderCode;
	}
	public String getMerchantCustomsCode() {
		return merchantCustomsCode;
	}
	public void setMerchantCustomsCode(String merchantCustomsCode) {
		this.merchantCustomsCode = merchantCustomsCode;
	}
	public String getMerchantCustomsName() {
		return merchantCustomsName;
	}
	public void setMerchantCustomsName(String merchantCustomsName) {
		this.merchantCustomsName = merchantCustomsName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}


class AlipayResponse{
	private String isSuccess;
	private String error;
	private String resultCode;
	private String detailErrorDes;
	private String tradeNo;
	
	public AlipayResponse(String isSuccess, String error, String resultCode, String detailErrorDes, String tradeNo) {
		this.isSuccess = isSuccess;
		this.error = error;
		this.resultCode = resultCode;
		this.detailErrorDes = detailErrorDes;
		this.tradeNo = tradeNo;
	}
	
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
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
}



























