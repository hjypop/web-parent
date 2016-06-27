package com.hjy.iface;

import javax.jms.MessageListener;

import com.hjy.model.MDataMap;


public interface IJmsListener extends MessageListener {

	/**
	 * 收到消息时的回调
	 * 
	 * @param message
	 *            消息内容
	 * @return 处理结果 默认请返回true
	 */
	public boolean onReceiveText(String sMessage, MDataMap mPropMap);

}
