package com.core.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.TopicConnection;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;

import com.core.base.BaseClass;



/**
 * @author srnpr
 * 
 */
public class JmsConnection extends BaseClass {

	static PooledConnection connection = null;

	public static PooledConnection getInstance() {
		if (connection == null) {
			JmsConnection jConnection = new JmsConnection();
			jConnection.initConn();
		}
		return connection;
	}

	/**
	 * 获取连接
	 * @return
	 */
	private ActiveMQConnectionFactory getFactory() {
		String url = getConfig("webcore.jms_server_conn");
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setUserName(getConfig("webcore.jms_server_name"));
		activeMQConnectionFactory.setPassword(getConfig("webcore.jms_server_pass"));
		activeMQConnectionFactory.setBrokerURL(url);

		return activeMQConnectionFactory;
	}

	static TopicConnection clientConnection = null;

	public static Connection getClientConnection() {
		if (clientConnection == null) {
			try {
				clientConnection =new JmsConnection(). getFactory().createTopicConnection();
				clientConnection.setClientID(ServerInfo.INSTANCE.getServerCode());
				clientConnection.start();
			} catch (JMSException e) {

				e.printStackTrace();
			}
		}
		return clientConnection;

	}

	private void initConn() {
		try {
			PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(getFactory());
			// session数
			int maximumActive = 2000;
			pooledConnectionFactory.setMaximumActiveSessionPerConnection(maximumActive);
			pooledConnectionFactory.setIdleTimeout(1000);
			pooledConnectionFactory.setExpiryTimeout(1000);
			pooledConnectionFactory.setMaxConnections(5);
			pooledConnectionFactory.setBlockIfSessionPoolIsFull(true);
			connection = (PooledConnection) pooledConnectionFactory.createConnection();
			// connection.setClientID(ServerInfo.INSTANCE.getServerCode());
			// 必须start，否则无法接收消息
			connection.start();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public boolean destory() {
		try {
			if (connection != null) {
				connection.stop();
				connection.close();
			}
			if (clientConnection != null) {
				clientConnection.stop();
				clientConnection.close();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return true;
	}

}

































