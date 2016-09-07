package com.hjy.selleradapter.job;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;

import com.hjy.helper.WebHelper;
import com.hjy.quartz.job.RootJob;
import com.hjy.selleradapter.minspc.RsyncVoidOrder;

/**
 * 
 * @title: com.hjy.selleradapter.job.JobForVoidOrder.java 
 * @description: 订单作废
 *
 * @author Yangcl
 * @date 2016年9月7日 下午1:34:02 
 * @version 1.0.0
 */
public class JobForVoidOrder extends RootJob {

	@Override
	public void doExecute(JobExecutionContext context) {
		String lockCode = WebHelper.getInstance().addLock(1000 , "JobForVoidOrder");	// 分布式锁定
		if (StringUtils.isNotBlank(lockCode)) {
			try {
				new RsyncVoidOrder().doRsync();
			} catch (Exception e) {
				e.printStackTrace();  
			}finally {
				WebHelper.getInstance().unLock(lockCode);
			}
		}
	}

}
