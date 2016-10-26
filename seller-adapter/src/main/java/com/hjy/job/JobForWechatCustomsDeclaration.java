package com.hjy.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;

import com.hjy.quartz.job.RootJob;

/**
 * @description: 跨境商户的订单进行报关处理|处理oc_kj_seller_customs_declaration表中
 * 	type=‘wechat’的所有记录，对其进行报关处理
 * 	微信报关处理
 * @author Yangcl
 * @date 2016年10月26日 下午2:29:14 
 * @version 1.0.0
 */
public class JobForWechatCustomsDeclaration extends RootJob{

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



























