package com.hjy.selleradapter.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import org.quartz.JobExecutionContext;

import com.hjy.annotation.Inject;
import com.hjy.dao.user.IUcSellerinfoDao;
import com.hjy.dao.webcore.IWcSellerinfoDao;
import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.model.SellerInfoModel;
import com.hjy.quartz.job.RootJob;

/**
 * @description: 定时同步 uc_sellerinfo、uc_seller_info_extend两个表中的部分有效字段到wc_sellerinfo表中
 * 
 * @group seller-adapter-openapi 
 * 
 * @author Yangcl
 * @date 2017年2月21日 下午5:48:59 
 * @version 1.0.0
 */
public class JobForRsyncSellerInfo extends RootJob {

	@Inject
	private IWcSellerinfoDao wcSellerinfoDao;   
	@Inject 
	private IUcSellerinfoDao ucSellerinfoDao;
	
	
	
	@Override
	public void doExecute(JobExecutionContext context) {
		String formate_ = "yyyy-MM-dd 00:00:00";
		Map<String , String> map = new HashMap<String , String>();
		map.put("startTime", this.getNextDate(new Date(), 0, formate_));
		map.put("endTime", this.getNextDate(new Date(), 1, formate_)); 
		List<SellerInfoModel> mlist = wcSellerinfoDao.selectUcSellerInfo(map);
		if(mlist != null && mlist.size() != 0){
			List<WcSellerinfo> list = wcSellerinfoDao.queryPage(null); 
			if(list != null && list.size() != 0){
				
			}else{
				// 全部插入到表中 
			}
		}

	}

	private String getNextDate(Date date , int flag , String formate_){  
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.DATE , 1);//把日期往后增加一天.整数往后推,负数往前移动
		 date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		 SimpleDateFormat formatter = new SimpleDateFormat(formate_);
		 
		 return formatter.format(date);
	}
}
















