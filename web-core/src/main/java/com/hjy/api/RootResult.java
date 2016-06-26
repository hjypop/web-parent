package com.hjy.api;

import com.hjy.annotation.ZapcomApi;
import com.hjy.iface.IBaseResult;

/**
 * @descriptions 返回执行结果
 * 
 * @author srnpr
 * @date 2016年6月26日-下午3:18:21
 * @version 1.0.0
 */
public class RootResult implements IBaseResult{  

	/**
	 * 返回标记 如果该标记为1则表明返回结果正确 否则都是错误消息
	 */
	@ZapcomApi(value="返回标记",remark="如果返回标记1则为API调用成功  否则则是错误编号")
	private int resultCode = 1;

	/**
	 * 返回消息 一般用于返回错误描述或者操作提示
	 */
	@ZapcomApi(value="返回消息",remark="返回的消息描述")
	private String resultMessage = "";

	public int getCode() {
		return resultCode;
	}

	public void setCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return resultMessage;
	}

	public void setMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}


}
