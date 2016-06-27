package com.hjy.selleradapter.kjt.request;

import com.hjy.iface.IRsyncRequest;

/**
 * 
 * 类: RsyncRequestInventory <br>
 * 描述: 跨境通商品库存同步请求参数类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月27日 下午5:45:08
 */
public class RsyncRequestInventory implements IRsyncRequest {
	private String ProductIDs;

	private String SaleChannelSysNo = "";

	public String getProductIDs() {
		return ProductIDs;
	}

	public void setProductIDs(String productIDs) {
		ProductIDs = productIDs;
	}

	public String getSaleChannelSysNo() {
		return SaleChannelSysNo;
	}

	public void setSaleChannelSysNo(String saleChannelSysNo) {
		SaleChannelSysNo = saleChannelSysNo;
	}
}
