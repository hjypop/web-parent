package com.hjy.selleradapter.kjt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.order.IOcKjSellerCustomsDeclarationDao;
import com.hjy.entity.order.OcKjSellerCustomsDeclaration;
import com.hjy.helper.ExceptionHelper;
import com.hjy.model.KjSellerCustomsDeclarationModel;
import com.hjy.model.RsyncResult;

public class RsyncAcquireSOPayDeclare extends RsyncKjtMaster{

	private static Logger logger = Logger.getLogger(RsyncAcquireSOPayDeclare.class);
	
	private IOcKjSellerCustomsDeclarationDao cdDao;
	private List<KjSellerCustomsDeclarationModel> list;
	
	// 初始化请求数据 
	public String getRequestDataJson() {
		List<AcquireSOPayDeclareRequest> reqList = new ArrayList<AcquireSOPayDeclareRequest>();
		for(KjSellerCustomsDeclarationModel e : this.list){
			AcquireSOPayDeclareRequest req = new AcquireSOPayDeclareRequest();
			req.setPayTime(e.getUpdateTime());
			req.setPayTransNumber(e.getTradeNo());
			req.setSOSysNo(Integer.valueOf(e.getKjtOrderCode()));
			if(e.getType().equals("alipay")){
				req.setPayTypeSysNo(112);
			}else{
				req.setPayTypeSysNo(118); 
			}
			reqList.add(req);
		}
		
		return JSONObject.toJSONString(reqList); 
	}
	
	
	
	public RsyncResult doProcess(String responseJson) {
		RsyncResult r = new RsyncResult();
		r.setProcessNum(this.list.size()); 
		AcquireSOPayDeclareResponse entity = null;
		try {
			entity = JSON.parseObject(responseJson, AcquireSOPayDeclareResponse.class); 
			List<OcKjSellerCustomsDeclaration> ulist = new ArrayList<OcKjSellerCustomsDeclaration>();
			for(KjSellerCustomsDeclarationModel e : this.list){
				OcKjSellerCustomsDeclaration d = new OcKjSellerCustomsDeclaration();
				d.setUid(e.getUid()); 
				d.setFlag(4);
				d.setUpdateTime(new Date());
				d.setRemark("rsync kjt success"); 
				ulist.add(d);
			}
			r.setSuccessNum(this.list.size()); 
			if(!entity.getCode().equals("0")){ // 取出错误的报关数据 
				List<ErrorEntity> date = entity.getDate();
				if(date != null && date.size() != 0){
					for(ErrorEntity err : date){
						for(KjSellerCustomsDeclarationModel m : this.list){
							if(m.getKjtOrderCode().equals(err.getSOSysNo())){
								for(int i = 0 ; i < ulist.size(); i ++){
									if(m.getOrderCode().equals(ulist.get(i).getOrderCode())){ 
										ulist.get(i).setFlag(3); 
										ulist.get(i).setRemark(err.getDesc()); 
										break;
									}
								}
								break;
							}
						}
					}
				}	// 归纳错误数据完成
				r.setSuccessNum(this.list.size() - date.size()); 
			}
			
			for(OcKjSellerCustomsDeclaration u : ulist	){
				this.cdDao.updateSelective(u);
			}
			r.setProcessData(JSONObject.toJSONString(entity)); 
		} catch (Exception e) {
			String message = "响应消息体错误，响应数据解析异常，请联系跨境通，异常信息如下：\n" + ExceptionHelper.allExceptionInformation(e); 
			logger.error(message);
			r.setSuccessNum(0);
			r.setProcessData(message); 
		}
		
		return r; 
	}
	
	public void setList(List<KjSellerCustomsDeclarationModel> list) {
		this.list = list;
	}
	public void setCdDao(IOcKjSellerCustomsDeclarationDao cdDao) {
		this.cdDao = cdDao;
	}


	public String getRequestMethod() {
		return "Order.AcquireSOPayDeclare";
	}
}

// 构建跨境通请求实体类
class AcquireSOPayDeclareRequest{
	private Integer SOSysNo;
	private String PayTransNumber;
	private String PayTime;
	private Integer PayTypeSysNo;
	
	
	public Integer getSOSysNo() {
		return SOSysNo;
	}
	public void setSOSysNo(Integer sOSysNo) {
		SOSysNo = sOSysNo;
	}
	public String getPayTransNumber() {
		return PayTransNumber;
	}
	public void setPayTransNumber(String payTransNumber) {
		PayTransNumber = payTransNumber;
	}
	public String getPayTime() {
		return PayTime;
	}
	public void setPayTime(String payTime) {
		PayTime = payTime;
	}
	public Integer getPayTypeSysNo() {
		return PayTypeSysNo;
	}
	public void setPayTypeSysNo(Integer payTypeSysNo) {
		PayTypeSysNo = payTypeSysNo;
	}
}


class AcquireSOPayDeclareResponse{
	private String code;
	private String desc;
	private List<ErrorEntity> date;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<ErrorEntity> getDate() {
		return date;
	}
	public void setDate(List<ErrorEntity> date) {
		this.date = date;
	}
}

class ErrorEntity{
	private Integer SOSysNo;
	private String desc;
	public Integer getSOSysNo() {
		return SOSysNo;
	}
	public void setSOSysNo(Integer sOSysNo) {
		SOSysNo = sOSysNo;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}

































