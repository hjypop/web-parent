package com.hjy.system.init;

import org.apache.commons.lang.StringUtils;

import com.hjy.cache.CacheDefine;
import com.hjy.system.TopConst;

/**
 * alias InitZapcom
 * webcore初始化类
 *
 */
public class WebCoreInit extends RootInit {

	public boolean onInit() {

		return initVersion();
	}

	/*
	 * 初始化版本号
	 */
	public boolean initVersion() {

		TopConst.CONST_CURRENT_MODEL = getConfig("webcore.model");

		String sVersion = getConfig("webcore.version");

		String sCustVersion = getConfig("webcore.version_" + TopConst.CONST_CURRENT_MODEL);
		if (StringUtils.isNotBlank(sCustVersion)) {
			sVersion = sCustVersion;
		}

		TopConst.CONST_CURRENT_VERSION = sVersion;

		TopConst.CONST_LOG_TYPE = getConfig("webcore.log_type");

		TopConst.CONST_LOG_ADDRESS = getConfig("webcore.log_address");

		return true;

	}

	@Override
	public boolean onDestory() {

		// 清除所有缓存
		new CacheDefine().removeAllCache();

		return true;
	}

}
