package com.hjy.base;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;

import com.hjy.global.ConfigVisitor;
import com.hjy.helper.FormatHelper;

/**
 * 基类
 * 
 * @author HJY
 */
public abstract class BaseClass {

	/**
	 * @param lInfoId
	 *            默认请写0 否则读取配置文件
	 * @param sParms
	 *            替换参数
	 */
	public void bLogInfo(int iInfoCode, Object... sParms) {
		LogFactory.getLog(this.getClass())
				.info("[" + String.valueOf(iInfoCode) + "] " + ConfigVisitor.getLogInfo(iInfoCode, sParms));
	}

	/**
	 * 错误日志
	 * 
	 * @param lInfoId
	 *            默认请写0 否则读取配置文件
	 * @param sParms
	 *            替换参数
	 */
	public void bLogError(int iInfoCode, Object... sParms) {
		LogFactory.getLog(this.getClass())
				.error("[" + String.valueOf(iInfoCode) + "] " + ConfigVisitor.getLogInfo(iInfoCode, sParms));
	}

	/**
	 * debug日志
	 * 
	 * @param iInfoCode
	 * @param sParms
	 */
	public void bLogDebug(int iInfoCode, Object... sParms) {
		LogFactory.getLog(this.getClass())
				.debug("[" + String.valueOf(iInfoCode) + "] " + ConfigVisitor.getLogInfo(iInfoCode, sParms));
	}
	
	/**
	 * 警告日志
	 * 
	 * @param lid
	 * @param oMessage
	 */
	public void bLogWarn(int lid, Object... oMessage) {
		LogFactory.getLog(this.getClass())
			.warn("[TopBase-" + String.valueOf(lid) + "] " + StringUtils.join(oMessage));
	}

	/**
	 * @param sKey
	 *            配置主键
	 * @return 配置内容字符串
	 */
	public String bConfig(String sKey) {
		return ConfigVisitor.getConfig(sKey);
	}

	/**
	 * @param lInfoId
	 *            文本编号
	 * @param sParms
	 *            拼接字符串
	 * @return
	 */
	public String bInfo(long iInfoCode, Object... sParms) {

		return FormatHelper.formatString(ConfigVisitor.getInfo(iInfoCode), sParms);
	}
	

	/**
	 * 类日志记录
	 * 
	 * @param clazz
	 * @param objects
	 */
	/**
	 * @param clazz
	 * @param objects
	 */
	public static void ClassLogInfo(Class<?> clazz, Object... objects) {
		LogFactory.getLog(clazz).info(StringUtils.join(objects));
	}

	/**
	 * 类日志警告
	 * 
	 * @param clazz
	 * @param objects
	 */
	public static void classLogWarn(Class<?> clazz, Object... objects) {
		LogFactory.getLog(clazz).warn(StringUtils.join(objects));
	}
}
