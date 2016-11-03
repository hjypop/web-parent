package com.core.system.config;

import org.apache.commons.lang.StringUtils;

import com.core.helper.FormatHelper;
import com.core.model.MStringMap;

/**
 * alias TopUp
 * 获取类 取该取的
 * @author srnpr
 *
 */
public class PropVisitor {

	/**
	 * alias upConfig
	 * 获取配置
	 * @param sKey
	 * @return
	 */
	public static String getConfig(String sKey) {
		return PropConfig.Instance.getValue(sKey);
	}

	private final static PropInfo topInfo = new PropInfo();

	/**
	 * alias upInfo
	 * 获取定义
	 * @param lInfoId
	 * @return
	 */
	public static String getInfo(long iInfoCode) {
		return topInfo.getValue(iInfoCode);
	}
	
	/** 
	 * alias upLogInfo
	 * 格式化日志内容
	 * @param iInfoCode
	 * @param sParms
	 * @return
	 */
	public static String getLogInfo(int iInfoCode, Object... sParms)
	{
		return (iInfoCode<1?StringUtils.join(sParms):FormatHelper.formatString(getInfo(iInfoCode), sParms));
	}
	
	
	/**
	 * 配置
	 */
	private final static ConfigMap configMap=new ConfigMap();
	/**
	 * @param sKey
	 * @return
	 */
	public static MStringMap getConfigMap(String sKey) {
		return configMap.getValue(sKey);
	}
}
