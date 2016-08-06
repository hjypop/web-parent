package com.hjy.helper;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @descriptions 捕获所有的异常信息，然后记录到数据库日志或*.log 
 * 
 * @author Yangcl
 * @date 2016年8月6日-下午9:53:08
 * @version 1.0.0
 */
public class ExceptionHelpter {
	
	/**
	 * @descriptions 获取异常的详细信息 以字符串的形式返回 保持栈堆风格
	 * 
	 * @param e 
	 * @author Yangcl
	 * @date 2016年8月6日-下午9:54:04
	 * @version 1.0.0.1
	 */
	public static String allExceptionInformation(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
}
