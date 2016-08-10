package com.hjy.request;

/**
 * 
 * 类: Request <br>
 * 描述: api请求报文参数 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月10日 下午4:50:32
 */
public class Request {

	/**
	 * 调用方法
	 */
	private String method;
	/**
	 * 商户渠道识别码
	 */
	private String appid;
	/**
	 * 时间戳
	 */
	private String timestamp;
	/**
	 * 请求参数json字符串
	 */
	private String data;
	/**
	 * 随机数
	 */
	private String nonce;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

}
