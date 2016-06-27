package com.hjy.helper;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hjy.base.BaseClass;
import com.hjy.system.config.PropVisitor;

/**
 * @author HJY
 * 
 */
public class BeansHelper extends BaseClass {

	private static BeanFactory beanFactory = null;

	private static int flagInit = 0;

	private static Object getBeanObject(String name) {

		if (flagInit < 1 || beanFactory == null) {
			flagInit = 2;
			if (flagInit == 2) {
				flagInit = 3;
				if (beanFactory == null) {
					new BeansHelper().initBeanFactory();
				}
			}
		}

		Object oReturn = null;

		// 尝试返回 如果失败二次返回
		try {
			oReturn = beanFactory.getBean(name);
		} catch (Exception e) {
			e.printStackTrace();
			logger.logInfo(BeansHelper.class, 967912050, name);
			beanFactory = null;
			new BeansHelper().initBeanFactory();
			oReturn = beanFactory.getBean(name);

		}

		return oReturn;
	}

	// alias upBean
	@SuppressWarnings("unchecked")
	public static <T> T upBean(String sBeanName) {
		return (T) getBeanObject(sBeanName);
	}

	private synchronized void initBeanFactory() {

		if (beanFactory == null) {
			String[] sSpringConfig = PropVisitor.getConfig("webcore.spring_bean")
					.split(",");

			logger.logInfo(BeansHelper.class, 967912051,
					PropVisitor.getConfig("webcore.spring_bean"));

			beanFactory = new ClassPathXmlApplicationContext(sSpringConfig)
					.getBeanFactory();

		}

	}

}
