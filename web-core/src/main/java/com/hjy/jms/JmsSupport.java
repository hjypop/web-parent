package com.hjy.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.commons.lang.StringUtils;

import com.hjy.base.BaseClass;
import com.hjy.constant.WebConst;
import com.hjy.helper.WebHelper;
import com.hjy.iface.IBaseInstance;
import com.hjy.model.MDataMap;


public class JmsSupport extends BaseClass implements IBaseInstance {

	private final static JmsSupport jmsSupport = new JmsSupport();

	public static JmsSupport getInstance() {
		return jmsSupport;
	}

	/**
	 * 
	 * @param sTypeName
	 *            主题名称 该字段尽量定义为46991020**** 以保证系统唯一性
	 * @param sMsg
	 *            消息内容
	 */
	public void sendMessage(String sTypeName , String sMsg , MDataMap mPropMap , EJmsMessageType eMessageType) {
		// 定义如果启用了jms系统
		if (WebConst.CONST_FLAG_ENABLE_JMS) {
			try {
				Session session = JmsConnection.getInstance().createSession( false, Session.AUTO_ACKNOWLEDGE);
				Destination destination = null;
				// System.out.println("消息队列触发了,已注释！如使用请搜索本句话~");
				switch (eMessageType) {
				case Toplic:
					destination = session.createTopic(sTypeName);
					break;
				case Queue:
					destination = session.createQueue(sTypeName);
					break;
				}

				MessageProducer producer = session.createProducer(destination);
				// producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				TextMessage message = session.createTextMessage(sMsg);
				if (mPropMap != null) {
					for (String sKey : mPropMap.keySet()) {
						message.setStringProperty(sKey, mPropMap.get(sKey));
					}
				}
				producer.send(message);
				session.close();

			} catch (JMSException e) {
				bLogError(970205031, sTypeName, sMsg);
				WebHelper.getInstance().errorMessage(sTypeName, "jmserror", 1, "com.srnpr.zapzero.support.JmsSupport.sendToTopic", sTypeName + WebConst.CONST_SPLIT_LINE + sMsg, e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param sTypeName
	 *            主题名称 该字段尽量定义为46991020**** 以保证系统唯一性
	 * @param sSubName
	 *            持久化标记 该字段可谓空 留空时标记不持久化 否则请输入事件的名称
	 * @param eMessageType
	 *            定义成Queue模式时
	 * @param listener
	 *            监听事件对象
	 */
	public void addTopicLisense(String sTypeName , String sSubName , EJmsMessageType eMessageType , IJmsListener listener) {
		try {
			Session session = null;
			if (eMessageType.equals(EJmsMessageType.Toplic) && StringUtils.isNotBlank(sSubName)) {
				session = JmsConnection.getClientConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
			} else {
				session = JmsConnection.getInstance().createSession(false , Session.AUTO_ACKNOWLEDGE);
			}

			Destination destination = null;
			MessageConsumer consumer = null;

			switch (eMessageType){
			case Toplic:
				destination = session.createTopic(sTypeName);
				break;
			case Queue:
				sSubName = "";
				destination = session.createQueue(sTypeName);
				break;
			}

			if (StringUtils.isNotBlank(sSubName)) {
				// session.unsubscribe(sSubName);
				consumer = session.createDurableSubscriber(session.createTopic(sTypeName), sSubName);
			} else {
				consumer = session.createConsumer(destination);
			}
			consumer.setMessageListener(listener);
		} catch (JMSException e) {
			bLogError(970205032, sTypeName, sSubName);
			WebHelper.errorMessage(sTypeName, "jmserror", 1, "com.srnpr.zapzero.support.JmsSupport.addTopicLisense" , 
					sTypeName + WebConst.CONST_SPLIT_LINE + sSubName, e);
			e.printStackTrace();
		}
	}
}























