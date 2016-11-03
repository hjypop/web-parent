package com.core.api;

import com.core.annotation.ZapcomApi;

/**
 * @descriptions 输入参数
 * 
 * @author srnpr
 * @date 2016年6月26日-下午3:18:43
 * @version 1.0.0
 */
public class RootInput  { // implements IBaseInput 	TODO 猛男涛 感觉这里没必要实现接口！！！！！！！！！！！！！！！！

	/**
	 * 版本标记 默认值为1 该参数用于扩展使用
	 */
	@ZapcomApi(value="版本号",remark="仅用于扩展，无需传入",require=0)
	private int version = 1;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	
	
	

}
