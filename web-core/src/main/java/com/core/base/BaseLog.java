package com.core.base;

import org.apache.commons.logging.LogFactory;

import com.core.helper.FormatHelper;
import com.core.system.config.PropVisitor;

public class BaseLog extends BaseClass {
	/**
	 * 记录日志信息
	 * 
	 * @param sClassName
	 * @param iInfoCode
	 * @param sParms
	 */
	public void logInfo(Class<?> clazz, int iInfoCode, Object... sParms) {
		LogFactory.getLog(clazz).info(formatLog(iInfoCode, sParms));
	}
	
	/**
	 * 记录日志信息
	 * 
	 * @param sClassName
	 * @param iInfoCode
	 * @param sParms
	 */
	public void logInfo(Class<?> clazz, String content) {
		LogFactory.getLog(clazz).info(content);
	}
	
	/**
	 * @param lInfoId
	 *            默认请写0 否则读取配置文件
	 * @param sParms
	 *            替换参数
	 */
	public void logInfo(int iInfoCode, Object... sParms) {
		LogFactory.getLog(this.getClass()).info(formatLog(iInfoCode, sParms));
	}
	
	/**
	 * @param lInfoId
	 *            默认请写0 否则读取配置文件
	 * @param sParms
	 *            替换参数
	 */
	public void logInfo(int iInfoCode) {
		LogFactory.getLog(this.getClass()).info(PropVisitor.getLogInfo(iInfoCode));
	}
	
	/**
	 * @param lInfoId
	 *            默认请写0 否则读取配置文件
	 * @param sParms
	 *            替换参数
	 */
	public void logInfo(String content) {
		LogFactory.getLog(this.getClass()).info(content);
	}
	
	
	/**
	 * 记录日志信息
	 * 
	 * @param sClassName
	 * @param iInfoCode
	 * @param sParms
	 */
	public void logError(Class<?> clazz, int iInfoCode, Object... sParms) {
		LogFactory.getLog(clazz).error(formatLog(iInfoCode, sParms));
	}
	
	/**
	 * 记录日志信息
	 * 
	 * @param sClassName
	 * @param iInfoCode
	 * @param sParms
	 */
	public void logError(Class<?> clazz, String content) {
		LogFactory.getLog(clazz).error(content);
	}
	
	/**
	 * @param lInfoId
	 *            默认请写0 否则读取配置文件
	 * @param sParms
	 *            替换参数
	 */
	public void logError(int iInfoCode, Object... sParms) {
		LogFactory.getLog(this.getClass()).error(formatLog(iInfoCode, sParms));
	}
	
	/**
	 * @param lInfoId
	 *            默认请写0 否则读取配置文件
	 * @param sParms
	 *            替换参数
	 */
	public void logError(int iInfoCode) {
		LogFactory.getLog(this.getClass()).error(PropVisitor.getLogInfo(iInfoCode));
	}
	
	/**
	 * @param lInfoId
	 *            默认请写0 否则读取配置文件
	 * @param sParms
	 *            替换参数
	 */
	public void logError(String content) {
		LogFactory.getLog(this.getClass()).error(content);
	}
	
	/**
	 * 记录日志信息
	 * 
	 * @param sClassName
	 * @param iInfoCode
	 * @param sParms
	 */
	public void logDebug(Class<?> clazz, int iInfoCode, Object... sParms) {
		LogFactory.getLog(clazz).debug(formatLog(iInfoCode, sParms));
	}
	
	/**
	 * 记录日志信息
	 * 
	 * @param sClassName
	 * @param iInfoCode
	 * @param sParms
	 */
	public void logDebug(Class<?> clazz, String content) {
		LogFactory.getLog(clazz).debug(content);
	}
	
	/**
	 * @param lInfoId
	 *            默认请写0 否则读取配置文件
	 * @param sParms
	 *            替换参数
	 */
	public void logDebug(int iInfoCode, Object... sParms) {
		LogFactory.getLog(this.getClass()).debug(formatLog(iInfoCode, sParms));
	}
	
	/**
	 * @param lInfoId
	 *            默认请写0 否则读取配置文件
	 * @param sParms
	 *            替换参数
	 */
	public void logDebug(int iInfoCode) {
		LogFactory.getLog(this.getClass()).debug(PropVisitor.getLogInfo(iInfoCode));
	}
	
	/**
	 * @param lInfoId
	 *            默认请写0 否则读取配置文件
	 * @param sParms
	 *            替换参数
	 */
	public void logDebug(String content) {
		LogFactory.getLog(this.getClass()).debug(content);
	}
	
	/**
	 * 记录日志信息
	 * 
	 * @param sClassName
	 * @param iInfoCode
	 * @param sParms
	 */
	public void logWarn(Class<?> clazz, int iInfoCode, Object... sParms) {
		LogFactory.getLog(clazz).warn(formatLog(iInfoCode, sParms));
	}
	
	/**
	 * 记录日志信息
	 * 
	 * @param sClassName
	 * @param iInfoCode
	 * @param sParms
	 */
	public void logWarn(Class<?> clazz, String content) {
		LogFactory.getLog(clazz).warn(content);
	}
	
	/**
	 * @param lInfoId
	 *            默认请写0 否则读取配置文件
	 * @param sParms
	 *            替换参数
	 */
	public void logWarn(int iInfoCode, Object... sParms) {
		LogFactory.getLog(this.getClass()).warn(formatLog(iInfoCode, sParms));
	}
	
	/**
	 * @param lInfoId
	 *            默认请写0 否则读取配置文件
	 * @param sParms
	 *            替换参数
	 */
	public void logWarn(int iInfoCode) {
		LogFactory.getLog(this.getClass()).warn(PropVisitor.getLogInfo(iInfoCode));
	}
	
	/**
	 * @param lInfoId
	 *            默认请写0 否则读取配置文件
	 * @param sParms
	 *            替换参数
	 */
	public void logWarn(String content) {
		LogFactory.getLog(this.getClass()).warn(content);
	}
	
	/**
	 * 格式化日志输出
	 * 
	 * @param iInfoCode
	 * @param sParms
	 * @return
	 */
	public String formatLog(int iInfoCode, Object... sParms) {
		return "[" + String.valueOf(iInfoCode) + "]: " + PropVisitor.getLogInfo(iInfoCode, sParms);
	}
}
