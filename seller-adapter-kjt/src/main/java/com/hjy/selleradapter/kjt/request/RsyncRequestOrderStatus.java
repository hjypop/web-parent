package com.hjy.selleradapter.kjt.request;

import java.util.List;

import com.hjy.iface.IRsyncRequest;

/**
 * 
 * 类: RsyncRequestTraceOrder <br>
 * 描述: 定时同步跨境通订单状态请求参数类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月27日 下午3:15:35
 */
public class RsyncRequestOrderStatus implements IRsyncRequest {

	private List<Long> OrderIds;
	private long SalesChannelSysNo;

	public List<Long> getOrderIds() {
		return OrderIds;
	}

	public void setOrderIds(List<Long> orderIds) {
		OrderIds = orderIds;
	}

	public long getSalesChannelSysNo() {
		return SalesChannelSysNo;
	}

	public void setSalesChannelSysNo(long salesChannelSysNo) {
		SalesChannelSysNo = salesChannelSysNo;
	}

}
