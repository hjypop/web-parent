package com.hjy.selleradapter.job;

import com.hjy.iface.IBaseResult;
import com.hjy.model.MWebResult;
import com.hjy.quartz.job.RootJobForExec;
import com.hjy.quartz.model.ConfigJobExec;
import com.hjy.service.impl.OrderForKJT;

/**
 * 异步定时生成发货单
 * 
 * @author jlin
 *
 */
public class JobForOrderSoCreate extends RootJobForExec {

	@Override
	public IBaseResult execByInfo(String sInfo) {

		MWebResult mWebResult = new MWebResult();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		OrderForKJT forKJT = new OrderForKJT();
		if (!forKJT.rsyncOrder(sInfo)) {
			mWebResult.inErrorMessage(100009135);
		}

		return mWebResult;
	}

	private static ConfigJobExec config = new ConfigJobExec();

	@Override
	public ConfigJobExec getConfig() {
		config.setExecType("449746990003");
		config.setMaxExecNumber(20);
		return config;
	}
}
