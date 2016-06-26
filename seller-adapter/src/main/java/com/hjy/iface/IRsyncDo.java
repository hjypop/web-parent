package com.hjy.iface;

import com.hjy.model.RsyncResult;

public interface IRsyncDo<TConfig extends IRsyncConfig, TRequest extends IRsyncRequest, TResponse extends IRsyncResponse>
																	   extends IRsyncProcess {

	/**
	 * 返回当前的配置的调用 <br/>
	 * 通常返回一个常亮型的配置
	 * 
	 * @return
	 */
	public TConfig upConfig();

	/**
	 * 设置输入参数
	 * 
	 * @return
	 */
	public TRequest upRsyncRequest();

	/**
	 * 处理逻辑 在调用接口成功后触发该逻辑处理
	 * 
	 * @param tRequest
	 * @param tResponse
	 * @return
	 */
	public RsyncResult doProcess(TRequest tRequest, TResponse tResponse);

	/**
	 * 获取返回参数的对象 通常new一个返回参数可以 用于json的反序列化
	 * 
	 * @return
	 */
	public TResponse upResponseObject();

}
