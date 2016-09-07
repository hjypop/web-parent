package com.hjy.selleradapter.job;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;

import com.hjy.helper.WebHelper;
import com.hjy.quartz.job.RootJob;
import com.hjy.selleradapter.minspc.RsyncProductList;

/**
 * 
 * @title: com.hjy.selleradapter.job.JobForGetProductList.java 
 * @description: 获取所有订阅产品 
 *
 * @author Yangcl
 * @date 2016年9月7日 下午1:33:35 
 * @version 1.0.0
 */
public class JobForGetProductList extends RootJob {

	@Override
	public void doExecute(JobExecutionContext context) {
		String lockCode = WebHelper.getInstance().addLock(1000 , "JobForGetProductList");	// 分布式锁定
		if (StringUtils.isNotBlank(lockCode)) {
			try {
				new RsyncProductList().doRsync();
			} catch (Exception e) {
				e.printStackTrace();  
			}finally {
				WebHelper.getInstance().unLock(lockCode);
			}
		}
	}

}
