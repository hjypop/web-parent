package com.core.helper;

import com.core.support.LogSupport;

public class LogHelper {

	/**
	 * 添加日志
	 * 
	 * @param sType
	 *            日志类型
	 * @param sInfo
	 *            日志内容
	 * @return
	 */
	public static boolean addLogString(String sType, String sInfo) {
		return LogSupport.getInstance().addLog(sType, sInfo);
	}

	/**
	 * 添加日志
	 * 
	 * @param sType
	 * @param oInfo
	 * @return
	 */
	public static boolean addLog(String sType, Object oInfo) {
		return LogSupport.getInstance().addLog(sType, GsonHelper.toJson(oInfo));
	}

}
