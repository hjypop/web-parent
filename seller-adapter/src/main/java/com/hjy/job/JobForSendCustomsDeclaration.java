package com.hjy.job;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.Inject;
import com.hjy.dao.order.IOcKjSellerCustomsDeclarationDao;
import com.hjy.dao.webcore.IWcSellerinfoDao;
import com.hjy.entity.order.OcKjSellerCustomsDeclaration;
import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.helper.PureNetUtil;
import com.hjy.helper.SignHelper;
import com.hjy.helper.WebHelper;
import com.hjy.quartz.job.RootJob;
import com.hjy.request.customsDeclaration.Customs;
import com.hjy.request.customsDeclaration.RequestParam;

/**
 * @description: 跨境商户的订单进行报关处理|处理oc_kj_seller_customs_declaration表中
 * 	的所有记录，对其进行报关处理|此处调用.net组王炳春负责的字符网关接口
 * 	
 * @author Yangcl
 * @date 2016年10月26日 下午2:29:14 
 * @version 1.0.0
 */
public class JobForSendCustomsDeclaration extends RootJob{
	
	private static Logger logger = Logger.getLogger(JobForSendCustomsDeclaration.class);
	
	@Inject
	private IOcKjSellerCustomsDeclarationDao dao;
	@Inject 
	private IWcSellerinfoDao wcSellerinfoDao;
	
	
	private String startTime;
	private String endTime;
	
	public JobForSendCustomsDeclaration() {
	}

	public JobForSendCustomsDeclaration(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}


	public void doExecute(JobExecutionContext context) {
		String lockCode = WebHelper.getInstance().addLock(1000 , "JobForSendCustomsDeclaration");	// 分布式锁定
		if (StringUtils.isNotBlank(lockCode)) {
			try {
				if(StringUtils.isAnyBlank(this.startTime , this.endTime)){  // 非手动执行该定时任务 
					Date date = new Date(); 								 // 2016-09-18 16:26:08
					this.startTime = this.getHour(date , -1);   // 2016-09-18 15:00:00         带同步订单的开始时间
					this.endTime = this.getHour(date , 0);	     // 2016-09-18 16:00:00		  带同步订单的结束时间
				}
				if(this.compareDate(this.startTime , this.endTime)){  // 开始时间大于结束时间则返回
					return ;
				}
				List<WcSellerinfo> sscList = wcSellerinfoDao.getCustomsDeclarationSellerList();  
				if(sscList == null || sscList.size() == 0){
					return ; 
				}
				Map<String , String> kjsellerMap = new HashMap<String , String>();
				for(WcSellerinfo s : sscList){
					kjsellerMap.put(s.getSellerCode() , s.getSellerCustomNumber() + "@" + s.getSellerCustomLocation());  
				}
				
				Map<String , String> map = new HashMap<String , String>(3);
//				map.put("type", "alipay");		 // 这里需要去掉这个条件 
				map.put("startTime" , this.startTime);
				map.put("endTime" , this.endTime); 
				List<OcKjSellerCustomsDeclaration> list = dao.getRequestList(map);
				if(list != null && list.size() > 0){
					for(OcKjSellerCustomsDeclaration e : list){ 
						RequestParam req = new RequestParam();
						req.setC_mid(this.getConfig("seller_adapter.pay_ichsy_mid")); 
						SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
						String time = sdf.format(new Date());
						req.setC_ymd(time); 
						Customs c = new Customs(); 
						c.setC_customs(Integer.valueOf( kjsellerMap.get(e.getSellerCode()).split("@")[1]) ); 
						c.setC_customs_amount(e.getDueMoney()); 
						c.setC_customs_code(kjsellerMap.get(e.getSellerCode()).split("@")[0]); 
						c.setC_customs_name(e.getSellerName());
						c.setC_customs_order(e.getOrderCode() + time); 
						c.setC_idno(e.getIdcard());
						c.setC_idnotype("IDCARD");
						c.setC_moneytype(1);
						c.setC_name(e.getAuthName());
						c.setC_order(e.getBigOrderCode());
						BigDecimal c_product_fee = e.getDueMoney().subtract(e.getTransportMoney());
						c.setC_product_fee(c_product_fee);
						c.setC_sub_order(e.getOrderCode());
						c.setC_transport_fee(e.getTransportMoney()); 
						req.setCustoms(c); 
						
						ScdResponse ar = this.getResponse(this.sendRequest(req));
						OcKjSellerCustomsDeclaration u = new OcKjSellerCustomsDeclaration();
						u.setUid(e.getUid()); 
						u.setUpdateTime(new Date()); 
						if(ar.getResult() == 1 && ar.getStatus() == 1){ // 请求处理成功
							u.setFlag(1);
							u.setRemark("报关成功"); 
							u.setTradeNo(ar.getTradeNo()); 
						}else{
							u.setFlag(2); 
							u.setRemark(ar.getDesc());
						}
						dao.updateSelective(u);
					}
				}
			}catch (Exception e) {
				e.printStackTrace();  
			}finally {
				WebHelper.getInstance().unLock(lockCode);
			}
		}else{		// 分布式锁定中，请解锁  
			logger.error("JobForSendCustomsDeclaration.java  分布式锁定中，请解锁"); 
		}
	}
	
	// 获取支付网关返回的响应数据
	private ScdResponse getResponse(String json){
		
		JSONObject response = JSONObject.parseObject(json);
		String desc = response.getString("descrption");
		Integer result = response.getInteger("result");
		JSONObject customs = JSONObject.parseObject(JSONObject.parseObject(JSONObject.parseObject(response.getString("V1")).getString("Customs_CreateOrder")).getString("customs"));
		Integer status = customs.getInteger("pStatus");
		String tradeNo = customs.getString("tradeNo");
		return new ScdResponse(result, status, tradeNo, desc);
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
		param.put("", JSONObject.toJSONString(req));  // TODO 这里怎么解决？？？？？
        param.put("sign", this.getSign(JSONObject.toJSONString(req))); 
		return param;
	}
	
	private String getSign(String json){
		String sign = "";
		try { 
			String str = json  + "&" + this.getConfig("seller_adapter.pay_ichsy_pass");
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


class ScdResponse{
	/*
	  操作结果码，整形。
	  1：成功
	  10：未找到商户
	  11：账户被冻结
	  12：未登陆或者登陆过期
	  13：没有访问权限
	  2：签名错误
	  3：订单号错误
	  31：未找到订单
	  32：订单已存在
	  4：参数错误
	  50：外部接口返回错误
	  51：不支持的操作
	  6：不存在
	  7：配置错误
	  8：余额不足
	  9：数据库错误
	  99：其它错误
	  100：失败  
	  */
	private Integer result;
	
	/*报关状态
	    0:未申报
	    10:申报已提交
	    3:申报中
	    1:申报成功
	    2:申报失败
	    4:异常
    */
	private Integer status;
	
	// 报关成功返回的序列号
	private String tradeNo;
	
	private String desc;

	public ScdResponse(Integer result, Integer status, String tradeNo, String desc) {
		this.result = result;
		this.status = status;
		this.tradeNo = tradeNo;
		this.desc = desc;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	
}



























