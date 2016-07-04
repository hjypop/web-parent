package com.hjy.selleradapter.job;

import org.quartz.JobExecutionContext;

import com.hjy.helper.WebHelper;
import com.hjy.quartz.job.RootJob;
import com.hjy.selleradapter.kjt.RsyncGetKjtProductIdByDate;
/**
 * @descriptions 从跨境通查询信息变化的商品并入库 | properties配置信息核对完成
 * 
 * @date 2016年6月24日下午3:37:36
 * @author Yangcl
 * @version 1.0.1
 */
public class JobGetChangeProductFromKJT extends RootJob {

	public void doExecute(JobExecutionContext context) {
		String Lockcode = WebHelper.getInstance().addLock(10000,"cpkjt15269");
		//从跨境通查询信息变化的商品入库
		new RsyncGetKjtProductIdByDate().doRsync();
		WebHelper.getInstance().unLock(Lockcode);
	}
}
