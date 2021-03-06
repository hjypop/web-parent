package com.hjy.selleradapter.kjt.config;

import com.hjy.iface.IRsyncConfig;
import com.hjy.iface.IRsyncDateCheck;

/**
 * 同步的配置选项
 * 
 * @author xiegj
 * 
 */
public class RsyncConfigGetKjtProductIdByDate implements IRsyncConfig,IRsyncDateCheck {

	public String getRsyncTarget() {

		return "Product.ProductIDGetQuery";
	}

	
	public String getBaseStartTime() {
		return "2015-05-01 00:00:00";
	}

	public int getMaxStepSecond() {
		return 3600 * 36;
	}

	public int getBackSecond() {
		return 3600;
	}

}
