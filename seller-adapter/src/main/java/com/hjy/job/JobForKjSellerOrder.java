package com.hjy.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;

import com.hjy.annotation.Inject;
import com.hjy.dao.IJobExectimerDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.dto.OrderinfoDto;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.helper.WebHelper;
import com.hjy.pojo.entity.system.JobExectimer;
import com.hjy.quartz.job.RootJob;

/**
 * 
 * @title: com.hjy.job.JobForKjSellerOrder.java 
 * @description: 将所有跨境商户的订单 从oc_orderinfo表中导入到 job_exectimer 表中。
 * 							 其导入订单的依据为：该项目配置文件中所加入的kj_seller_info。
 * 
 * 							 注意！此定时任务每小时执行一次
 * 
 * @author Yangcl
 * @date 2016年9月18日 下午2:54:53 
 * @version 1.0.0
 */
public class JobForKjSellerOrder extends RootJob {

	private String orderCode = null;

	public JobForKjSellerOrder() {
	}
	
	/**
	 * @description: 为手动调用定时任务留出扩展  
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月18日 下午5:38:09 
	 * @version 1.0.0.1
	 */
	public JobForKjSellerOrder(String orderCode) {
		this.orderCode = orderCode;
	}


	@Inject
	private IOcOrderinfoDao orderinfoDao;
	@Inject 
	private IJobExectimerDao jobExectimerDao;
	
	
	public void doExecute(JobExecutionContext context) {
		String lockCode = WebHelper.getInstance().addLock(1000 , "JobForKjSellerOrder");	// 分布式锁定
		if (StringUtils.isNotBlank(lockCode)) {
			try {
				this.executeByNomal();
			} catch (Exception e) {
				e.printStackTrace();  
			}finally {
				WebHelper.getInstance().unLock(lockCode);
			}
		}
		
	}
	
	
	/**
	 * @description: 执行常规定时任务  
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月18日 下午4:30:06 
	 * @version 1.0.0.1
	 */
	private void executeByNomal(){
		String kjSellerInfo = getConfig("seller_adapter.kj_seller_info");
		
		String [] ksiArray = kjSellerInfo.split(",");
		for(int i = 0 ; i < ksiArray.length ; i ++){
			String execType = ksiArray[i].split("@")[1];    // 449746990014 and so on..
			String smallSellerCode = ksiArray[i].split("@")[0];
			Date date = new Date();											// 2016-09-18 16:28:26
			String startTime = this.getHour(date, -1);				// 2016-09-18 15:00:00
			String endTime = this.getHour(date, 0);				// 2016-09-18 16:00:00
			// 定位条件：order_status = '4497153900010002'  下单成功-未发货  即代表我平台已经付款的订单才同步过去
			// update_time between ''  and '' 
			List<OcOrderinfo> list = orderinfoDao.findExectimerOrderList(new OrderinfoDto(smallSellerCode , startTime , endTime)); 
			if(list != null && list.size() != 0){
				for(OcOrderinfo e : list){
					JobExectimer k = new JobExectimer();
					k.setExecType(execType);
					k.setExecInfo(e.getOrderCode()); 
					List<JobExectimer> list_ = jobExectimerDao.findByOrderCode(k);  // 先查库，看是否存在
					if(list_ == null || list_.size() == 0){
						k.setUid(WebHelper.getInstance().genUuid());
						k.setExecCode(WebHelper.getInstance().genUniqueCode("ET"));
						k.setCreateTime(new Date()); 
						k.setExecTime( getNextHour(new Date()) );  // 执行时间向后推一小时。
						k.setFlagSuccess(0); 
						k.setRemark("JobForKjSellerOrder" ); 
						k.setExecNumber(0);
						jobExectimerDao.insertSelective(k);
					}
				}
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
	
	private Date getNextHour(Date date){
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.HOUR , 1); 
		 return calendar.getTime();
	}
	
	

}






























