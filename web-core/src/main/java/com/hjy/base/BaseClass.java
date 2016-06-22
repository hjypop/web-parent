package com.hjy.base;

import com.hjy.helper.FormatHelper;
import com.hjy.system.config.PropVisitor;

/**
 * 基类
 * 
 * @author HJY
 */
public abstract class BaseClass {
	public static BaseLog logger = new BaseLog();

	public BaseLog getLogger() {
		return logger;
	}
	
	/**
	 * alias bConfig
	 * @param sKey
	 *            配置主键
	 * @return 配置内容字符串
	 */
	public String getConfig(String sKey) {
		return PropVisitor.getConfig(sKey);
	}
	
	/**
	 * alias bInfo
	 * @param lInfoId
	 *            文本编号
	 * @param sParms
	 *            拼接字符串
	 * @return
	 */
	public String getInfo(long iInfoCode, Object... sParms) {

		return FormatHelper.formatString(PropVisitor.getInfo(iInfoCode), sParms);
	}
}
