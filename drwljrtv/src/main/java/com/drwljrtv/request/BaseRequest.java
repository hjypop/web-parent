package com.drwljrtv.request;

/**
 * 
 * 类: BaseRequest <br>
 * 描述: 请求参数基类 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月4日 下午5:45:38
 */
public class BaseRequest {

	/**
	 * login 固定值（可以理解为方法名称）
	 */
	private String cmd;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

}
