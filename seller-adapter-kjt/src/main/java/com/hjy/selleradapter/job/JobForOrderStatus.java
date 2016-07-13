package com.hjy.selleradapter.job;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;

import com.hjy.annotation.Inject;
import com.hjy.entity.OcOrderKjtList;
import com.hjy.quartz.job.RootJob;
import com.hjy.selleradapter.kjt.RsyncOrderStatus;
import com.hjy.service.IOcOrderKjtListService;

/**
 * 
 * 类: JobForTraceOrder <br>
 * 描述: 定时同步订单状态 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月27日 下午3:51:51
 */
public class JobForOrderStatus extends RootJob {
	@Inject
	private IOcOrderKjtListService service;

	@Override
	public void doExecute(JobExecutionContext context) {

		String sostatus = "0,1,4,41,45";
		List<OcOrderKjtList> list = service.findOrderListByStatus(sostatus);
		List<Long> idList = new ArrayList<Long>(20);
		for (int i = 0; i < list.size(); i++) {
			String order_code_out = list.get(i).getOrderCodeOut();
			if (StringUtils.isNotBlank(order_code_out)) {
				idList.add(Long.valueOf(order_code_out));
				if ((i != 0 && (i + 1) % 20 == 0) || (list.size() - 1 == i)) {
					RsyncOrderStatus traceOrder = new RsyncOrderStatus();
					//渠道号
					traceOrder.upRsyncRequest().setSalesChannelSysNo(Long.valueOf(getConfig("seller_adapter_kjt.kjt_SaleChannelSysNo")));
					traceOrder.upRsyncRequest().setOrderIds(idList);
					traceOrder.doRsync();
					idList.clear();
				}
			}
		}
	}
}
