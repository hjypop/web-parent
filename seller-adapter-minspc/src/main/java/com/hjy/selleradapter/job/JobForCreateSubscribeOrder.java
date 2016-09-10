package com.hjy.selleradapter.job;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;

import com.hjy.helper.WebHelper;
import com.hjy.quartz.job.RootJob;
import com.hjy.selleradapter.minspc.RsyncSubscribeOrder;

/**
 * @title: com.hjy.job.JobForCreateSubscribeOrder.java 
 * @description: 生成订阅订单（并发送海关）
 *
 * @author Yangcl
 * @date 2016年9月6日 下午2:24:05 
 * @version 1.0.0
 */
public class JobForCreateSubscribeOrder extends RootJob {
	
	@Override  
	public void doExecute(JobExecutionContext context) {
		String lockCode = WebHelper.getInstance().addLock(1000 , "JobForCreateSubscribeOrder");	// 分布式锁定
		if (StringUtils.isNotBlank(lockCode)) {
			try {
				new RsyncSubscribeOrder().doRsync();
			} catch (Exception e) {
				e.printStackTrace();  
			}finally {
				WebHelper.getInstance().unLock(lockCode);
			}
		}
	}



}