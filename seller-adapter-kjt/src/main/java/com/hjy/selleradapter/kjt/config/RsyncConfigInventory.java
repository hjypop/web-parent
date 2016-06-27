package com.hjy.selleradapter.kjt.config;

import com.hjy.iface.IRsyncConfig;
import com.hjy.iface.IRsyncDateCheck;

/**
 * alias RsyncResponseGetKjtProductInventoryById<br>
 * 类: RsyncConfigInventory <br>
 * 描述: 跨境通商品库存同步配置 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月27日 下午5:45:55
 */
public class RsyncConfigInventory implements IRsyncDateCheck, IRsyncConfig {

	public String getRsyncTarget() {
		return "Inventory.ChannelQ4SBatchGet";
	}

	public String getBaseStartTime() {
		return "2015-05-01 00:00:00";
	}

	public int getMaxStepSecond() {
		return 3600 * 36;
	}

	public int getBackSecond() {
		return 0;
	}

}
