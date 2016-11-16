package com.hjy.selleradapter.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;

import com.hjy.annotation.Inject;
import com.hjy.dao.order.IOcKjSellerSeparateOrderDao;
import com.hjy.dto.minspc.VoidDto;
import com.hjy.entity.order.OcKjSellerSeparateOrder;
import com.hjy.helper.WebHelper;
import com.hjy.quartz.job.RootJob;
import com.hjy.selleradapter.minspc.RsyncVoidOrder;

/**
 * 
 * @title: com.hjy.selleradapter.job.JobForVoidOrder.java 
 * @description: 订单作废|此定时任务15分钟执行一次
 *
 * @author Yangcl
 * @date 2016年9月7日 下午1:34:02 
 * @version 1.0.0
 */
public class JobForVoidOrder extends RootJob {

	@Inject 
	private IOcKjSellerSeparateOrderDao kjSellerSeparateOrderDao; 
	
	
	@Override
	public void doExecute(JobExecutionContext context) {
		String lockCode = WebHelper.getInstance().addLock(1000 , "JobForVoidOrder");	// 分布式锁定
		if (StringUtils.isNotBlank(lockCode)) {
			try {
				Date date = new Date();												// 2016-09-18 16:28:26        当前时间  			
				VoidDto dto = new VoidDto();
				dto.setSellerCode(getConfig("seller_adapter_minspc.small_seller_code"));   // "SF03100646"
				dto.setSellerStatus("0");
				dto.setStartTime(this.getHour(date, -1));		// 2016-09-18 15:00:00   当前时间点向前推一小时
				dto.setEndTime(this.getHour(date, 0)); 		// 2016-09-18 16:00:00   当前时间点，取整点时间
				// 根据 oc_orderinfo 表的update_time，取当前时间的整点时间和前一小时的整点时间，做时间段查询。
				List<OcKjSellerSeparateOrder> voidList = kjSellerSeparateOrderDao.selectVoidOrderInfo(dto);  
				if(voidList != null && voidList.size() != 0){
					for(OcKjSellerSeparateOrder e : voidList){
						RsyncVoidOrder rvo = new RsyncVoidOrder();
						rvo.setUpdateInfo(e); 
						rvo.doRsync();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();  
			}finally {
				WebHelper.getInstance().unLock(lockCode);
			}
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

}


















