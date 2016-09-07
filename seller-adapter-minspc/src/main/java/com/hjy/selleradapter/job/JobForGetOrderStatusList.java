package com.hjy.selleradapter.job;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;

import com.hjy.helper.WebHelper;
import com.hjy.quartz.job.RootJob;
import com.hjy.selleradapter.minspc.RsyncOrderStatusList;

/**
 * 
 * @title: com.hjy.selleradapter.job.JobForGetOrderStatusList.java 
 * @description: 批量获取订单状态
 *
 * @author Yangcl
 * @date 2016年9月7日 下午1:26:42 
 * @version 1.0.0
 */
public class JobForGetOrderStatusList extends RootJob {

	@Override
	public void doExecute(JobExecutionContext context) {
		String lockCode = WebHelper.getInstance().addLock(1000 , "JobForGetOrderStatusList");	// 分布式锁定
		if (StringUtils.isNotBlank(lockCode)) {
			try {
				new RsyncOrderStatusList().doRsync();
			} catch (Exception e) {
				e.printStackTrace();  
			}finally {
				WebHelper.getInstance().unLock(lockCode);
			}
		}
	}

}
