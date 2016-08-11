package com.hjy.request;

/**
 * @descriptions 请求基类。
 * 
 * @param <T>
 *            这是一个包含响应信息的泛型，它们都存放于com.hjy.response.data包中
 * @test open.api.request.RequestSample.requestMsgTest()
 * 
 * @date 2016年8月2日上午11:37:00
 * @author Yangcl
 * @version 1.0.1
 */
public class ApiRequest {
	private String sign;
	private String appSecret;// 商户渠道编号
	private String createTime; // 请求创建时间

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

}
