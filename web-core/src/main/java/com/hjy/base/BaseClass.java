package com.hjy.base;

/**
 * 基类
 * 
 * @author srnpr
 */
public abstract class BaseClass {

//	// private Log logger = null;
//
//	/**
//	 * @param lInfoId
//	 *            默认请写0 否则读取配置文件
//	 * @param sParms
//	 *            替换参数
//	 */
//	public void bLogInfo(int iInfoCode, Object... sParms) {
//		/*
//		 * if (logger == null) { logger = LogFactory.getLog(this.getClass()); }
//		 */
//		LogFactory.getLog(this.getClass())
//				.info("[" + String.valueOf(iInfoCode) + "] " + TopUp.upLogInfo(iInfoCode, sParms));
//	}
//
//	/**
//	 * 错误日志
//	 * 
//	 * @param lInfoId
//	 *            默认请写0 否则读取配置文件
//	 * @param sParms
//	 *            替换参数
//	 */
//	public void bLogError(int iInfoCode, Object... sParms) {
//		/*
//		 * if (logger == null) { logger = LogFactory.getLog(this.getClass()); }
//		 */
//		LogFactory.getLog(this.getClass())
//				.error("[" + String.valueOf(iInfoCode) + "] " + TopUp.upLogInfo(iInfoCode, sParms));
//	}
//
//	/**
//	 * debug日志
//	 * 
//	 * @param iInfoCode
//	 * @param sParms
//	 */
//	public void bLogDebug(int iInfoCode, Object... sParms) {
//		/*
//		 * if (logger == null) { logger = LogFactory.getLog(this.getClass()); }
//		 */
//		LogFactory.getLog(this.getClass())
//				.debug("[" + String.valueOf(iInfoCode) + "] " + TopUp.upLogInfo(iInfoCode, sParms));
//	}
//
//	/**
//	 * @param sKey
//	 *            配置主键
//	 * @return 配置内容字符串
//	 */
//	public String bConfig(String sKey) {
//		return TopUp.upConfig(sKey);
//	}
//
//	/**
//	 * @param lInfoId
//	 *            文本编号
//	 * @param sParms
//	 *            拼接字符串
//	 * @return
//	 */
//	public String bInfo(long iInfoCode, Object... sParms) {
//
//		return FormatHelper.formatString(TopUp.upInfo(iInfoCode), sParms);
//	}

}
