package com.hjy.selleradapter.job;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;

import com.hjy.helper.WebHelper;
import com.hjy.quartz.job.RootJob;
import com.hjy.selleradapter.minspc.RsyncOrderOutput;

/**
 * 
 * @title: com.hjy.selleradapter.job.JobForOrderOutput.java 
 * @description: 通知经销商订单已出关
 *
 * @author Yangcl
 * @date 2016年9月7日 下午1:36:01 
 * @version 1.0.0
 */
public class JobForOrderOutput extends RootJob {

	@Override
	public void doExecute(JobExecutionContext context) {
		String lockCode = WebHelper.getInstance().addLock(1000 , "JobForOrderOutput");	// 分布式锁定
		if (StringUtils.isNotBlank(lockCode)) {
			try {
				new RsyncOrderOutput().doRsync();
			} catch (Exception e) {
				e.printStackTrace();  
			}finally {
				WebHelper.getInstance().unLock(lockCode);
			}
		}
	}

}
