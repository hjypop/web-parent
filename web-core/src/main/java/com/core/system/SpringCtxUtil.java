package com.core.system;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public class SpringCtxUtil {

	private static ApplicationContext ctx;
	
	public static void setApplicationContext(ApplicationContext ctx) {
		SpringCtxUtil.ctx = ctx;
	}

	public static ApplicationContext getContext() {
		return ctx;
	}

	public static <T> T getBean(Class<T> clazz) throws BeansException {
		return (T) ctx.getBean(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) throws BeansException {
		return (T) ctx.getBean(beanName);
	}
	
}
