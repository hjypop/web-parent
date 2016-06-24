package com.hjy.selleradapter.kjt.config;

import com.cmall.groupcenter.groupface.IRsyncDateCheck;
import com.cmall.groupcenter.homehas.config.RsyncConfigRsyncBase;

/**
 * 同步的配置选项
 * 
 * @author xiegj
 * 
 */
public class RsyncConfigGetKjtProductIdByDate extends RsyncConfigRsyncBase implements IRsyncDateCheck {

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
