package com.hjy.selleradapter.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;

import com.hjy.annotation.Inject;
import com.hjy.dao.order.IOcKjSellerCustomsDeclarationDao;
import com.hjy.helper.WebHelper;
import com.hjy.model.KjSellerCustomsDeclarationModel;
import com.hjy.quartz.job.RootJob;
import com.hjy.selleradapter.kjt.RsyncAcquireSOPayDeclare;

/**
 * @description: sb跨境通分销订单支付申报上报接口
 * @接口标识 Order.AcquireSOPayDeclare 
 * 
 * @author Yangcl
 * @date 2016年10月28日 上午10:41:16 
 * @version 1.0.0
 */
public class JobForAcquireSOPayDeclare extends RootJob {

	@Inject
	private IOcKjSellerCustomsDeclarationDao dao;
	
	private String startTime;
	private String endTime;
	
	public JobForAcquireSOPayDeclare() {
	}

	public JobForAcquireSOPayDeclare(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}



	public void doExecute(JobExecutionContext context) {
		String lockCode = WebHelper.getInstance().addLock(1000 , "JobForAcquireSOPayDeclare");	// 分布式锁定
		if (StringUtils.isNotBlank(lockCode)) {
			try {
				if(StringUtils.isAnyBlank(this.startTime , this.endTime)){  // 非手动执行该定时任务 
					Date date = new Date(); 								 // 2016-09-18 16:26:08
					this.startTime = this.getHour(date , -1);  // 2016-09-18 15:00:00         带同步订单的开始时间
					this.endTime = this.getHour(date , 0);	   // 2016-09-18 16:00:00		 带同步订单的结束时间
				}
				if(this.compareDate(this.startTime , this.endTime)){  // 开始时间大于结束时间则返回
					return ;
				}
				Map<String , String> map = new HashMap<String , String>(3);
				map.put("sellerCode", getConfig("seller_adapter_kjt.seller_code_KJT"));
				map.put("startTime" , this.startTime);
				map.put("endTime" , this.endTime); 
				List<KjSellerCustomsDeclarationModel> list = dao.getKjSellerCustomsDeclarationList(map);
				if(list != null && list.size() != 0){
					RsyncAcquireSOPayDeclare rsync = new RsyncAcquireSOPayDeclare();
					rsync.setList(list);
					rsync.setCdDao(dao); 
					rsync.doRsync();
				}
				
				
			}catch (Exception e) {
				e.printStackTrace();  
			}finally {
				WebHelper.getInstance().unLock(lockCode);
			}
		}else{
			// 分布式锁定中，请解锁
		}
		
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




























