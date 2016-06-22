package com.hjy.system.config;

import org.apache.commons.lang.StringUtils;

import com.hjy.helper.FormatHelper;

/**
 * alias TopUp
 * 获取类 取该取的
 * @author srnpr
 *
 */
public class ConfigVisitor {

	/**
	 * alias upConfig
	 * 获取配置
	 * @param sKey
	 * @return
	 */
	public static String getConfig(String sKey) {
		
		return InitConfig.Instance.getValue(sKey);
	}

	private final static InitInfo topInfo = new InitInfo();

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
	
	//有对info/*.properties的访问支持了,下面不知何用
//	/**
//	 * 配置
//	 */
//	private final static ConfigMap configMap=new ConfigMap();
//	/**
//	 * @param sKey
//	 * @return
//	 */
//	public static MStringMap upConfigMap(String sKey) {
//		return configMap.upValue(sKey);
//	}
}
