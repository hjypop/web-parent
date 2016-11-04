package com.drwljrtv.response;

/**
 * 
 * 类: BaseResponse <br>
 * 描述: 响应参数基类 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月4日 下午5:45:51
 */
public class BaseResponse {

	/**
	 * 响应编码
	 */
	private String ret;
	/**
	 * 响应消息
	 */
	private String msg;
	/**
	 * 响应数据
	 */
	private String data;

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
