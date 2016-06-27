package com.hjy.selleradapter.job;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;

import com.hjy.model.MDataMap;
import com.hjy.quartz.job.RootJob;
import com.hjy.selleradapter.service.TraceOrder;

/**
 * 
 * 类: JobForTraceOrder <br>
 * 描述: 定时同步订单状态 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月27日 下午3:51:51
 */
public class JobForOrderStatus extends RootJob {

	@Override
	public void doExecute(JobExecutionContext context) {
		// List<MDataMap> list =
		// DbUp.upTable("oc_order_kjt_list").queryAll("order_code_out", "",
		// "sostatus in (0,1,4,41,45) and order_code_out<>'' ", null);
		List<MDataMap> list = null;

		List<Long> idList = new ArrayList<Long>(20);

		for (int i = 0; i < list.size(); i++) {
			String order_code_out = list.get(i).get("order_code_out");
			if (StringUtils.isNotBlank(order_code_out)) {
				idList.add(Long.valueOf(order_code_out));
				if ((i != 0 && (i + 1) % 20 == 0) || (list.size() - 1 == i)) {
					TraceOrder traceOrder = new TraceOrder();
					traceOrder.upRsyncRequest()
							.setSalesChannelSysNo(Long.valueOf(getConfig("groupcenter.rsync_kjt_SaleChannelSysNo")));
					traceOrder.upRsyncRequest().setOrderIds(idList);
					traceOrder.doRsync();
					idList.clear();
				}
			}
		}
	}

}
