package com.hjy.selleradapter.job;

import org.quartz.JobExecutionContext;

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

	@Override
	public void doExecute(JobExecutionContext context) {
		

	}

}
