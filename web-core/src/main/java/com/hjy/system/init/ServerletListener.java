package com.hjy.system.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 初始化 加载各种配置和初始化类
 * 
 * @author HJY
 * 
 */
public class ServerletListener extends ServerletLoader implements
		ServletContextListener {

	private ServerletLoader contextLoader;

	public void contextDestroyed(ServletContextEvent event) {

		this.contextLoader.destory(event.getServletContext());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {

		if (this.contextLoader == null) {
			this.contextLoader = this;
		}
		this.contextLoader.init(event.getServletContext());

	}

}
